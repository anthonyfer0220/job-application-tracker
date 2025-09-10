package com.anthonyfer0220.infra;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.Tags;
import software.amazon.awscdk.services.ec2.InstanceClass;
import software.amazon.awscdk.services.ec2.InstanceSize;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.ec2.Port;
import software.amazon.awscdk.services.certificatemanager.Certificate;
import software.amazon.awscdk.services.certificatemanager.CertificateValidation;
import software.amazon.awscdk.services.ec2.ISecurityGroup;
import software.amazon.awscdk.services.ec2.SubnetSelection;
import software.amazon.awscdk.services.ec2.SubnetType;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ecr.Repository;
import software.amazon.awscdk.services.ecs.AwsLogDriverProps;
import software.amazon.awscdk.services.ecs.CloudMapOptions;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerDefinitionOptions;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.FargateService;
import software.amazon.awscdk.services.ecs.FargateTaskDefinition;
import software.amazon.awscdk.services.ecs.LogDriver;
import software.amazon.awscdk.services.ecs.PortMapping;
import software.amazon.awscdk.services.ecs.Protocol;
import software.amazon.awscdk.services.ecs.Secret;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.elasticloadbalancingv2.HealthCheck;
import software.amazon.awscdk.services.logs.LogGroup;
import software.amazon.awscdk.services.logs.RetentionDays;
import software.amazon.awscdk.services.rds.Credentials;
import software.amazon.awscdk.services.rds.DatabaseInstance;
import software.amazon.awscdk.services.rds.DatabaseInstanceEngine;
import software.amazon.awscdk.services.rds.PostgresEngineVersion;
import software.amazon.awscdk.services.rds.PostgresInstanceEngineProps;
import software.amazon.awscdk.services.rds.StorageType;
import software.amazon.awscdk.services.route53.HostedZone;
import software.amazon.awscdk.services.route53.HostedZoneProviderProps;
import software.amazon.awscdk.services.route53.IHostedZone;
import software.amazon.awscdk.services.servicediscovery.DnsRecordType;
import software.amazon.awscdk.services.servicediscovery.IPrivateDnsNamespace;
import software.amazon.awscdk.services.servicediscovery.PrivateDnsNamespace;
import software.amazon.awscdk.services.secretsmanager.SecretStringGenerator;
import software.constructs.Construct;

public class BackendStack extends Stack {
	private final Vpc vpc;
	private final Cluster ecsCluster;
	private final IPrivateDnsNamespace namespace;

	public BackendStack(final Construct scope, final String id, final StackProps props) {
		super(scope, id, props);
		Tags.of(this).add("Project", "JobApplicationTracker");
		Tags.of(this).add("Owner", "Anthony Fernandez");
		Tags.of(this).add("Env", "Dev");

		this.vpc = createVpc();

		this.namespace = PrivateDnsNamespace.Builder
				.create(this, "JobApplicationTrackerNamespace")
				.name("job-application-tracker.internal")
				.vpc(vpc)
				.build();

		// Per-service DBs
		DatabaseInstance authServiceDb = createDatabase(
				"AuthServiceDB", "auth_service_db");
		DatabaseInstance jobApplicationServiceDb = createDatabase(
				"JobApplicationServiceDB", "job_application_service_db");

		// Cluster + namespace
		this.ecsCluster = createEcsCluster();

		// JWT secret (Secrets Manager) for auth-service
		software.amazon.awscdk.services.secretsmanager.Secret jwtSecret = createJwtSecret();

		// Services
		FargateService jobApplicationService = createFargateService(
				"JobApplicationService",
				"job-application-service",
				List.of(4000),
				jobApplicationServiceDb,
				"job_application_service_db",
				null,
				null);

		jobApplicationServiceDb.getConnections().allowDefaultPortFrom(jobApplicationService,
				"Job Application service can reach its DB");

		FargateService authService = createFargateService(
				"AuthService",
				"auth-service",
				List.of(4002),
				authServiceDb,
				"auth_service_db",
				null,
				Map.of(
						"JWT_SECRET", Secret.fromSecretsManager(jwtSecret)));

		authServiceDb.getConnections().allowDefaultPortFrom(authService, "Auth service can reach its DB");

		// API Gateway (ALB + Fargate)
		ApplicationLoadBalancedFargateService api = createApiGatewayService();

		// Alllow API -> backends, ALB health checks
		wireTraffic(api, jobApplicationService, authService);
	}

	private Vpc createVpc() {
		return Vpc.Builder
				.create(this, "JobTrackerVPC")
				.vpcName("JobTrackerVPC")
				.maxAzs(2)
				.natGateways(0)
				.build();
	}

	private DatabaseInstance createDatabase(String id, String dbName) {
		return DatabaseInstance.Builder
				.create(this, id)
				.engine(DatabaseInstanceEngine.postgres(PostgresInstanceEngineProps.builder()
						.version(PostgresEngineVersion.VER_17_2)
						.build()))
				.vpc(vpc)
				.vpcSubnets(SubnetSelection.builder()
						.subnetType(SubnetType.PRIVATE_ISOLATED)
						.build())
				.publiclyAccessible(false)
				.instanceType(InstanceType.of(InstanceClass.BURSTABLE3, InstanceSize.MICRO))
				.allocatedStorage(20)
				.storageType(StorageType.GP3)
				.credentials(Credentials.fromGeneratedSecret("admin_user"))
				.databaseName(dbName)
				.backupRetention(Duration.days(7))
				.deletionProtection(true)
				.removalPolicy(RemovalPolicy.RETAIN)
				.build();
	}

	private Cluster createEcsCluster() {
		return Cluster.Builder
				.create(this, "JobApplicationTrackerCluster")
				.vpc(vpc)
				.build();
	}

	private software.amazon.awscdk.services.secretsmanager.Secret createJwtSecret() {
		return software.amazon.awscdk.services.secretsmanager.Secret.Builder.create(this, "JwtSecret")
				.secretName("jobtracker/jwt")
				.generateSecretString(SecretStringGenerator.builder()
						.excludePunctuation(true)
						.passwordLength(48)
						.build())
				.build();
	}

	private FargateService createFargateService(
			String id,
			String imageName,
			List<Integer> ports,
			DatabaseInstance db,
			String dbName,
			Map<String, String> additionalEnvVars,
			Map<String, Secret> containerSecrets) {

		FargateTaskDefinition taskDefinition = FargateTaskDefinition.Builder
				.create(this, id + "Task")
				.cpu(256)
				.memoryLimitMiB(512)
				.build();

		ContainerDefinitionOptions.Builder containerOptions = ContainerDefinitionOptions.builder()
				.image(imageFromEcr(imageName, "1.0.0"))
				.portMappings(ports.stream()
						.map(port -> PortMapping.builder()
								.containerPort(port)
								.protocol(Protocol.TCP)
								.build())
						.toList())
				.logging(LogDriver.awsLogs(AwsLogDriverProps.builder()
						.logGroup(LogGroup.Builder.create(this, id + "LogGroup")
								.logGroupName("/ecs/" + imageName)
								.removalPolicy(RemovalPolicy.DESTROY)
								.retention(RetentionDays.ONE_DAY)
								.build())
						.streamPrefix(imageName)
						.build()));

		Map<String, String> envVars = new HashMap<>();
		Map<String, Secret> secrets = new HashMap<>();

		if (db != null) {
			envVars.put("SPRING_DATASOURCE_URL", "jdbc:postgresql://%s:%s/%s".formatted(
					db.getDbInstanceEndpointAddress(),
					db.getDbInstanceEndpointPort(),
					dbName));

			envVars.put("SPRING_DATASOURCE_USERNAME", "admin_user");
			envVars.put("SPRING_JPA_HIBERNATE_DDL_AUTO", "update");
			envVars.put("SPRING_SQL_INIT_MODE", "always");
			envVars.put("SPRING_DATASOURCE_HIKARI_INITIALIZATION_FAIL_TIMEOUT", "60000");

			secrets.put("SPRING_DATASOURCE_PASSWORD", Secret.fromSecretsManager(db.getSecret(), "password"));
		}

		if (additionalEnvVars != null && !additionalEnvVars.isEmpty()) {
			envVars.putAll(additionalEnvVars);
		}

		if (containerSecrets != null && !containerSecrets.isEmpty()) {
			secrets.putAll(containerSecrets);
		}

		containerOptions.environment(envVars);
		containerOptions.secrets(secrets);

		taskDefinition.addContainer(imageName + "Container", containerOptions.build());

		return FargateService.Builder.create(this, id)
				.cluster(ecsCluster)
				.taskDefinition(taskDefinition)
				.enableExecuteCommand(true)
				.assignPublicIp(true)
				.vpcSubnets(SubnetSelection.builder()
						.subnetType(SubnetType.PUBLIC).build())
				.serviceName(imageName)
				.cloudMapOptions(CloudMapOptions.builder()
						.cloudMapNamespace(namespace)
						.name(imageName)
						.dnsRecordType(DnsRecordType.A)
						.dnsTtl(Duration.seconds(10))
						.build())
				.desiredCount(1)
				.build();
	}

	private ApplicationLoadBalancedFargateService createApiGatewayService() {
		FargateTaskDefinition taskDefinition = FargateTaskDefinition.Builder
				.create(this, "APIGatewayTaskDefinition")
				.cpu(256)
				.memoryLimitMiB(512)
				.build();

		ContainerDefinitionOptions containerOptions = ContainerDefinitionOptions.builder()
				.image(imageFromEcr("api-gateway", "1.0.0"))
				.environment(Map.of(
						"SPRING_PROFILES_ACTIVE", "prod",
						"FRONTEND_ORIGIN", "https://www.personaljobtracker.com",
						"JOB_SERVICE_URL", "http://job-application-service.job-application-tracker.internal:4000",
						"AUTH_SERVICE_URL", "http://auth-service.job-application-tracker.internal:4002"))
				.portMappings(List.of(4005).stream()
						.map(port -> PortMapping.builder()
								.containerPort(port)
								.protocol(Protocol.TCP)
								.build())
						.toList())
				.logging(LogDriver.awsLogs(AwsLogDriverProps.builder()
						.logGroup(LogGroup.Builder.create(this, "ApiGatewayLogGroup")
								.logGroupName("/ecs/api-gateway")
								.removalPolicy(RemovalPolicy.DESTROY)
								.retention(RetentionDays.ONE_DAY)
								.build())
						.streamPrefix("api-gateway")
						.build()))
				.build();

		taskDefinition.addContainer("APIGatewayContainer", containerOptions);

		IHostedZone zone = HostedZone.fromLookup(this, "ApiZone", HostedZoneProviderProps.builder()
				.domainName("personaljobtracker.com")
				.build());

		Certificate cert = Certificate.Builder.create(this, "ApiCert")
				.domainName("api.personaljobtracker.com")
				.validation(CertificateValidation.fromDns(zone))
				.build();

		return ApplicationLoadBalancedFargateService.Builder
				.create(this, "APIGatewayService")
				.cluster(ecsCluster)
				.taskDefinition(taskDefinition)
				.desiredCount(1)
				.healthCheckGracePeriod(Duration.seconds(60))
				.enableExecuteCommand(true)
				.taskSubnets(SubnetSelection.builder()
						.subnetType(SubnetType.PUBLIC)
						.build())
				.assignPublicIp(true)
				.certificate(cert)
				.domainName("api.personaljobtracker.com")
				.domainZone(zone)
				.build();
	}

	public void wireTraffic(ApplicationLoadBalancedFargateService api, FargateService job, FargateService auth) {
		ISecurityGroup apiSg = api.getService().getConnections().getSecurityGroups().get(0);
		ISecurityGroup jobSg = job.getConnections().getSecurityGroups().get(0);
		ISecurityGroup authSg = auth.getConnections().getSecurityGroups().get(0);

		jobSg.addIngressRule(apiSg, Port.tcp(4000), "API to Job");
		authSg.addIngressRule(apiSg, Port.tcp(4002), "API to Auth");

		api.getTargetGroup().configureHealthCheck(HealthCheck.builder()
				.path("/actuator/health")
				.healthyHttpCodes("200")
				.interval(Duration.seconds(15))
				.timeout(Duration.seconds(5))
				.build());
	}

	private ContainerImage imageFromEcr(String repoName, String tag) {
		var repo = Repository.fromRepositoryName(this, repoName + "Repo", repoName);
		return ContainerImage.fromEcrRepository(repo, tag);
	}
}
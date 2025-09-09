package com.anthonyfer0220.infra;

import java.util.List;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.certificatemanager.Certificate;
import software.amazon.awscdk.services.certificatemanager.CertificateValidation;
import software.amazon.awscdk.services.route53.ARecord;
import software.amazon.awscdk.services.route53.HostedZone;
import software.amazon.awscdk.services.route53.HostedZoneProviderProps;
import software.amazon.awscdk.services.route53.IHostedZone;
import software.amazon.awscdk.services.route53.RecordTarget;
import software.amazon.awscdk.services.route53.targets.CloudFrontTarget;
import software.amazon.awscdk.services.s3.BlockPublicAccess;
import software.amazon.awscdk.services.s3.Bucket;
import software.amazon.awscdk.services.s3.BucketEncryption;
import software.amazon.awscdk.services.cloudfront.BehaviorOptions;
import software.amazon.awscdk.services.cloudfront.Distribution;
import software.amazon.awscdk.services.cloudfront.IOrigin;
import software.amazon.awscdk.services.cloudfront.OriginAccessIdentity;
import software.amazon.awscdk.services.cloudfront.SecurityPolicyProtocol;
import software.amazon.awscdk.services.cloudfront.ViewerProtocolPolicy;
import software.amazon.awscdk.services.cloudfront.origins.S3BucketOrigin;
import software.amazon.awscdk.services.cloudfront.origins.S3BucketOriginWithOAIProps;
import software.constructs.Construct;

public class FrontendStack extends Stack {

    public FrontendStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        IHostedZone zone = createZone();

        Bucket siteBucket = createBucket();

        Certificate cert = createViewerCert(zone);

        OriginAccessIdentity oai = createOai();

        IOrigin s3Origin = S3BucketOrigin.withOriginAccessIdentity(siteBucket,
                S3BucketOriginWithOAIProps.builder()
                        .originAccessIdentity(oai)
                        .build());

        Distribution dist = createDistribution(s3Origin, cert);

        createDns(zone, dist);
    }

    private IHostedZone createZone() {
        return HostedZone.fromLookup(this, "Zone", HostedZoneProviderProps.builder()
                .domainName("personaljobtracker.com")
                .build());
    }

    private Bucket createBucket() {
        return Bucket.Builder.create(this, "SiteBucket")
                .blockPublicAccess(BlockPublicAccess.BLOCK_ALL)
                .encryption(BucketEncryption.S3_MANAGED)
                .build();
    }

    private Certificate createViewerCert(IHostedZone zone) {
        return Certificate.Builder.create(this, "ViewerCert")
                .domainName("www.personaljobtracker.com")
                .validation(CertificateValidation.fromDns(zone))
                .build();
    }

    private OriginAccessIdentity createOai() {
        return OriginAccessIdentity.Builder.create(this, "OAI")
                .comment("Access identity for CloudFront to reach S3")
                .build();
    }

    private Distribution createDistribution(IOrigin s3Origin, Certificate cert) {
        return Distribution.Builder.create(this, "Dist")
                .defaultBehavior(BehaviorOptions.builder()
                        .origin(s3Origin)
                        .viewerProtocolPolicy(ViewerProtocolPolicy.REDIRECT_TO_HTTPS)
                        .build())
                .defaultRootObject("index.html")
                .domainNames(List.of("www.personaljobtracker.com"))
                .certificate(cert)
                .minimumProtocolVersion(SecurityPolicyProtocol.TLS_V1_2_2021)
                .build();
    }

    private void createDns(IHostedZone zone, Distribution dist) {
        ARecord.Builder.create(this, "WwwAlias")
                .zone(zone)
                .recordName("www")
                .target(RecordTarget.fromAlias(new CloudFrontTarget(dist)))
                .build();
    }
}
package com.anthonyfer0220.infra;

import software.amazon.awscdk.App;
import software.amazon.awscdk.AppProps;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

public class AppMain {
    public static void main(final String[] args) {
        App app = new App(AppProps.builder().outdir("./cdk.out").build());

        Environment env = Environment.builder()
                .account(System.getenv("CDK_DEFAULT_ACCOUNT"))
                .region("us-east-1")
                .build();

        new BackendStack(app, "Backend", StackProps.builder()
                .env(env)
                .stackName("JobApplicationTrackerBackend")
                .build());

        new FrontendStack(app, "Frontend", StackProps.builder()
                .env(env)
                .stackName("JobApplicationTrackerFrontend")
                .build());

        app.synth();
        System.out.println("Synth complete");
    }
}
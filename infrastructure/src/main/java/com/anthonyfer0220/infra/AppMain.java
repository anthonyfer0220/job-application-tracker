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
                .region(System.getenv("CDK_DEFAULT_REGION"))
                .build();

        StackProps props = StackProps.builder()
                .env(env)
                .stackName("JobApplicationTrackerBackend")
                .build();

        new BackendStack(app, "Backend", props);

        app.synth();
        System.out.println("Synth complete");
    }
}
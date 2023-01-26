package com.myorg;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;



public class CdkJavaApp {
    public static void main(final String[] args) {
        App app = new App();

        new CdkJavaStack(app, "CdkJavaStack", StackProps.builder()
                .env(Environment.builder()
                        .account("080266302756")
                        .region("us-east-1")
                        .build())

                // For more information, see https://docs.aws.amazon.com/cdk/latest/guide/environments.html
                .build());

        app.synth();
    }
}


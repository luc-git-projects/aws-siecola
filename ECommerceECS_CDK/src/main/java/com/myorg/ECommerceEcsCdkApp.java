package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ECommerceEcsCdkApp {
    public static void main(final String[] args) {
        App app = new App();

        Environment environment = Environment.builder()
                .account("014498656507")
                .region("us-east-2")
                .build();

        Map<String, String> infraTags = new HashMap<>();
        infraTags.put("team", "SiecolaCode");
        infraTags.put("cost", "ECommerceInfra");

        StackProps stackProps = StackProps.builder()
                .env(environment)
                .tags(infraTags)
                .build();

        EcrStack ecrStack = new EcrStack(app, "Ecr", stackProps);

        app.synth();
    }
}


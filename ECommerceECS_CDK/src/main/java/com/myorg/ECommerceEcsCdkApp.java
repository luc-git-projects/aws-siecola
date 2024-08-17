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



        StackProps ecrStackProps = StackProps.builder()
                .env(environment)
                .tags(infraTags)
                .build();

        EcrStack ecrStack = new EcrStack(app, "Ecr", ecrStackProps);

        StackProps vpcStackProps = StackProps.builder()
                .env(environment)
                .tags(infraTags)
                .build();

        VpcStack vpcStack = new VpcStack(app,"Vpc", vpcStackProps);



        StackProps clusterStackProps = StackProps.builder()
                .env(environment)
                .tags(infraTags)
                .build();

        ClusterStack clusterStack = new ClusterStack(app, "Cluster", clusterStackProps, new ClusterStackProps(vpcStack.getVpc()));
        clusterStack.addDependency(vpcStack);



        StackProps nlbStackProps = StackProps.builder()
                .env(environment)
                .tags(infraTags)
                .build();

        NlbStack nlbStack = new NlbStack(app, "Nlb", nlbStackProps, new NblStackProps(vpcStack.getVpc()));
        nlbStack.addDependency(vpcStack);



        Map<String, String> productServiceTags = new HashMap<>();
        productServiceTags.put("team", "SiecolaCode");
        productServiceTags.put("cost", "ProductsService");

        ProductsServiceStack productsServiceStack = new ProductsServiceStack(app, "ProductsService",
                StackProps.builder()
                        .env(environment)
                        .tags(productServiceTags)
                        .build(),
                new ProductsServiceProps(vpcStack.getVpc(),
                        clusterStack.getCluster(),
                        nlbStack.getNetworkLoadBalancer(),
                        nlbStack.getApplicationLoadBalancer(),
                        ecrStack.getProductsServiceRepository())
                );
        productsServiceStack.addDependency(vpcStack);
        productsServiceStack.addDependency(clusterStack);
        productsServiceStack.addDependency(nlbStack);
        productsServiceStack.addDependency(ecrStack);

        ApiStack apiStack = new ApiStack(app, "Api", StackProps.builder()
                .env(environment)
                .tags(infraTags)
                .build(),
                new ApiStackProps(
                        nlbStack.getNetworkLoadBalancer(),
                        nlbStack.getVpcLink()
                ));
        apiStack.addDependency(nlbStack);

        app.synth();
    }
}


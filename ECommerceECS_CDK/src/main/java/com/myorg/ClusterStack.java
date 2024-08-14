package com.myorg;

import org.jetbrains.annotations.Nullable;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ClusterProps;
import software.constructs.Construct;

public class ClusterStack extends Stack {

    private final Cluster cluster;
    public ClusterStack(final Construct scope, final String id, final StackProps props, ClusterStackProps clusterStackProps) {
        super(scope, id, props);

        ClusterProps clusterProps = ClusterProps.builder()
                .clusterName("ECommerce")
                .vpc(clusterStackProps.vpc())
                .containerInsights(true)
                .build();

        this.cluster = new Cluster(this, "Cluster", clusterProps);
    }

    public Cluster getCluster() {
        return cluster;
    }
}

record ClusterStackProps(Vpc vpc){
}

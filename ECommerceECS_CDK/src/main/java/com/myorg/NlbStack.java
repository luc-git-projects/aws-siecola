package com.myorg;

import org.jetbrains.annotations.Nullable;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.apigateway.VpcLink;
import software.amazon.awscdk.services.apigateway.VpcLinkProps;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.elasticloadbalancingv2.ApplicationLoadBalancer;
import software.amazon.awscdk.services.elasticloadbalancingv2.ApplicationLoadBalancerProps;
import software.amazon.awscdk.services.elasticloadbalancingv2.NetworkLoadBalancer;
import software.amazon.awscdk.services.elasticloadbalancingv2.NetworkLoadBalancerProps;
import software.constructs.Construct;

import java.util.Collections;

public class NlbStack extends Stack {

    private final VpcLink vpcLink;

    private final NetworkLoadBalancer networkLoadBalancer;

    private final ApplicationLoadBalancer applicationLoadBalancer;

    public NlbStack(final Construct scope, final String id, final StackProps props, NblStackProps nblStackProps) {
        super(scope, id, props);

        this.networkLoadBalancer = new NetworkLoadBalancer(this, "Nlb",
                NetworkLoadBalancerProps.builder()
                        .loadBalancerName("ECommerceNlb")
                        .internetFacing(false)
                        .vpc(nblStackProps.vpc())
                        .build());

        this.vpcLink = new VpcLink(this, "VpcLink",
                VpcLinkProps.builder()
                        .targets(Collections.singletonList(this.networkLoadBalancer))
                        .build());

        this.applicationLoadBalancer = new ApplicationLoadBalancer(this, "Alb",
                ApplicationLoadBalancerProps.builder()
                        .loadBalancerName("ECommerceAlb")
                        .internetFacing(false)
                        .vpc(nblStackProps.vpc())
                        .build()
                );
    }

    public VpcLink getVpcLink() {
        return vpcLink;
    }

    public NetworkLoadBalancer getNetworkLoadBalancer() {
        return networkLoadBalancer;
    }

    public ApplicationLoadBalancer getApplicationLoadBalancer() {
        return applicationLoadBalancer;
    }
}

record NblStackProps(Vpc vpc){

}

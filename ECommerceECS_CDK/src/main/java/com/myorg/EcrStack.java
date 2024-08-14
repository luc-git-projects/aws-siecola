package com.myorg;

import org.jetbrains.annotations.Nullable;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ecr.Repository;
import software.amazon.awscdk.services.ecr.RepositoryProps;
import software.amazon.awscdk.services.ecr.TagMutability;
import software.constructs.Construct;

public class EcrStack extends Stack {

    private final Repository productsServiceRepository;

    public EcrStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        RepositoryProps repositoryProps = RepositoryProps.builder()
                .repositoryName("productsservice")
                .removalPolicy(RemovalPolicy.DESTROY)
                .imageTagMutability(TagMutability.IMMUTABLE)
                .emptyOnDelete(true)
                .build();

        this.productsServiceRepository = new Repository(this, "Productsservice", repositoryProps);
    }

    public Repository getProductsServiceRepository() {
        return productsServiceRepository;
    }
}

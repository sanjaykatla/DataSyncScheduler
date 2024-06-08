package org.sanjay.datasyncscheduler.adapter.source.aws.config;

import org.sanjay.datasyncscheduler.adapter.source.aws.proxy.impl.AwsS3ProxyImpl;
import org.sanjay.datasyncscheduler.adapter.source.aws.service.impl.AwsS3SourceServiceImpl;
import org.sanjay.datasyncscheduler.adapter.source.proxy.SourceStorageProxy;
import org.sanjay.datasyncscheduler.adapter.source.service.SourceStorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsSourceConfig {

    private final AwsProperties awsProperties;

    AwsSourceConfig(AwsProperties awsProperties) {
        this.awsProperties = awsProperties;
    }

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials awsCred = AwsBasicCredentials.create(
                awsProperties.getAccessKeyId(),
                awsProperties.getSecretAccessKey()
        );
        return S3Client.builder()
                .region(Region.of(awsProperties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(awsCred))
                .build();
    }

    @Bean(name = "awsS3Proxy")
    public SourceStorageProxy getAwsSourceStorageProxy() {
        return new AwsS3ProxyImpl(s3Client());
    }

    @Bean(name = "awsSourceStorageService")
    public SourceStorageService getAwsSourceStorageService() {
        return new AwsS3SourceServiceImpl(getAwsSourceStorageProxy());
    }
}


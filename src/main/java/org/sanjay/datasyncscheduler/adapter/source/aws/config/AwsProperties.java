package org.sanjay.datasyncscheduler.adapter.source.aws.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aws")
@Setter
@Getter
public class AwsProperties {
    private String accessKeyId;
    private String secretAccessKey;
    private String region;
}


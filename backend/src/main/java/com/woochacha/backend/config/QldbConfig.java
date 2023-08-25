package com.woochacha.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.qldbsession.QldbSessionClient;
import software.amazon.qldb.QldbDriver;
import software.amazon.qldb.RetryPolicy;

@Configuration
public class QldbConfig {
    @Value("${ledger.name}")
    private String ledgerName;

    @Value("${aws.db.access.key}")
    private String accessKeyId;

    @Value("${aws.db.secret.key}")
    private String secretAccessKey;

    @Value("${aws.db.region}")
    private String region;

    @Bean
    public QldbDriver QldbDriver(){
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);
        return QldbDriver.builder()
                .ledger(ledgerName)
                .transactionRetryPolicy(RetryPolicy
                        .builder()
                        .maxRetries(3)
                        .build())
                .sessionClientBuilder(QldbSessionClient.builder()
                        .region(Region.of(region))
                        .credentialsProvider(credentialsProvider)
                )
                .build();
    }

}

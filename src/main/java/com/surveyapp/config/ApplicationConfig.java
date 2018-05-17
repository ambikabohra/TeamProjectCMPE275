package com.surveyapp.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.surveyapp.backend.persistence.repositories")
@EntityScan(basePackages = "com.surveyapp.backend.persistence.domain.backend")
@EnableTransactionManagement
@PropertySource("file:///${user.home}/.surveyapp/application-common.properties")
public class ApplicationConfig {

   /* @Value("${aws.s3.profile}")
    private String awsProfileName;

    @Bean
    public AmazonS3Client s3Client() {
        AWSCredentials credentials = new ProfileCredentialsProvider(awsProfileName).getCredentials();
        AmazonS3Client s3Client = new AmazonS3Client(credentials);
        Region region = Region.getRegion(Regions.US_EAST_2);
        s3Client.setRegion(region);
        return s3Client;
    }*/

}

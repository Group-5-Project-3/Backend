package com.project3.project3.config;

import io.github.cdimascio.dotenv.Dotenv;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Bean
    public S3Client s3Client() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        // Retrieve credentials, using fallback from dotenv if necessary
        String accessKeyId = System.getenv("AWS_ACCESS_KEY_ID");
        String secretAccessKey = System.getenv("AWS_SECRET_ACCESS_KEY");
        String region = System.getenv("AWS_REGION");

        // Use Dotenv values if environment variables are not set
        accessKeyId = (accessKeyId != null && !accessKeyId.isEmpty()) ? accessKeyId : dotenv.get("AWS_ACCESS_KEY_ID");
        secretAccessKey = (secretAccessKey != null && !secretAccessKey.isEmpty()) ? secretAccessKey : dotenv.get("AWS_SECRET_ACCESS_KEY");
        region = (region != null && !region.isEmpty()) ? region : dotenv.get("AWS_REGION");

        // Check if any critical value is missing and throw an informative exception
        if (accessKeyId == null || secretAccessKey == null || region == null) {
            throw new IllegalArgumentException("AWS credentials or region are not set. Please check your environment variables or .env file.");
        }

        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretAccessKey);

        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }
}

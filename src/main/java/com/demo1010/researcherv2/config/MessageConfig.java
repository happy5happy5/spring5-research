package com.demo1010.researcherv2.config;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Slf4j
@Configuration
@Getter
@Setter
public class MessageConfig {

    @Value("${aws.accessKeyId}")
    private String awsAccessKey;
    @Value("${aws.secretKey}")
    private String awsSecretKey;
    @Value("${sms.accessKeyId}")
    private String smsAccessKey;
    @Value("${sms.secretKey}")
    private String smsSecretKey;
    @Value("${sms.domain}")
    private String domain;


    @Bean
    @Scope(value = "singleton") // 싱글톤 스코프로 설정
    public AmazonSimpleEmailService amazonSimpleEmailService() {
        log.info("Configuring Amazon SES");

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);

        return AmazonSimpleEmailServiceClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();
    }

    @Bean
    @Scope(value = "singleton") // 싱글톤 스코프로 설정
    public DefaultMessageService defaultMessageService() {
        log.info("Configuring DefaultMessageService");
        return new DefaultMessageService(smsAccessKey, smsSecretKey, domain);
    }
}

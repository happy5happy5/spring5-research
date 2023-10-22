//package com.demo1010.researcherv2.config;
//
//
//import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.sns.AmazonSNS;
//import com.amazonaws.services.sns.AmazonSNSClient;
//import com.amazonaws.services.sns.AmazonSNSClientBuilder;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Slf4j
//@Configuration
//@Getter
//@Setter
//public class AwsSnsConfig {
//
//    @Bean
//    public AmazonSNS amazonSNS() {
//        log.info("amazonSNS");
//        return AmazonSNSClientBuilder.standard()
//                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
//                .withRegion(Regions.AP_NORTHEAST_2)
//                .build();
//    }
//
//    @Bean
//    public AmazonSNSClient amazonSNSClient() {
//        log.info("amazonSNSClient");
//        return (AmazonSNSClient) AmazonSNSClientBuilder.standard()
//                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
//                .withRegion(Regions.AP_NORTHEAST_2)
//                .build();
//    }
//}
//

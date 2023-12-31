//package com.demo1010.researcherv2.service;
//
//
//import com.amazonaws.services.sns.AmazonSNSClient;
//import com.amazonaws.services.sns.model.*;
//import com.demo1010.researcherv2.entity.ApplicationUser;
//import com.demo1010.researcherv2.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class SnsServiceImpl implements SnsService {
//    private final AmazonSNSClient amazonSNSClient;
//    private final UserRepository userRepository;
//    @Override
//    public String createTopic(String topicName) {
//        CreateTopicRequest createTopicRequest = new CreateTopicRequest(topicName);
//        CreateTopicResult createTopicResult = amazonSNSClient.createTopic(createTopicRequest);
//        return createTopicResult.getTopicArn();
//    }
//    @Override
//    public String sendMail(String topicArn, String message, String subject) {
//        PublishRequest publishRequest = new PublishRequest()
//                .withTopicArn(topicArn)
//                .withMessage(message)
//                .withSubject(subject);
//        amazonSNSClient.publish(publishRequest);
//        return topicArn;
//    }
//    @Override
//    public String createSubscription(String topicArn, String username) {
//        ApplicationUser user = userRepository.findByUsername(username).orElseThrow();
//        SubscribeRequest subscribeRequest = new SubscribeRequest();
//        subscribeRequest.withTopicArn(topicArn)
//                .withProtocol("email")
//                .withEndpoint(user.getEmail());
//        SubscribeResult emailSubscribeResult = amazonSNSClient.subscribe(subscribeRequest);
//        return emailSubscribeResult.getSubscriptionArn();
//    }
//
//
//
//}
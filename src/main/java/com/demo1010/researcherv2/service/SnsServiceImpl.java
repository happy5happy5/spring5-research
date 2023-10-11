package com.demo1010.researcherv2.service;


import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.*;
import com.demo1010.researcherv2.entity.ApplicationUser;
import com.demo1010.researcherv2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SnsServiceImpl implements SnsService {

    private final AmazonSNSClient amazonSNSClient;
    private final UserRepository userRepository;

    @Override
    public String createTopic(String topicName) {
        CreateTopicRequest createTopicRequest = new CreateTopicRequest(topicName);
        CreateTopicResult createTopicResult = amazonSNSClient.createTopic(createTopicRequest);
        return createTopicResult.getTopicArn();
    }

    @Override
    public String sendMessage(String topicArn, String message) {
        PublishRequest publishRequest = new PublishRequest(topicArn, message);
        PublishResult publishResult = amazonSNSClient.publish(publishRequest);
        return publishResult.getMessageId();
    }

    @Override
    public String createSubscription(String topicArn, String username) {

        ApplicationUser user = userRepository.findByUsername(username).orElseThrow();
        SubscribeRequest subscribeRequest = new SubscribeRequest();
        subscribeRequest.withTopicArn(topicArn)
                .withProtocol("email")
                .withEndpoint(user.getEmail());
        SubscribeResult emailSubscribeResult = amazonSNSClient.subscribe(subscribeRequest);
        return emailSubscribeResult.getSubscriptionArn();
    }

//    public void pubTextSMS(String message, String phoneNumber) {
//        try {
//            PublishRequest request = PublishRequest
//
//            PublishResponse result = snsClient.publish(request);
//            System.out.println(result.messageId() + " Message sent. Status was " + result.sdkHttpResponse().statusCode());
//
//        } catch (SnsException e) {
//            System.err.println(e.awsErrorDetails().errorMessage());
//            System.exit(1);
//        }
//    }



}
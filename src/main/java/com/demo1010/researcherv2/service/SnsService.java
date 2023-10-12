package com.demo1010.researcherv2.service;

import com.amazonaws.services.sns.AmazonSNS;

import java.util.List;

public interface SnsService {

    String createTopic(String topicName);

    String sendMail(String topicArn, String message, String subject);

    String createSubscription(String topicArn, String username);
}

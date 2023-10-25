package com.demo1010.researcherv2.service;

import net.nurigo.sdk.message.response.SingleMessageSentResponse;

public interface MessageService {
    void sendEmail(String to, String subject, String content);

    SingleMessageSentResponse sendPhone(String from, String to, String text);

    void sendCodeToEmail(String email);

    void sendCodeToPhone(String phone);

    boolean checkCode(String ep, String code);
}

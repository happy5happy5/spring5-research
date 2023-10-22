package com.demo1010.researcherv2.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.demo1010.researcherv2.config.AwsSesConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SesServiceImpl implements SesService {


    private AwsSesConfig awsSesConfig;

    @Autowired
    public SesServiceImpl(AwsSesConfig awsSesConfig) {
        this.awsSesConfig = awsSesConfig;
    }

    @Override
    public void sendEmail(String to, String subject, String content) {
        SendEmailRequest request = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(to))
                .withMessage(new Message()
                        .withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(content)))
                        .withSubject(new Content().withCharset("UTF-8").withData(subject)))
                .withSource("zombil8731@gmail.com")
                .withConfigurationSetName("my-first-configuration-set");
        SendEmailResult result = awsSesConfig.amazonSimpleEmailService().sendEmail(request);
        log.info("Email sent to: " + to + " with result: " + result.getMessageId());
    }


}

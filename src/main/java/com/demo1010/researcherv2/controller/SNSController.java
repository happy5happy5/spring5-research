package com.demo1010.researcherv2.controller;

import com.amazonaws.services.sns.model.PublishRequest;
import com.demo1010.researcherv2.service.SnsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/sns")
public class SNSController {

    private final SnsService snsService;

    @PostMapping("/topic")
    public String sendMessage() {
//
        String topicArn = snsService.createTopic("testTopic");

        log.info("topicArn: {}", topicArn);

        return "redirect:/"; // 메시지를 보낸 후 홈페이지로 리다이렉트
    }

    @GetMapping("/subsms")
    public String subSms() {
//        topicArn: arn:aws:sns:ap-northeast-2:879689923226:testTopic
        snsService.createSubscription("arn:aws:sns:ap-northeast-2:879689923226:testTopic", "testUser");
        return "redirect:/";
    }

    @GetMapping("/sendsms")
    public String sendSms() {
        String temp = snsService.sendMail("arn:aws:sns:ap-northeast-2:879689923226:testTopic", "testMessage", "testSubject");
        log.info("sendSms: {}", temp);
        return "redirect:/";
    }
}

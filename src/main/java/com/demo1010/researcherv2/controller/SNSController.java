package com.demo1010.researcherv2.controller;

import com.amazonaws.services.sns.model.PublishRequest;
import com.demo1010.researcherv2.service.SnsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SNSController {

    private final SnsService snsService;

    @PostMapping("/send-message")
    public String sendMessage() {
        String message = "This is a test message.";
        String topicArn = "YOUR_SNS_TOPIC_ARN"; // 실제 SNS 주제 ARN으로 대체

        PublishRequest publishRequest = new PublishRequest()
                .withTopicArn(topicArn)
                .withMessage(message);

        snsService.amazonSNS().publish(publishRequest);

        return "redirect:/"; // 메시지를 보낸 후 홈페이지로 리다이렉트
    }
}

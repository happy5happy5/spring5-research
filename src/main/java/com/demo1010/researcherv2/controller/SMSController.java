package com.demo1010.researcherv2.controller;


import com.demo1010.researcherv2.service.MessageService;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sms")
public class SMSController {

    private final MessageService messageService;


    @GetMapping("/sendOne")
    public String sendOne(){
        SingleMessageSentResponse response = messageService.sendPhone("01012345678", "01012345678", "테스트 메시지");
        return response.toString();
    }
}

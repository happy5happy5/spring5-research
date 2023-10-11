package com.demo1010.researcherv2.controller;


import com.demo1010.researcherv2.service.SMSService;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sms")
public class SMSController {
    private final SMSService smsService;



    @GetMapping("/sendOne")
    public String sendOne(){
        SingleMessageSentResponse response = smsService.sendOne("01090281679","01040026862","아니 그게아니라 aws sns 에서 한국을 지원 안하더라");
        return response.toString();
    }

    @GetMapping("/sendMany")
    public String sendMany(){
//        smsService.sendMany();
        return "redirect:/";
    }

}

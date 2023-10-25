package com.demo1010.researcherv2.controller;

import com.demo1010.researcherv2.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ses")
public class SESController {

    private final MessageService messageService;

    @GetMapping("/send")
    public String send(@RequestParam String to) {
        log.info("[GET] /ses/send");
        messageService.sendEmail(to, "test", "test");
        return "good";
    }
}

package com.demo1010.researcherv2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home() {
        log.info("[GET] /");
        return "redirect:/research/list";
    }

    @GetMapping("/admin")
    public String admin() {
        log.info("[GET] /admin");
        return "pages/admin/admin";
    }

    @GetMapping("/user")
    public String user() {
        log.info("[GET] /user");
        return "pages/user/user";
    }

}

package com.demo1010.researcherv2.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String login() {
        log.info("[GET] /auth/login");
        return "pages/auth/login";
    }

//    @GetMapping("/logout")
//    public String logout() {
//        log.info("[GET] /auth/logout");
//        return "pages/auth/logout";
//    }


}

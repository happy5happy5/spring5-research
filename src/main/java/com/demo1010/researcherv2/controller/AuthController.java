package com.demo1010.researcherv2.controller;


import com.demo1010.researcherv2.model.ApiResponse;
import com.demo1010.researcherv2.model.RegistrationDTO;
import com.demo1010.researcherv2.service.AuthService;
import com.demo1010.researcherv2.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final MessageService messageService;
//    private final

    @GetMapping("/login")
    public String login() {
        log.info("[GET] /auth/login");
        return "pages/auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        log.info("[GET] /auth/register");
        model.addAttribute("registrationDTO", new RegistrationDTO());
        return "pages/auth/register";
    }

    @PostMapping("/register")
    public String register(RegistrationDTO registrationDTO, Model model) {
        log.info("[POST] /auth/register");
        RegistrationDTO registered = authService.register(registrationDTO);
        if(!Objects.equals(registered.getAttrName(), "success")) {
            model.addAttribute(registered.getAttrName(), registered.getAttrValue());
            model.addAttribute("registrationDTO", registrationDTO);
            return "pages/auth/register";
        }
        return "redirect:/auth/login";
    }

    @PostMapping("/code/phone")
    @ResponseBody
    public ApiResponse<String> sendCodeToPhone(@RequestBody String phone) {
        log.info("[POST] /auth/code/phone");
        messageService.sendCodeToPhone(phone);
        return ApiResponse.success("인증번호가 전송되었습니다.", phone);
    }

    @GetMapping("/validate/code")
    @ResponseBody
    public ApiResponse<String> sendCodeToPhoneGet(@RequestParam String code, @RequestParam String phone) {
        log.info("[GET] /auth/code/phone");
        boolean e = messageService.checkCode(phone, code);
        if(!e) {
            return ApiResponse.error(400, "인증번호가 일치하지 않습니다.");
        }
        return ApiResponse.success("인증번호가 일치합니다.", phone);
    }
}

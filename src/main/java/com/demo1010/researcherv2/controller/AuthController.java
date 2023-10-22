package com.demo1010.researcherv2.controller;


import com.demo1010.researcherv2.entity.Role;
import com.demo1010.researcherv2.model.RegistrationDTO;
import com.demo1010.researcherv2.repository.RoleRepository;
import com.demo1010.researcherv2.repository.UserRepository;
import com.demo1010.researcherv2.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;
import java.util.Set;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final AuthService authService;

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


}

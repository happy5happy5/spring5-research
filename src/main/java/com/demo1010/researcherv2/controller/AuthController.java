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

import java.util.Set;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

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
    public String register(RegistrationDTO registrationDTO) {
        log.info("[POST] /auth/register");
//        if(registrationDTO.isPasswordMatches()){
//            return "redirect:/auth/register?error=비밀번호가 일치하지 않습니다.";
//        }
        Set<Role> role =roleRepository.findByAuthority("ROLE_USER").map(Set::of).orElseThrow(() -> new RuntimeException("ROLE_ADMIN이 DB에 없습니다."));

        userRepository.save(registrationDTO.toEntity(registrationDTO,role));
        return "redirect:/auth/login";
    }


}

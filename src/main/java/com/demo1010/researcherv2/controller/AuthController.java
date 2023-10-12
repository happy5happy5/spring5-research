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
    public String register(RegistrationDTO registrationDTO, Model model) {
        log.info("[POST] /auth/register");
        Set<Role> role =roleRepository.findByAuthority("ROLE_USER").map(Set::of).orElseThrow(() -> new RuntimeException("ROLE_ADMIN이 DB에 없습니다."));
//        같은 유저가 있는지 확인
        if (userRepository.findByUsername(registrationDTO.getUsername()).isPresent()) {
            model.addAttribute("registrationDTO", registrationDTO);
            model.addAttribute("usernameError", "이미 존재하는 아이디 입니다.");
            return "pages/auth/register";
        }
        if (userRepository.findByEmail(registrationDTO.getEmail()).isPresent()) {
            model.addAttribute("registrationDTO", registrationDTO);
            model.addAttribute("emailError", "이미 존재하는 이메일 입니다.");
            return "pages/auth/register";
        }
        if(!registrationDTO.getPassword().equals(registrationDTO.getPasswordConfirm())){
            model.addAttribute("registrationDTO", registrationDTO);
            model.addAttribute("passwordError", "비밀번호가 일치하지 않습니다.");
            return "pages/auth/register";
        }
        if(!registrationDTO.getPhone().matches("^\\d{3}\\d{3,4}\\d{4}$")){
            model.addAttribute("registrationDTO", registrationDTO);
            model.addAttribute("phoneError", "전화번호 형식이 올바르지 않습니다.");
            return "pages/auth/register";
        }
//        전화번호 중복 금지
        if (userRepository.findByPhone(registrationDTO.getPhone()).isPresent()) {
            model.addAttribute("registrationDTO", registrationDTO);
            model.addAttribute("phoneError", "이미 존재하는 전화번호 입니다.");
            return "pages/auth/register";
        }

        if(!registrationDTO.getName().matches("^[가-힣]{2,5}$")){
            model.addAttribute("registrationDTO", registrationDTO);
            model.addAttribute("nameError", "이름은 한글로 2~5자 이어야 합니다.");
            return "pages/auth/register";
        }



        model.addAttribute("registrationDTO", registrationDTO);
        userRepository.save(registrationDTO.toEntity(registrationDTO,role));
        return "redirect:/auth/login";
    }


}

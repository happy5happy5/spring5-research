package com.demo1010.researcherv2.service;


import com.demo1010.researcherv2.entity.Role;
import com.demo1010.researcherv2.model.RegistrationDTO;
import com.demo1010.researcherv2.repository.RoleRepository;
import com.demo1010.researcherv2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService{

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    @Override
    public RegistrationDTO register(RegistrationDTO registrationDTO) {

        if (userRepository.findByUsername(registrationDTO.getUsername()).isPresent()) {
            registrationDTO.setAttrName("usernameError");
            registrationDTO.setAttrValue("이미 존재하는 아이디 입니다.");
            return registrationDTO;
        }
        if (userRepository.findByEmail(registrationDTO.getEmail()).isPresent()) {
            registrationDTO.setAttrName("emailError");
            registrationDTO.setAttrValue("이미 존재하는 이메일 입니다.");
            return registrationDTO;
        }
        if(!registrationDTO.getPassword().equals(registrationDTO.getPasswordConfirm())){
            registrationDTO.setAttrName("passwordError");
            registrationDTO.setAttrValue("비밀번호가 일치하지 않습니다.");
            return registrationDTO;
        }
        if(!registrationDTO.getPhone().matches("^\\d{3}\\d{3,4}\\d{4}$")){
            registrationDTO.setAttrName("phoneError");
            registrationDTO.setAttrValue("전화번호 형식이 올바르지 않습니다.");
            return registrationDTO;
        }
//        전화번호 중복 금지
        if (userRepository.findByPhone(registrationDTO.getPhone()).isPresent()) {
            registrationDTO.setAttrName("phoneError");
            registrationDTO.setAttrValue("이미 존재하는 전화번호 입니다.");
            return registrationDTO;
        }

        if(!registrationDTO.getName().matches("^[가-힣]{2,5}$")){
            registrationDTO.setAttrName("nameError");
            registrationDTO.setAttrValue("이름은 한글로 2~5자 이어야 합니다.");
            return registrationDTO;
        }
        Set<Role> role =roleRepository.findByAuthority("ROLE_USER").map(Set::of).orElseThrow(() -> new RuntimeException("ROLE_ADMIN이 DB에 없습니다."));
        userRepository.save(registrationDTO.toEntity(registrationDTO,role));
        registrationDTO.setAttrName("success");
        registrationDTO.setAttrValue("회원가입에 성공하였습니다.");
        return registrationDTO;
    }
}

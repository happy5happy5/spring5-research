package com.demo1010.researcherv2.model;


import javax.validation.constraints.*;

import com.demo1010.researcherv2.entity.ApplicationUser;
import com.demo1010.researcherv2.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
//@PasswordMatches(message = "비밀번호가 일치하지 않습니다.")
public class RegistrationDTO {
//    @NotEmpty(message = "아이디는 필수 입력 값 입니다.")
//    @NotBlank(message = "아이디는 필수 입력 값 입니다.")
    private String username;
//    @NotEmpty(message = "이메일은 필수 입력 값 입니다.")
//    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
//    @NotEmpty(message = "비밀번호는 필수 입력 값 입니다.")
    private String password;
//    @NotEmpty(message = "비밀번호 확인은 필수 입력 값 입니다.")
//    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).*$", message = "비밀번호는 숫자, 소문자, 대문자, 특수 문자를 포함해야 합니다.")
    private String passwordConfirm;
//    @NotEmpty(message = "이름은 필수 입력 값 입니다.")
    private String name;
//    @NotEmpty(message = "전화번호는 필수 입력 값 입니다.")
    private String phone;
    private String gender;
    private String attrName;
    private String attrValue;

    public void setPhone(String phone) {
        this.phone = phone.replaceAll("-", "");
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

//    compare password and passwordConfirm
//    public boolean isPasswordMatches() {
//        return this.password.equals(this.passwordConfirm);
//    }

    public ApplicationUser toEntity(RegistrationDTO dto, Set<Role> role){
        ApplicationUser user = new ApplicationUser();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setName(dto.getName());
        user.setPhone(dto.getPhone());
        user.setAuthorities(role);
        return user;
    }
}

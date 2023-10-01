package com.demo1010.researcherv2.model;

import com.demo1010.researcherv2.entity.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private ApplicationUser applicationUser;
    private String token;
}
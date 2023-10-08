package com.demo1010.researcherv2.entity;

import javax.persistence.*;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    private String authority;


    public Role(String authority){
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

}
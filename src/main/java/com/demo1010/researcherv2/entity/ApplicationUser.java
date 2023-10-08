package com.demo1010.researcherv2.entity;


import javax.persistence.*;

import com.demo1010.researcherv2.model.RegistrationDTO;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class ApplicationUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @Column(unique = true)
    private String username;
    private String email;
    private String password;
    private String name;
    private String phone;

    // 다대일 관계 설정
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role_junction", joinColumns = @JoinColumn(name="user_id") , inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> authorities;

    public ApplicationUser(RegistrationDTO dto){

        this.username = dto.getUsername();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.name = dto.getName();
        this.phone =dto.getPhone();
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : this.authorities) {
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}


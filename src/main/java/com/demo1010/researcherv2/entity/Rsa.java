package com.demo1010.researcherv2.entity;

import javax.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Rsa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rsa_seq")
    private Integer rsa_seq;
    private Integer rs_seq;
    private String username;
    private String email_arn;
    private String topic_arn;

}

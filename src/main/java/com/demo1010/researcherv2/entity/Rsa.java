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

    //    foreign key 설정
    //    rs_seq
//    @ManyToOne
//    @JoinColumn(name = "rs_seq", insertable = false, updatable = false)
//    private Rs rs;

    //    username
//    @ManyToOne
//    @JoinColumn(name = "username", insertable = false, updatable = false)
//    private ApplicationUser applicationUser;

}

package com.demo1010.researcherv2.entity;

import javax.persistence.*;

import lombok.*;

import java.util.Set;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Rsr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rsr_seq;

    private Integer rs_seq;
    private Integer rsi_no;
    private String rsi_type;
    private Integer rsr_total;
    private Integer rsr_type0_1;
    private Integer rsr_type0_2;
    private Integer rsr_type0_3;
    private Integer rsr_type0_4;
    private Integer rsr_type0_5;
    private String rsr_type0_etc_yn;
    private Integer rsr_type1_o;
    private Integer rsr_type1_x;
    private Integer rsr_type2;
    private String rsr_type3_yn;
    private Integer rsr_type4;

    // 다대다 관계 설정
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_rsr_junction", joinColumns = @JoinColumn(name="rs_seq") , inverseJoinColumns = @JoinColumn(name="user_id"))
    private Set<ApplicationUser> answers;
}


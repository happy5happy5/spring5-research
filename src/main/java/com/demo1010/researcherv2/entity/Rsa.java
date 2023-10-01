package com.demo1010.researcherv2.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rsa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rsa_seq;

    private Integer rs_seq;
    private Integer rsi_seq;
    private Integer user_id;
    private Integer rsi_no;
    private String rsi_type;
    private String rsa_type0;
    private String rsa_type0_etc;
    private String rsa_type1;
    private String rsa_type2;
    private String rsa_type3;
    private String rsa_type4;

    // 다대일 관계 설정
    @ManyToOne
    @JoinColumn(name = "rs_seq", referencedColumnName = "rs_seq", insertable = false, updatable = false)
    private Rs rs;

    // 다대일 관계 설정
    @ManyToOne
    @JoinColumn(name = "rsi_seq", referencedColumnName = "rsi_seq", insertable = false, updatable = false)
    private Rsi rsi;

    // 다대일 관계 설정
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private ApplicationUser user;

}

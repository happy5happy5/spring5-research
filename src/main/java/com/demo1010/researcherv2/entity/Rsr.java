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
public class Rsr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rsr_seq;

    private Integer rs_seq;
    private Integer rsi_no;
    private String rsi_type;
    private String rsr_total;
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

    // 다대일 관계 설정
    @ManyToOne
    @JoinColumn(name = "rs_seq", referencedColumnName = "rs_seq", insertable = false, updatable = false)
    private Rs rs;

}


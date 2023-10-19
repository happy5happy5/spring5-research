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

    //    foreign key 설정
//    rs_seq
//    @ManyToOne
//    @JoinColumn(name = "rs_seq", insertable = false, updatable = false)
//    private Rs rs;

    //    rsi_no
    //    rsi_type
//    @ManyToOne
//    @JoinColumn(name = "rsi_no", insertable = false, updatable = false)
//    @JoinColumn(name = "rsi_type", insertable = false, updatable = false)
//    private Rsi rsi;



}



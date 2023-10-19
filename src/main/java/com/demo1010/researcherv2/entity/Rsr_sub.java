package com.demo1010.researcherv2.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Rsr_sub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rsr_sub_seq;

    private Integer rsr_seq;
    private Integer rs_seq;
    private Integer rsi_no;
    private String rsr_sub_etc;
    private String rsr_sub_type3;

    //    foreign key 설정
//    rsr_seq
//    @ManyToOne
//    @JoinColumn(name = "rsr_seq", insertable = false, updatable = false)
//    private Rsr rsr;

    //    rs_seq
//    @ManyToOne
//    @JoinColumn(name = "rs_seq", insertable = false, updatable = false)
//    private Rs rs;

    //    rsi_no
//    @ManyToOne
//    @JoinColumn(name = "rsi_no", insertable = false, updatable = false)
//    private Rsi rsi;

}


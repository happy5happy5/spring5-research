package com.demo1010.researcherv2.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Rsr_sub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rsr_sub_seq;

    private Integer rs_seq;
    private Integer rsi_seq;
    private String rsi_no;
    private String rsr_sub_etc;
    private String rsr_sub_type3;

    // 다대일 관계 설정
//    @ManyToOne
//    @JoinColumn(name = "rs_seq", referencedColumnName = "rs_seq", insertable = false, updatable = false)
//    private Rs rs;
//
//     다대일 관계 설정
//    @ManyToOne
//    @JoinColumn(name = "rsi_seq", referencedColumnName = "rsi_seq", insertable = false, updatable = false)
//    private Rsi rsi;

}


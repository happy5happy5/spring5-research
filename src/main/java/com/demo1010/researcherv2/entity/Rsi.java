package com.demo1010.researcherv2.entity;

import javax.persistence.*;

import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Rsi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rsi_seq; // 질문 번호

    private Integer rs_seq; // 설문지 번호
    private String rsi_question; // 질문 내용
    private Integer rsi_no; // 질문 번호
    private String rsi_type; // 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
    private String rsi_type0_1; // 객관식 1번 실제 질문 내용
    private String rsi_type0_2; // 객관식 2번 실제 질문 내용
    private String rsi_type0_3; // 객관식 3번 실제 질문 내용
    private String rsi_type0_4; // 객관식 4번 실제 질문 내용
    private String rsi_type0_5; // 객관식 5번 실제 질문 내용
    private String rsi_type0_etc; // 기타 입력란이 있는지 여부
    private String rsi_type1; // OX 인지 여부
    private String rsi_type2; // likert 인지 여부
    private String rsi_type3; // 주관식 인지 여부
    private String rsi_type4; // 별점 인지 여부
    private String rsi_type5; // 다중 선택 몇개?
    @Column(columnDefinition = "text")
    private String html_data; // html 데이터

    // 다대일 관계 설정
//    @ManyToOne
//    @JoinColumn(name = "rs_seq", referencedColumnName = "rs_seq", insertable = false, updatable = false)
//    private Rs rs;

    // 일대다 관계 설정
//    @OneToMany(mappedBy = "rsi", cascade = CascadeType.ALL)
//    private List<Rsa> rsaList;

}


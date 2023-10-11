package com.demo1010.researcherv2.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Rs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rs_seq")
    private Integer rs_seq;
    private String username;
    private String rs_title;
    private String rs_desc;
    private Integer rs_cnt;
    private String rs_start_date;
    private String rs_end_date;
    private String use_yn;
    private Integer hits;
    @Column(columnDefinition = "text")
    private String html_data; // html 데이터
    private String topic_arn; // SNS 토픽 ARN

}


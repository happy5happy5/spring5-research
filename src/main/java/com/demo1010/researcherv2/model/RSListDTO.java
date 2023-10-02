package com.demo1010.researcherv2.model;

import lombok.Data;

@Data
public class RSListDTO {

    private Integer rs_seq;
    private String username;
    private String rs_title;
    private String rs_desc;
    private Integer rs_cnt;
    private String rs_start_date;
    private String rs_end_date;
    private String use_yn;
    private Integer hits;


}

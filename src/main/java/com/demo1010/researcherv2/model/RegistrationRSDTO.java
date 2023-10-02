package com.demo1010.researcherv2.model;

import lombok.Data;

import java.util.List;

@Data
public class RegistrationRSDTO {

    private int rs_seq;
    private String username;
    private int rs_cnt;
    private int hits;
    private String rs_desc;
    private String rs_end_date;
    private String rs_start_date;
    private String rs_title;
    private String use_yn;
    private List<RSIListDTO> rsiList;

}

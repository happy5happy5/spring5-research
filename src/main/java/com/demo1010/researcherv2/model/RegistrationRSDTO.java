package com.demo1010.researcherv2.model;

import lombok.Data;

import java.util.ArrayList;
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
    private List<RSIDTO> rsiList;

    public RegistrationRSDTO() {
        this.rs_seq = 0;
        this.username = "";
        this.rs_cnt = 0;
        this.hits = 0;
        this.rs_desc = "";
        this.rs_end_date = "";
        this.rs_start_date = "";
        this.rs_title = "";
        this.use_yn = "";
        List<RSIDTO> rsiDTO = new ArrayList<>();
        rsiDTO.add(new RSIDTO());
        this.rsiList = rsiDTO;
    }

}

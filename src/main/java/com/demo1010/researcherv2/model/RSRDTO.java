package com.demo1010.researcherv2.model;

import com.demo1010.researcherv2.entity.Rsr;
import lombok.Data;

@Data
public class RSRDTO {

    private Integer rs_seq;
    private String type;
    private String no;
    private String username;
    private String rsi_answer;

}

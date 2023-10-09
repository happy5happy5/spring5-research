package com.demo1010.researcherv2.model;


import lombok.Data;

import java.util.List;

@Data
public class RegistrationRSRDTO {
//    let data = {
//            rs_seq: research.rs_seq,
//    username: userName,
//    answerList: answerList
//}
    private Integer rs_seq;
    private String username;
    private List<RSRDTO> answerList;

}

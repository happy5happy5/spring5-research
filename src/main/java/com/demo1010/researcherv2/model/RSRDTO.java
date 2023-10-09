package com.demo1010.researcherv2.model;

import com.demo1010.researcherv2.entity.Rsr;
import lombok.Data;

@Data
public class RSRDTO {

//    let answerList = [
//    {
//        rs_seq: research.rs_seq,
//                username: userName
//    },
//            // 주의할 점은 type5 도 type0 형태로 저장 된다.
//            // {
//            //     type: "0",
//            //     no: "1",
//            //     answerList: []
//            // }
//            ];
    private Integer rs_seq;
    private String username;
    private String type;
    private String no;
    private String[] answerList;


//    toEntity
    public Rsr toEntity() {
        Rsr rsr = new Rsr();
        rsr.setRs_seq(rs_seq);
        rsr.setRsi_no(Integer.parseInt(no));
        rsr.setRsi_type(type);
//        rsr.setRsr_total(String.join(",", answerList));
        switch (type){
            case "0":
                switch (answerList[0]){
                    case "1":
                        rsr.setRsr_type0_1(1);
                        break;
                    case "2":
                        rsr.setRsr_type0_2(1);
                        break;
                    case "3":
                        rsr.setRsr_type0_3(1);
                        break;
                    case "4":
                        rsr.setRsr_type0_4(1);
                        break;
                    case "5":
                        rsr.setRsr_type0_5(1);
                        break;
                }
                break;
            case "1":
                switch (answerList[1]){
                    case "1":
                        rsr.setRsr_type1_o(1);
                        break;
                    case "2":
                        rsr.setRsr_type1_x(1);
                        break;
                }
                break;
            case "2":
                rsr.setRsr_type2(Integer.parseInt(answerList[1]));
                break;
            case "3":
                rsr.setRsr_type3_yn("Y");
                break;
            case "4":
                rsr.setRsr_type4(Integer.parseInt(answerList[0]));
                break;
            case "5":

                break;
        }

        return rsr;
    }

}

package com.demo1010.researcherv2.model;

import com.demo1010.researcherv2.entity.Rsi;
import lombok.Data;

import java.util.List;

@Data
public class RSIDTO {

    private String type;
    private List<String> data_input;
    private List<String> data_textarea;
    private String html;

    public Rsi toEntity(int seq,int no) {
        Rsi rsi = new Rsi();
        rsi.setRs_seq(seq);
        rsi.setRsi_no(no);
        rsi.setRsi_type(type);
        rsi.setHtml_data(html);



        // 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
        switch (type) {
            case "0", "5":
                //        data_textarea 에 비어있는 값이 존재한다면 삭제 한다
                for (int i = 0; i < data_textarea.size(); i++) {
                    if (data_textarea.get(i).isEmpty()) {
                        data_textarea.remove(i);
                        i--;
                    }
                }
//                                rsi.setRsi_type("객관식");
                for (int i = 0; i < data_textarea.size(); i++) {
                    switch (i) {
                        case 0:
                            rsi.setRsi_question(data_textarea.get(i));
                            break;
                        case 1:
                            rsi.setRsi_type0_1(data_textarea.get(i));
                            break;
                        case 2:
                            rsi.setRsi_type0_2(data_textarea.get(i));
                            break;
                        case 3:
                            rsi.setRsi_type0_3(data_textarea.get(i));
                            break;
                        case 4:
                            rsi.setRsi_type0_4(data_textarea.get(i));
                            break;
                        case 5:
                            rsi.setRsi_type0_5(data_textarea.get(i));
                            break;
                    }
                }
                if (type.equals("5")) {
                    rsi.setRsi_type5(data_input.get(0));
                }

                break;
            case "1", "2", "3", "4":
                rsi.setRsi_question(data_textarea.get(0));
                break;
        }
        return rsi;
    }


}

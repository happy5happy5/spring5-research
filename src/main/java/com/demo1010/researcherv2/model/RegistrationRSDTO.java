package com.demo1010.researcherv2.model;

import com.demo1010.researcherv2.entity.ApplicationUser;
import com.demo1010.researcherv2.entity.Rs;
import lombok.Data;

import java.util.List;

@Data
public class RegistrationRSDTO {
    private String username;
    private List<String> data_input;
    private List<String> data_textarea;
    private String html;
    private List<RSIDTO> content;

    public Rs toEntity() {
        Rs rs = new Rs();
        rs.setApplicationUser(new ApplicationUser());
        rs.getApplicationUser().setUsername(username);
        rs.setHtml_data(html);
        rs.setRs_title(data_textarea.get(0));
        rs.setRs_desc(data_textarea.get(1));
        rs.setRs_start_date(data_input.get(0));
        rs.setRs_end_date(data_input.get(1));
        rs.setRs_cnt(content.size());
        rs.setHits(0);
        rs.setUse_yn("N");
        return rs;
    }
}



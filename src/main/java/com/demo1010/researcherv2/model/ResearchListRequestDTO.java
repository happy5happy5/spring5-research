package com.demo1010.researcherv2.model;

import lombok.Data;

@Data
public class ResearchListRequestDTO {
    private int page;
    private int size;
    private String sort;
    private String order;
    private String searchType;
    private String searchKeyword;

    public ResearchListRequestDTO() {
        this.page = 0;
        this.size = 10;
        this.sort = "rs_seq";
        this.order = "rs_seq";
        this.searchType = "title";
        this.searchKeyword = "";
    }

}

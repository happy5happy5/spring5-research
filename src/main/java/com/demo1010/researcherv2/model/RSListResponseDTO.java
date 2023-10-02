package com.demo1010.researcherv2.model;

import lombok.Data;

import java.util.List;

@Data
public class RSListResponseDTO {

    private List<RSDTO> rsList;
    private int totalPage;
    private int currentPage;
    private int totalSize;
    private int currentSize;
    private boolean hasNext;
    private boolean hasPrevious;
    private boolean isFirst;
    private boolean isLast;
    private boolean isEmpty;
    private boolean hasContent;
    private boolean hasElements;

}

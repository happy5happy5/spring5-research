package com.demo1010.researcherv2.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RegistrationRSDTO {
    private List<ContentsDTO> content;

}

@Data
class ContentsDTO {
    private String type;

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<String> data_input;

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<String> data_textarea;
    private String html;
}

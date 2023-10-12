package com.demo1010.researcherv2.service;


import com.demo1010.researcherv2.entity.Rs;
import com.demo1010.researcherv2.model.RegistrationRSDTO;
import com.demo1010.researcherv2.model.RegistrationRSRDTO;

public interface ResearchService {
    Rs createResearch(RegistrationRSDTO registrationRSDTO);

    void createResponse(RegistrationRSRDTO registrationRSRDTO);

    void deleteResearch(Integer rsSeq);
}

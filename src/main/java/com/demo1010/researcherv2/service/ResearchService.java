package com.demo1010.researcherv2.service;


import com.demo1010.researcherv2.model.RegistrationRSDTO;
import com.demo1010.researcherv2.model.RegistrationRSRDTO;

public interface ResearchService {
    void createResearch(RegistrationRSDTO registrationRSDTO);

    void createResponse(RegistrationRSRDTO registrationRSRDTO);
}

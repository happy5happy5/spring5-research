package com.demo1010.researcherv2.service;

import com.demo1010.researcherv2.entity.Rs;
import com.demo1010.researcherv2.model.RegistrationRSDTO;
import com.demo1010.researcherv2.repository.RsRepository;
import com.demo1010.researcherv2.repository.RsiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResearchServiceImpl implements ResearchService {

    private final RsiRepository rsiRepository;
    private final RsRepository rsRepository;


    @Override
    public void createResearch(RegistrationRSDTO registrationRSDTO) {
        Rs rs = rsRepository.save(registrationRSDTO.toEntity());
//        rs.getRs_seq();

        for (int i = 0; i < registrationRSDTO.getContent().size(); i++) {
            rsiRepository.save(registrationRSDTO.getContent().get(i).toEntity( rs.getRs_seq(),i+1));
        }
    }
}

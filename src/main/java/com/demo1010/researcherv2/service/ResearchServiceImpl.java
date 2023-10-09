package com.demo1010.researcherv2.service;

import com.demo1010.researcherv2.entity.Rs;
import com.demo1010.researcherv2.entity.Rsr;
import com.demo1010.researcherv2.model.RegistrationRSDTO;
import com.demo1010.researcherv2.model.RegistrationRSRDTO;
import com.demo1010.researcherv2.repository.RsRepository;
import com.demo1010.researcherv2.repository.RsiRepository;
import com.demo1010.researcherv2.repository.RsrRepository;
import com.demo1010.researcherv2.repository.RsrSubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResearchServiceImpl implements ResearchService {

    private final RsiRepository rsiRepository;
    private final RsRepository rsRepository;
    private final RsrRepository rsrRepository;
    private final RsrSubRepository rsrSubRepository;


    @Override
    public void createResearch(RegistrationRSDTO registrationRSDTO) {
        Rs rs = rsRepository.save(registrationRSDTO.toEntity());
//        rs.getRs_seq();

        for (int i = 0; i < registrationRSDTO.getContent().size(); i++) {
            rsiRepository.save(registrationRSDTO.getContent().get(i).toEntity( rs.getRs_seq(),i+1));
        }
    }

    @Override
    public void createResponse(RegistrationRSRDTO registrationRSRDTO) {
        Integer rs_seq = registrationRSRDTO.getRs_seq();
        String username = registrationRSRDTO.getUsername();
        List<Rsr> answerList = rsrRepository.findAllByRsSeqOrderByRsi_noAsc(rs_seq);
//        log.info("answerList: {}", answerList);

    }
}

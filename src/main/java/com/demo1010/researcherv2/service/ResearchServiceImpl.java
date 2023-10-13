package com.demo1010.researcherv2.service;

import com.demo1010.researcherv2.entity.Rs;
import com.demo1010.researcherv2.entity.Rsa;
import com.demo1010.researcherv2.entity.Rsr;
import com.demo1010.researcherv2.entity.Rsr_sub;
import com.demo1010.researcherv2.model.RSRDTO;
import com.demo1010.researcherv2.model.RegistrationRSDTO;
import com.demo1010.researcherv2.model.RegistrationRSRDTO;
import com.demo1010.researcherv2.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResearchServiceImpl implements ResearchService {
    private final RsaRepository rsaRepository;

    private final RsiRepository rsiRepository;
    private final RsRepository rsRepository;
    private final RsrRepository rsrRepository;
    private final RsrSubRepository rsrSubRepository;
//    private final SnsService snsService;


    @Override
    public Rs createResearch(RegistrationRSDTO registrationRSDTO) {
        Rs rs = rsRepository.save(registrationRSDTO.toEntity());
//        String topicArn = snsService.createTopic("rs_seq"+rs.getRs_seq().toString());
//        rs.setTopic_arn(topicArn);
        rsRepository.save(rs);
        for (int i = 0; i < registrationRSDTO.getContent().size(); i++) {
            rsiRepository.save(registrationRSDTO.getContent().get(i).toEntity( rs.getRs_seq(),i+1));
        }
        return rs;
    }

    @Override
    public void createResponse(RegistrationRSRDTO registrationRSRDTO) {
        Integer rs_seq = registrationRSRDTO.getRs_seq();
        String username = registrationRSRDTO.getUsername();
        List<RSRDTO> answerList = registrationRSRDTO.getAnswerList();
        List<Rsr> existAnswerList = rsrRepository.findAllByRsSeqOrderByRsi_noAsc(rs_seq);
        log.info("[createResponse] answerList : {}", answerList);
//        전부 있거나 전부 없는 경우 뿐이다 -> 중간 수정이 안되기 때문임 ㅇㅇ -> 수정이 필요 하면 삭제 후 재작성을 해야함
//        전부 없는 경우
        if (existAnswerList.isEmpty()) {
            for (int i = 0; i < answerList.size(); i++) {
                Rsr rsr = new Rsr();
                rsr.setRsr_type0_1(0);
                rsr.setRsr_type0_2(0);
                rsr.setRsr_type0_3(0);
                rsr.setRsr_type0_4(0);
                rsr.setRsr_type0_5(0);
                rsr.setRsr_type0_etc_yn("N");
                rsr.setRsr_type1_o(0);
                rsr.setRsr_type1_x(0);
                rsr.setRsr_type2(0);
                rsr.setRsr_type3_yn("N");
                rsr.setRsr_type4(0);
                rsr.setRsr_total(0);
                rsr.setRs_seq(rs_seq);
                rsr.setRsi_no(i + 1);
                String type = answerList.get(i).getType();
                rsr.setRsi_type(type);
                String answeredQuestion = answerList.get(i).getRsi_answer();
                switch (type) {
                    // 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
                    case "0":
                        switch (answeredQuestion) {
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
                            default:
                                throw new IllegalStateException("Unexpected value: " + answeredQuestion);
                        }
                        break;
                    // 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
                    case "1":
                        switch (answeredQuestion) {
                            case "1":
                                rsr.setRsr_type1_o(1);
                                break;
                            case "2":
                                rsr.setRsr_type1_x(1);
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + answeredQuestion);
                        }
                        break;
                    // 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
                    case "2":
                        switch (answeredQuestion) {
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
                    // 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
                    case "3":
                        rsr.setRsr_type3_yn("Y");
                        break;
                    // 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
                    case "4":
                        rsr.setRsr_type4(Integer.parseInt(answeredQuestion));
                        break;
                    // 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
                    case "5":
                        String[] split = answeredQuestion.split(",");
                        for (String s : split) {
                            switch (s) {
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
                                default:
                                    throw new IllegalStateException("Unexpected value: " + s);
                            }
                        }
                        break;
                }
                rsr.setRsr_total(1);
                rsrRepository.save(rsr);
                if(type.equals("3")){
                    Rsr_sub rsrSub = new Rsr_sub();
                    rsrSub.setRsr_seq(rsr.getRsr_seq());
                    rsrSub.setRsi_no(Integer.valueOf(answerList.get(i).getNo()));
                    rsrSub.setRs_seq(rsr.getRs_seq());
                    rsrSub.setRsr_sub_type3(answeredQuestion);
                    rsrSubRepository.save(rsrSub);
                }
            }
//            전부 있는 경우
        } else {
            for (int i = 0; i < answerList.size(); i++) {
                Rsr rsr = rsrRepository.findByRsSeqAndRsiNo(rs_seq, i + 1);
                String type = answerList.get(i).getType();
                if(!rsr.getRsi_type().equals(type)){
//                    매우 매우 매우 매우 잘못된 경우 (데이터가 꼬였음 절대 발생하면 안됨)
                    throw new IllegalStateException("[Welcome! HellGate Open HellGate Open HellGate Open HellGate Open HellGate Open]Unexpected value: " + type);
                }
                String answeredQuestion = answerList.get(i).getRsi_answer();
                switch (type){
                    // 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
                    case "0":
                        switch (answeredQuestion){
                            case "1":
                                rsr.setRsr_type0_1(rsr.getRsr_type0_1()+1);
                                break;
                            case "2":
                                rsr.setRsr_type0_2(rsr.getRsr_type0_2()+1);
                                break;
                            case "3":
                                rsr.setRsr_type0_3(rsr.getRsr_type0_3()+1);
                                break;
                            case "4":
                                rsr.setRsr_type0_4(rsr.getRsr_type0_4()+1);
                                break;
                            case "5":
                                rsr.setRsr_type0_5(rsr.getRsr_type0_5()+1);
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + answeredQuestion);
                        }
                        break;
                    // 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
                    case "1":
                        switch (answeredQuestion){
                            case "1":
                                rsr.setRsr_type1_o(rsr.getRsr_type1_o()+1);
                                break;
                            case "2":
                                rsr.setRsr_type1_x(rsr.getRsr_type1_x()+1);
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + answeredQuestion);
                        }
                        break;
                    // 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
                    case "2":
                        switch (answeredQuestion){
                            case "1":
                                rsr.setRsr_type0_1(rsr.getRsr_type0_1()+1);
                                break;
                            case "2":
                                rsr.setRsr_type0_2(rsr.getRsr_type0_2()+1);
                                break;
                            case "3":
                                rsr.setRsr_type0_3(rsr.getRsr_type0_3()+1);
                                break;
                            case "4":
                                rsr.setRsr_type0_4(rsr.getRsr_type0_4()+1);
                                break;
                            case "5":
                                rsr.setRsr_type0_5(rsr.getRsr_type0_5()+1);
                                break;
                        }
                        break;
                    // 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
                    case "3":
                        rsr.setRsr_type3_yn("Y");
                        Rsr_sub rsrSub = new Rsr_sub();
                        rsrSub.setRsr_seq(rsr.getRsr_seq());
                        rsrSub.setRsi_no(Integer.valueOf(answerList.get(i).getNo()));
                        rsrSub.setRs_seq(rsr.getRs_seq());
                        rsrSub.setRsr_sub_type3(answeredQuestion);
                        rsrSubRepository.save(rsrSub);
                        break;
                    // 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
                    case "4":
                        rsr.setRsr_type4(rsr.getRsr_type4()+Integer.parseInt(answeredQuestion));
                        break;
                    // 0: 객관식 1: OX 2: likert 3: 주관식 4: 별점 5: 다중선택
                    case "5":
                        String[] split = answeredQuestion.split(",");
                        for (String s : split) {
                            switch (s) {
                                case "1":
                                    rsr.setRsr_type0_1(rsr.getRsr_type0_1() + 1);
                                    break;
                                case "2":
                                    rsr.setRsr_type0_2(rsr.getRsr_type0_2() + 1);
                                    break;
                                case "3":
                                    rsr.setRsr_type0_3(rsr.getRsr_type0_3() + 1);
                                    break;
                                case "4":
                                    rsr.setRsr_type0_4(rsr.getRsr_type0_4() + 1);
                                    break;
                                case "5":
                                    rsr.setRsr_type0_5(rsr.getRsr_type0_5() + 1);
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected value: " + s);
                            }
                        }
                        break;
                }
                rsr.setRsr_total(rsr.getRsr_total()+1);
                rsrRepository.save(rsr);
            }
        }
        Rsa rsa = new Rsa();
        rsa.setRs_seq(rs_seq);
        rsa.setUsername(username);
//        rsa.setTopic_arn(rsRepository.findByRsSeq(rs_seq).getTopic_arn());
//        String arn = snsService.createSubscription(rsa.getTopic_arn(), username);
//        rsa.setEmail_arn(arn);
        rsaRepository.save(rsa);
    }

    @Override
    public void deleteResearch(Integer rsSeq) {
//        rsrRepository.deleteAllByRsSeq(rsSeq);
//        rsrSubRepository.deleteAllByRsSeq(rsSeq);
//        rsaRepository.deleteAllByRsSeq(rsSeq);
//        rsiRepository.deleteAllByRsSeq(rsSeq);
        rsRepository.deleteById(rsSeq);
    }
}

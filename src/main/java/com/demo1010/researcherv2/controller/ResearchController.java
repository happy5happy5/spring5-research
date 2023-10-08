package com.demo1010.researcherv2.controller;


import com.demo1010.researcherv2.entity.Rs;
import com.demo1010.researcherv2.model.*;
import com.demo1010.researcherv2.repository.RsRepository;
import com.demo1010.researcherv2.service.ResearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/research")
public class ResearchController {

    private final RsRepository rsRepository;
    private final ResearchService researchService;

    @GetMapping("/list")
    public String list(Model model, RSListRequestDTO requestDTO) {
        log.info("[GET] /research/list");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        model.addAttribute("userRoles", roles);
        model.addAttribute("username", auth.getName());

        return "pages/research/listform";
    }

    @GetMapping("/list2")
    @ResponseBody
    public RSListResponseDTO list2(RSListRequestDTO requestDTO) {
        log.info("[GET] /research/list2");

        // 페이지와 사이즈를 이용해 데이터를 가져오도록 수정
        int page = requestDTO.getPage() - 1; // 페이지 번호는 0부터 시작하므로 1을 빼줍니다.
        int size = requestDTO.getSize();
        Sort sort = Sort.by(Sort.Direction.DESC, "rs_seq");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Rs> rsPage = rsRepository.searchList(pageable);

        RSListResponseDTO responseDTO = new RSListResponseDTO();
        List<RSDTO> rsList = getResearchListDTOS(rsPage);
        responseDTO.setRsList(rsList);
        responseDTO.setTotalPage(rsPage.getTotalPages());
        responseDTO.setCurrentPage(rsPage.getNumber());
        responseDTO.setTotalSize((int) rsPage.getTotalElements());
        responseDTO.setCurrentSize(rsPage.getNumberOfElements());
        responseDTO.setHasNext(rsPage.hasNext());
        responseDTO.setHasPrevious(rsPage.hasPrevious());
        responseDTO.setFirst(rsPage.isFirst());
        responseDTO.setLast(rsPage.isLast());
        responseDTO.setEmpty(rsPage.isEmpty());
        responseDTO.setHasContent(rsPage.hasContent());
        responseDTO.setHasElements(rsPage.hasContent());


        return responseDTO;
    }

    private static List<RSDTO> getResearchListDTOS(Page<Rs> rsPage) {
        List<RSDTO> rsList = new ArrayList<>();
        for (Rs rs : rsPage.getContent()) {
            RSDTO researchListDTO = new RSDTO();
            researchListDTO.setRs_seq(rs.getRs_seq());
            researchListDTO.setUsername(rs.getUsername());
            researchListDTO.setRs_title(rs.getRs_title());
            researchListDTO.setRs_desc(rs.getRs_desc());
            researchListDTO.setRs_cnt(rs.getRs_cnt());
            researchListDTO.setRs_start_date(rs.getRs_start_date());
            researchListDTO.setRs_end_date(rs.getRs_end_date());
            researchListDTO.setUse_yn(rs.getUse_yn());
            researchListDTO.setHits(rs.getHits());
            rsList.add(researchListDTO);
        }
        return rsList;
    }


    @GetMapping("/create")
    public String create(Model model) {
        log.info("[GET] /research/create");
        return "pages/research/createform";
    }

    @PostMapping("/create")
    @ResponseBody
    public ApiResponse<String> create(@RequestBody RegistrationRSDTO registrationRSDTO) {
        log.info("[POST] /research/create");

        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username = securityContext.getAuthentication().getName();
        registrationRSDTO.setUsername(username);
        log.info("registrationRSDTO: {}", registrationRSDTO);
        researchService.createResearch(registrationRSDTO);
        return new ApiResponse<>(200, "success", null, LocalDateTime.now());
    }


}

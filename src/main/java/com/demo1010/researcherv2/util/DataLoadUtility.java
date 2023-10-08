package com.demo1010.researcherv2.util;



import com.demo1010.researcherv2.entity.ApplicationUser;
import com.demo1010.researcherv2.entity.Role;
import com.demo1010.researcherv2.entity.Rs;
import com.demo1010.researcherv2.entity.Rsi;
import com.demo1010.researcherv2.model.RegistrationDTO;
import com.demo1010.researcherv2.repository.RoleRepository;
import com.demo1010.researcherv2.repository.RsRepository;
import com.demo1010.researcherv2.repository.RsiRepository;
import com.demo1010.researcherv2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.logging.Logger;

@Component
public class DataLoadUtility implements CommandLineRunner {

    private final Logger logger = Logger.getLogger(DataLoadUtility.class.getName());

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RsRepository rsRepository;
    private final RsiRepository rsiRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoadUtility(UserRepository userRepository, RoleRepository roleRepository, RsRepository rsRepository, RsiRepository rsiRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.rsRepository = rsRepository;
        this.rsiRepository = rsiRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args){
        if(roleRepository.findByAuthority("ROLE_ADMIN").isPresent()){
            return;
        }
        logger.info("DummyMaker.run");
        Role adminRole = roleRepository.save(new Role("ROLE_ADMIN"));
        Role userRole = roleRepository.save(new Role("ROLE_USER"));
        Set<Role> roles = Set.of(adminRole);
        Set<Role> roles2 = Set.of(userRole);
        Set<Role> roles3 = Set.of(adminRole, userRole);

        ApplicationUser registeredUser1 = registerUser( "zombil8731", "1234", "윤종복", "zombil8731@gmail.com", "01090281679", roles3);
        ApplicationUser registeredUser2 =registerUser( "songsong", "1234", "박송이", "songp875@gmail.com", "01040026862", roles);
        ApplicationUser registeredUser3 =registerUser( "yhs000727", "1234", "양희수", "yhs000727@naver.com", "01094719727", roles);
        registerUser( "parksk", "1234", "박상균", "parksk@metabuild.co.kr", "01032337280", roles);
        registerUser( "okdol", "1234", "조옥현", "okdol@metabuild.co.kr", "01027074294", roles);
        registerUser( "alzack", "1234", "임예준", "alzack@kibwa.org", "01099340633", roles2);
        registerUser( "admin", "password", "관리자", "test@test.com", "01012345678", roles3);

//        insert Rs dummy data

        Rs rs1 = new Rs();
        rs1.setRs_cnt(0);
        rs1.setRs_desc("test desc1111111111");
        rs1.setRs_title("test title1111111111111111");
        rs1.setUsername(registeredUser1.getUsername());
//        rs1.setUser(registeredUser1);
        Rs registeredRs1=rsRepository.save(rs1);

        Rs rs2 = new Rs();
        rs2.setRs_cnt(0);
        rs2.setRs_desc("test desc2222222222");
        rs2.setRs_title("test title22222222222222222222");
        rs2.setUsername(registeredUser2.getUsername());
//        rs2.setUser(registeredUser2);
        Rs registeredRs2=rsRepository.save(rs2);

        Rs rs3 = new Rs();
        rs3.setRs_cnt(0);
        rs3.setRs_desc("test desc33333333333333");
        rs3.setRs_title("test title33333333333333333");
        rs3.setUsername(registeredUser3.getUsername());
//        rs3.setUser(registeredUser3);
        Rs registeredRs3=rsRepository.save(rs3);

//        Rs 를 for문으로 많이 만들기
        for(int i=0; i<100; i++){
            Rs rs = new Rs();
            rs.setRs_cnt(0);
            rs.setRs_desc("여기는 설문 내용이 없음"+i);
            rs.setRs_title("무한 스크롤 테스트용"+i);
            rs.setUsername(registeredUser1.getUsername());
//            rs.setUser(registeredUser1);
            rsRepository.save(rs);
        }


        questionMaker(5, "0", registeredRs1);
        questionMaker(2, "1", registeredRs1);
        questionMaker(5, "2", registeredRs1);
        questionMaker(5, "3", registeredRs1);
        questionMaker(4, "4", registeredRs1);
        questionMaker(5, "5", registeredRs1);

        questionMaker(5, "0", registeredRs2);
        questionMaker(5, "1", registeredRs2);
        questionMaker(2, "2", registeredRs2);
        questionMaker(5, "3", registeredRs2);
        questionMaker(5, "4", registeredRs2);
        questionMaker(3, "5", registeredRs2);

        questionMaker(5, "0", registeredRs3);
        questionMaker(5, "1", registeredRs3);
        questionMaker(2, "2", registeredRs3);
        questionMaker(5, "3", registeredRs3);
        questionMaker(4, "4", registeredRs3);
        questionMaker(5, "5", registeredRs3);

    }
    private ApplicationUser registerUser(String username, String password, String name, String email, String phone, Set<Role> role) {
        RegistrationDTO dto = new RegistrationDTO();
        dto.setUsername(username);
        dto.setPassword(password);
        dto.setName(name);
        dto.setEmail(email);
        dto.setPhone(phone);

        ApplicationUser user = new ApplicationUser(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthorities(role);
        return userRepository.save(user);
    }

    private void questionMaker(int num, String type, Rs rs){
        for(int no=1; no<=num; no++){
            Rsi rsi = new Rsi();
            rsi.setRs_seq(rs.getRs_seq());
            rsi.setRsi_no(no);
            rsi.setRsi_question("Q"+no+" : "+"test question hi there");
            rsi.setRsi_type(type);
            switch (type) {
                case "0", "5" -> {
                    rsi.setRsi_type0_1("객관식 1번");
                    rsi.setRsi_type0_2("객관식 2번");
                    rsi.setRsi_type0_3("객관식 3번");
                    rsi.setRsi_type0_4("객관식 4번");
                    rsi.setRsi_type0_5("객관식 5번");
                    rsi.setRsi_type0_etc("YES");
                }
                case "1" -> rsi.setRsi_type1("OX");
                case "2" -> rsi.setRsi_type2("likert");
                case "3" -> rsi.setRsi_type3("주관식");
                case "4" -> rsi.setRsi_type4("별점");
            }
            rsiRepository.save(rsi);
        }
        rs.setRs_cnt(rs.getRs_cnt()+num);
        rsRepository.save(rs);
    }
}



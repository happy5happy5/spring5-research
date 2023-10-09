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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
//        현재 시간 구하기 yyyy-MM-dd 형태로 변경
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String present = LocalDateTime.now().format(formatter);
//        현재 날짜에서 10일 지난 날짜 구하기
        String plus10 = now.plusDays(10).format(formatter);
//        현재 날짜에서 20일 지난 날짜 구하기
        String plus20 = now.plusDays(20).format(formatter);
//        현재 날짜에서 30일 지난 날짜 구하기
        String plus30 = now.plusDays(30).format(formatter);
//        현재 날짜에서 40일 지난 날짜 구하기
        String plus40 = now.plusDays(40).format(formatter);


        Rs rs1 = new Rs();
        rs1.setRs_cnt(0);
        rs1.setRs_desc("test desc1111111111");
        rs1.setRs_title("test title1111111111111111");
        rs1.setRs_start_date(present);
        rs1.setRs_end_date(plus10);
        rs1.setUsername(registeredUser1.getUsername());
        rs1.setUse_yn("N");
        Rs registeredRs1=rsRepository.save(rs1);

        Rs rs2 = new Rs();
        rs2.setRs_cnt(0);
        rs2.setRs_desc("test desc2222222222");
        rs2.setRs_title("test title22222222222222222222");
        rs2.setRs_start_date(present);
        rs2.setRs_end_date(plus30);
        rs2.setUsername(registeredUser2.getUsername());
        rs2.setUse_yn("N");
        Rs registeredRs2=rsRepository.save(rs2);

        Rs rs3 = new Rs();
        rs3.setRs_cnt(0);
        rs3.setRs_desc("test desc33333333333333");
        rs3.setRs_title("test title33333333333333333");
        rs3.setUsername(registeredUser3.getUsername());
        rs3.setRs_start_date(plus10);
        rs3.setRs_end_date(plus30);
        rs3.setUse_yn("N");
        Rs registeredRs3=rsRepository.save(rs3);

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
}



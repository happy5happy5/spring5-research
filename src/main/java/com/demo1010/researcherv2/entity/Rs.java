package com.demo1010.researcherv2.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rs_seq;

    //    private Integer userId;
    private String username;
    private String rs_title;
    private String rs_desc;
    private Integer rs_cnt;
    private String rs_start_date;
    private String rs_end_date;
    private String use_yn;
    private Integer hits;

    // 다대일 관계 설정
    @ManyToOne
//    @JoinColumn(name = "userId", referencedColumnName = "user_id")
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    private ApplicationUser user;

    // 일대다 관계 설정
    @OneToMany(mappedBy = "rs", cascade = CascadeType.ALL)
    private List<Rsi> rsiList;

}


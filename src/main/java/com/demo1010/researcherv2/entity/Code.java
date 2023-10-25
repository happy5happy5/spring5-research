package com.demo1010.researcherv2.entity;


import lombok.*;
import org.joda.time.DateTime;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Code implements ICode{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cId;
    @Column(unique = true)
    private String ep;
    private boolean isEmail;
    private boolean isPhone;
    @Column(columnDefinition = "varchar(6)")
    private String code;
    private DateTime createdAt;
    private boolean isVerified;
    private boolean isExpired;

    @Override
    public boolean isExpiredCode() {
        DateTime now = DateTime.now();
        DateTime expiredAt = createdAt.plusHours(1);
        return now.isAfter(expiredAt);
    }

    @Override
    public void defaultSetCode() {
        this.isEmail = false;
        this.isPhone = false;
        this.isVerified = false;
        this.isExpired = false;
        this.createdAt = DateTime.now();
        this.code = String.valueOf((int)(Math.random()*1000000));
    }
}
package com.demo1010.researcherv2.repository;

import com.demo1010.researcherv2.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepository extends JpaRepository<Code, Integer> {

    Code findByEp(String ep);
}

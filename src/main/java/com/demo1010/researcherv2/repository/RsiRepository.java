package com.demo1010.researcherv2.repository;

import com.demo1010.researcherv2.entity.Rsi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RsiRepository extends JpaRepository<Rsi, Integer> {
}

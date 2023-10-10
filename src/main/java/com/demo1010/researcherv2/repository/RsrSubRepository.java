package com.demo1010.researcherv2.repository;

import com.demo1010.researcherv2.entity.Rsr_sub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RsrSubRepository extends JpaRepository<Rsr_sub, Integer> {

    @Query("SELECT rs FROM Rsr_sub rs WHERE rs.rs_seq = ?1 ORDER BY rs.rsi_no ASC")
    List<Rsr_sub> findAllByRsSeqOrderByRsi_noAsc(int rsSeq);
}

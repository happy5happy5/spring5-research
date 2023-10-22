package com.demo1010.researcherv2.repository;

import com.demo1010.researcherv2.entity.Rsi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RsiRepository extends JpaRepository<Rsi, Integer> {

    @Query("SELECT r FROM Rsi r WHERE r.rs_seq = :rsSeq ORDER BY r.rsi_no ASC")
    List<Rsi> findByRsSeqOrderByRsiNoAsc(@Param("rsSeq") int rsSeq);

    @Modifying
    @Query("DELETE FROM Rsi r WHERE r.rs_seq = :rsSeq")
    void deleteAllByRsSeq(Integer rsSeq);

//    삭제 쿼리

}

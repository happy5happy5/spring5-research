package com.demo1010.researcherv2.repository;

import com.demo1010.researcherv2.entity.Rsr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RsrRepository extends JpaRepository<Rsr, Integer> {


    @Query(value = "select * from rsr where rs_seq = ?1 order by rsi_no", nativeQuery = true)
    List<Rsr> findAllByRsSeqOrderByRsi_noAsc(int rs_seq);

    @Query(value = "select * from rsr where rs_seq = ?1 and rsi_no = ?2", nativeQuery = true)
    Rsr findByRsSeqAndRsiNo(int rs_seq, int rsi_no);

}

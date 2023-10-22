package com.demo1010.researcherv2.repository;

import com.demo1010.researcherv2.entity.Rsa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RsaRepository extends JpaRepository<Rsa, Integer> {
    @Query("SELECT r FROM Rsa r WHERE r.rs_seq = ?1 AND r.username = ?2")
    Optional<Rsa> findByRsSeqAndUsername(int rsSeq, String name);

    @Modifying
    @Query("DELETE FROM Rsa r WHERE r.rs_seq = ?1")
    void deleteAllByRsSeq(Integer rsSeq);

//    삭제 쿼리

}

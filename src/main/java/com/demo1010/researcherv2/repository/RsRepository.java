package com.demo1010.researcherv2.repository;

import com.demo1010.researcherv2.entity.Rs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RsRepository extends JpaRepository<Rs, Integer> {



    @Query(value = "SELECT * FROM rs ORDER BY rs_seq DESC", nativeQuery = true)
    Page<Rs> searchList(Pageable pageable);
}

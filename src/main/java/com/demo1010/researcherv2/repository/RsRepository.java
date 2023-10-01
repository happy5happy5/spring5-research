package com.demo1010.researcherv2.repository;

import com.demo1010.researcherv2.entity.Rs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RsRepository extends JpaRepository<Rs, Integer> {

}

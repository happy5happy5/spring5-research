package com.demo1010.researcherv2.repository;


import com.demo1010.researcherv2.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Integer> {

    //    Optional<ApplicationUser> findByUserId(Integer userId);
    Optional<ApplicationUser> findByUsername(String username);

    //    Optional<ApplicationUser> findUserByName(String name);


}

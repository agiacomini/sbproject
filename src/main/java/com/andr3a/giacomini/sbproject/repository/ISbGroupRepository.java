package com.andr3a.giacomini.sbproject.repository;

import com.andr3a.giacomini.sbproject.model.entity.SbGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISbGroupRepository extends JpaRepository<SbGroup, Long> {
}

package com.andr3a.giacomini.sbproject.repository.login;

import com.andr3a.giacomini.sbproject.entity.login.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthoritiesRepository extends JpaRepository<Authorities, Long> {
}

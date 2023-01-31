package com.andr3a.giacomini.sbproject.repository.login;

import com.andr3a.giacomini.sbproject.entity.Note;
import com.andr3a.giacomini.sbproject.entity.login.SbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISbUserRepository extends JpaRepository<SbUser, Long> {

    @Query(value = "SELECT * FROM sbuser n WHERE n.email =:email", nativeQuery = true)
    SbUser findSbUserByEmail(@Param("email") String email);

    @Query(value = "SELECT n FROM SbUser n WHERE n.email =:email")
//    Optional<SbUser> findSbUser2ByEmail(@Param("email") String email);
    SbUser findSbUser2ByEmail(@Param("email") String email);

    @Query(value = "SELECT n FROM SbUser n WHERE n.sbGroup.groupName =:sbGroupName")
    List<SbUser> findSbUsersBySbGroup(@Param("sbGroupName") String sbGroupName);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "UPDATE SbUser n SET n.userPassword =:newPassword WHERE n.email =:email")
    void updateSbUsersPasswordByEmail(@Param("email") String email,
                                      @Param("newPassword") String newPassord);

}

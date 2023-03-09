package com.andr3a.giacomini.sbproject.repository;

import com.andr3a.giacomini.sbproject.model.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFolderRepository extends JpaRepository<Folder,Long> {

}

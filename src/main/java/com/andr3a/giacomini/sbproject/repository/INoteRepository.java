package com.andr3a.giacomini.sbproject.repository;

import com.andr3a.giacomini.sbproject.model.entity.Note;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INoteRepository extends CrudRepository<Note, Long> {

    @Query(value = "SELECT * FROM note n WHERE n.folder_id =:folderId", nativeQuery = true)
    List<Note> findNoteByFolderId(@Param("folderId") Long folderId);

    @Query(value = "SELECT n FROM Note n WHERE n.folder.id =:folderId")
    List<Note> findNote2ByFolderId(@Param("folderId") Long folderId);
}

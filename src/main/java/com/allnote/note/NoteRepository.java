package com.allnote.note;

import com.allnote.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface NoteRepository extends JpaRepository<Note, Long>, NoteRepositoryCustom {

    @EntityGraph(attributePaths = {"user"})
    Page<Note> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    Page<Note> findAllByUser(User user, Pageable pageable);

}

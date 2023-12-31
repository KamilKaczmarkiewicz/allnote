package com.allnote.tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {
    Page<Tag> findByNameStartsWith(String startsWith, Pageable pr);
}

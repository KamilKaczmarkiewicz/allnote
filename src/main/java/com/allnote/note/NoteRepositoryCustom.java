package com.allnote.note;

import com.allnote.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoteRepositoryCustom {

    Page<Note> findAllWithTags(List<String> tags, Pageable pageable);

    Page<Note> findAllByUserWithTags(User user, List<String> tags, Pageable pageable);

}

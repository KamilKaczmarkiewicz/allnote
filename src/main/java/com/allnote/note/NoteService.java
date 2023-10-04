package com.allnote.note;

import com.allnote.note.exception.NoteNotFoundException;
import com.allnote.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    Optional<Note> find(long id) {
        return noteRepository.findById(id);
    }

    public Page<Note> findAll(int page, int size, List<String> sort) {
        Pageable pr = PageRequest.of(page, size, Sort.by(Utils.createSortOrder(sort)));
        return noteRepository.findAll(pr);
    }

    public void create(Note note) {
        noteRepository.save(note);
    }

    void update(Note note) {
        noteRepository.save(note);
    }

    void delete(long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
        noteRepository.delete(note);
    }

}

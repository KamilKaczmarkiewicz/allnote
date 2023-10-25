package com.allnote.note;

import com.allnote.note.exception.NoteNotFoundException;
import com.allnote.tag.Tag;
import com.allnote.tag.TagService;
import com.allnote.user.User;
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

    private final TagService tagService;

    Optional<Note> find(long noteId) {
        return noteRepository.findById(noteId);
    }

    public Page<Note> findAll(int page, int size, List<String> sort) {
        Pageable pr = PageRequest.of(page, size, Sort.by(Utils.createSortOrder(sort)));
        return noteRepository.findAll(pr);
    }

    public Page<Note> findAllByUser(User user, int page, int size, List<String> sort) {
        Pageable pr = PageRequest.of(page, size, Sort.by(Utils.createSortOrder(sort)));
        return noteRepository.findAllByUser(user, pr);
    }

    public void create(Note note) {
        noteRepository.save(note);
    }

    void update(Note note) {
        noteRepository.save(note);
    }

    void delete(long noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteNotFoundException(noteId));
        noteRepository.delete(note);
    }

    void delete(Note note) {
        noteRepository.delete(note);
    }

    public Page<Note> findAllWithTags(List<String> tags, int page, int size, List<String> sort) {
        Pageable pr = PageRequest.of(page, size, Sort.by(Utils.createSortOrder(sort)));
        return noteRepository.findAllWithTags(tags, pr);
    }

    public Page<Note> findAllByUserWithTags(User user, List<String> tags, int page, int size, List<String> sort) {
        Pageable pr = PageRequest.of(page, size, Sort.by(Utils.createSortOrder(sort)));
        return noteRepository.findAllByUserWithTags(user, tags, pr);

    }

    public void addTagToNote(Note note, String tagId) {
        Optional<Tag> tag = tagService.find(tagId);
        if (tag.isEmpty()) {
            tagService.create(Tag.builder().name(tagId).build());
            tag = tagService.find(tagId);
        }
        note.getTags().add(tag.get());
        noteRepository.save(note);
    }

    public void deleteTagFromNote(Note note, String tagId) {
        Optional<Tag> tag = tagService.find(tagId);
        if (tag.isPresent()) {
            note.getTags().remove(tag.get());
            noteRepository.save(note);
        }
    }
}

package com.allnote.note;

import com.allnote.note.dto.PostNoteRequest;
import com.allnote.note.dto.PutNoteRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface NoteController {

    @GetMapping("/api/notes/{id}")
    NoteModel getNote(@PathVariable("id") long noteId);

    @GetMapping("/api/notes")
    PagedModel getNotes
            (@RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "3") int size,
             @RequestParam(defaultValue = "title,asc") List<String> sort);

    @GetMapping("/api/users/{id}/notes")
    PagedModel getUserNotes
            (@PathVariable("id") long userId,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "3") int size,
             @RequestParam(defaultValue = "title,asc") List<String> sort);

    @PostMapping("/api/users/{id}/notes")
    @ResponseStatus(HttpStatus.CREATED)
    void postNote(@PathVariable("id") long userId, @RequestBody PostNoteRequest request);

    @PutMapping("/api/notes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void putNote(@PathVariable("id") long noteId, @RequestBody PutNoteRequest request);

    @DeleteMapping("/api/notes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteNote(@PathVariable("id") long noteId);

}

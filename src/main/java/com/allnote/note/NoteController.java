package com.allnote.note;

import com.allnote.note.dto.PostNoteRequest;
import com.allnote.note.dto.PutNoteRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface NoteController {

    @GetMapping("/api/notes/{id}")
    @ResponseStatus(HttpStatus.OK)
    NoteModel getNote(@PathVariable("id") long noteId);

    @GetMapping("/api/notes")
    @ResponseStatus(HttpStatus.OK)
    PagedModel getNotes
            (@RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "3") int size,
             @RequestParam(defaultValue = "title,asc") List<String> sort);

    @GetMapping("/api/users/{id}/notes")
    @ResponseStatus(HttpStatus.OK)
    PagedModel getUserNotes
            (@PathVariable("id") long userId,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "3") int size,
             @RequestParam(defaultValue = "title,asc") List<String> sort);

    @PostMapping("/api/users/{id}/notes")
    @ResponseStatus(HttpStatus.CREATED)
    NoteModel postNote(@PathVariable("id") long userId, @RequestBody PostNoteRequest request,
    @RequestParam(defaultValue = "false") boolean generateSummaryWithAI);

    @PutMapping("/api/notes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void putNote(@PathVariable("id") long noteId, @RequestBody PutNoteRequest request);

    @DeleteMapping("/api/notes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteNote(@PathVariable("id") long noteId);

    @GetMapping("/api/notes/with-tags")
    @ResponseStatus(HttpStatus.OK)
    PagedModel getNotesWithTags
            (@RequestParam(defaultValue = "") List<String> tags,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "3") int size,
             @RequestParam(defaultValue = "title,asc") List<String> sort);

    @GetMapping("/api/users/{userId}/notes/with-tags")
    @ResponseStatus(HttpStatus.OK)
    PagedModel getUserNotesWithTags
            (@PathVariable("userId") long userId,
             @RequestParam(defaultValue = "") List<String> tags,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "3") int size,
             @RequestParam(defaultValue = "title,asc") List<String> sort);

    @PutMapping("/api/notes/{noteId}/tags/{tagId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void addTagToNote(@PathVariable("noteId") long noteId, @PathVariable("tagId") String tagId);

    @DeleteMapping("/api/notes/{noteId}/tags/{tagId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTagFromNote(@PathVariable("noteId") long noteId, @PathVariable("tagId") String tagId);

}

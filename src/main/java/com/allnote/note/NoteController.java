package com.allnote.note;

import com.allnote.note.dto.PostNoteRequest;
import com.allnote.note.dto.PutNoteRequest;
import com.allnote.note.exception.NoteNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/notes")
public class NoteController {

    private NoteService noteService;

    private NoteModelAssembler noteModelAssembler;

    private PagedResourcesAssembler pagedResourcesAssembler;

    @GetMapping("/{id}")
    public NoteModel getNote(@PathVariable("id") long id) {
        Note note = noteService.find(id).orElseThrow(() -> new NoteNotFoundException(id));
        return noteModelAssembler.toModel(note);
    }

    @GetMapping
    public PagedModel getNotes
            (@RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "3") int size,
             @RequestParam(defaultValue = "title,asc") List<String> sort) {
        Page<Note> notes = noteService.findAll(page, size, sort);
        return pagedResourcesAssembler.toModel(notes, noteModelAssembler);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postNote(@RequestBody PostNoteRequest request) {
        noteService.create(request.postNoteRequestToNote());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putNote(@PathVariable("id") long id, @RequestBody PutNoteRequest request) {
        Note note = noteService.find(id).orElseThrow(() -> new NoteNotFoundException(id));
        noteService.update(request.putNoteRequestToNote(note));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNote(@PathVariable("id") long id) {
        noteService.delete(id);
    }

}

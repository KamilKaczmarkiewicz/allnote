package com.allnote.note;

import com.allnote.note.dto.PostNoteRequest;
import com.allnote.note.dto.PutNoteRequest;
import com.allnote.note.exception.NoteNotFoundException;
import com.allnote.user.User;
import com.allnote.user.UserService;
import com.allnote.user.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class NoteControllerDefault implements NoteController {

    private final NoteService noteService;

    private final UserService userService;

    private final NoteModelAssembler noteModelAssembler;

    private final PagedResourcesAssembler pagedResourcesAssembler;

    @Override
    public NoteModel getNote(@PathVariable("id") long id) {
        Note note = noteService.find(id).orElseThrow(() -> new NoteNotFoundException(id));
        return noteModelAssembler.toModel(note);
    }

    @Override
    public PagedModel getNotes
            (@RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "3") int size,
             @RequestParam(defaultValue = "title,asc") List<String> sort) {
        Page<Note> notes = noteService.findAll(page, size, sort);
        return pagedResourcesAssembler.toModel(notes, noteModelAssembler);
    }

    @Override
    public PagedModel getUserNotes
            (@PathVariable("id") long userId,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "3") int size,
             @RequestParam(defaultValue = "title,asc") List<String> sort) {
        User user = userService.find(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Page<Note> notes = noteService.findAllByUser(user, page, size, sort);
        return pagedResourcesAssembler.toModel(notes, noteModelAssembler);
    }

    @Override
    public void postNote(@PathVariable("id") long userId, @RequestBody PostNoteRequest request) {
        User user = userService.find(userId).orElseThrow(() -> new UserNotFoundException(userId));
        noteService.create(request.postNoteRequestToNote(user));
    }

    @Override
    public void putNote(@PathVariable("id") long id, @RequestBody PutNoteRequest request) {
        Note note = noteService.find(id).orElseThrow(() -> new NoteNotFoundException(id));
        noteService.update(request.putNoteRequestToNote(note));
    }

    @Override
    public void deleteNote(@PathVariable("id") long id) {
        noteService.delete(id);
    }

}

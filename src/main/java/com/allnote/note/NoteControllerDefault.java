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
    public NoteModel getNote(long id) {
        Note note = noteService.find(id).orElseThrow(() -> new NoteNotFoundException(id));
        return noteModelAssembler.toModel(note);
    }

    @Override
    public PagedModel getNotes(int page, int size, List<String> sort) {
        Page<Note> notes = noteService.findAll(page, size, sort);
        return pagedResourcesAssembler.toModel(notes, noteModelAssembler);
    }

    @Override
    public PagedModel getUserNotes(long userId, int page, int size, List<String> sort) {
        User user = userService.find(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Page<Note> notes = noteService.findAllByUser(user, page, size, sort);
        return pagedResourcesAssembler.toModel(notes, noteModelAssembler);
    }

    @Override
    public void postNote(long userId, PostNoteRequest request) {
        User user = userService.find(userId).orElseThrow(() -> new UserNotFoundException(userId));
        noteService.create(request.postNoteRequestToNote(user));
    }

    @Override
    public void putNote(long id, PutNoteRequest request) {
        Note note = noteService.find(id).orElseThrow(() -> new NoteNotFoundException(id));
        noteService.update(request.putNoteRequestToNote(note));
    }

    @Override
    public void deleteNote(long id) {
        noteService.delete(id);
    }

}

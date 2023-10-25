package com.allnote.note;

import com.allnote.note.dto.PostNoteRequest;
import com.allnote.note.dto.PutNoteRequest;
import com.allnote.note.exception.NoteNotFoundException;
import com.allnote.user.User;
import com.allnote.user.UserService;
import com.allnote.user.UserUtils;
import com.allnote.user.exception.ForbiddenException;
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
    public NoteModel getNote(long noteId) {
        Note note = noteService.find(noteId).orElseThrow(() -> new NoteNotFoundException(noteId));
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
    public void postNote(long userId, PostNoteRequest request, boolean generateSummaryWithAI) {
        if (!UserUtils.isUserAdminOrUserCaller(userId)) {
            throw new ForbiddenException();
        }
        User user = userService.find(userId).orElseThrow(() -> new UserNotFoundException(userId));
        noteService.create(request.postNoteRequestToNote(user), generateSummaryWithAI);
    }

    @Override
    public void putNote(long noteId, PutNoteRequest request) {
        Note note = noteService.find(noteId).orElseThrow(() -> new NoteNotFoundException(noteId));
        if (!UserUtils.isUserAdminOrUserCaller(note.getUser().getId())) {
            throw new ForbiddenException();
        }
        noteService.update(request.putNoteRequestToNote(note));
    }

    @Override
    public void deleteNote(long noteId) {
        Note note = noteService.find(noteId).orElseThrow(() -> new NoteNotFoundException(noteId));
        if (!UserUtils.isUserAdminOrUserCaller(note.getUser().getId())) {
            throw new ForbiddenException();
        }
        noteService.delete(note);
    }

    @Override
    public PagedModel getNotesWithTags(List<String> tags, int page, int size, List<String> sort) {
        Page<Note> notes = noteService.findAllWithTags(tags, page, size, sort);
        return pagedResourcesAssembler.toModel(notes, noteModelAssembler);
    }

    @Override
    public PagedModel getUserNotesWithTags(long userId, List<String> tags, int page, int size, List<String> sort) {
        User user = userService.find(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Page<Note> notes = noteService.findAllByUserWithTags(user, tags, page, size, sort);
        return pagedResourcesAssembler.toModel(notes, noteModelAssembler);
    }

    @Override
    public void addTagToNote(long noteId, String tagId) {
        Note note = noteService.find(noteId).orElseThrow(() -> new NoteNotFoundException(noteId));
        if (!UserUtils.isUserAdminOrUserCaller(note.getUser().getId())) {
            throw new ForbiddenException();
        }
        noteService.addTagToNote(note, tagId);
    }

    @Override
    public void deleteTagFromNote(long noteId, String tagId) {
        Note note = noteService.find(noteId).orElseThrow(() -> new NoteNotFoundException(noteId));
        if (!UserUtils.isUserAdminOrUserCaller(note.getUser().getId())) {
            throw new ForbiddenException();
        }
        noteService.deleteTagFromNote(note, tagId);
    }

}

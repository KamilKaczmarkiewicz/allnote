package com.allnote.note.exception;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException(long noteId) {
        super("note with id: " + noteId + " not found!");
    }
}

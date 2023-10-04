package com.allnote.note.exception;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException(long id) {
        super("note with id: " + id + " not found!");
    }
}

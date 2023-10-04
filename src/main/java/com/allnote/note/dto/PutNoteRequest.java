package com.allnote.note.dto;

import com.allnote.note.Note;

public record PutNoteRequest(
        String title,
        String summary,
        String content
) {
    public Note putNoteRequestToNote(Note note) {
        note.setTitle(title);
        note.setSummary(summary);
        note.setContent(content);
        return note;
    }
}

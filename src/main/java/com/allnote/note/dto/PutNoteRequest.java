package com.allnote.note.dto;

import com.allnote.note.Note;

public record PutNoteRequest(
        String title,
        String summary,
        String content
) {
    public Note putNoteRequestToNote(Note note) {
        if (title != null)
            note.setTitle(title);
        if (summary != null)
            note.setSummary(summary);
        if (content != null)
            note.setContent(content);
        return note;
    }
}

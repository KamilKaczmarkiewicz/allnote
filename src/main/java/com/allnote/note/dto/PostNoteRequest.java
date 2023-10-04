package com.allnote.note.dto;

import com.allnote.note.Note;

public record PostNoteRequest(
        String title,
        String summary,
        String content
) {
    public Note postNoteRequestToNote() {
        return Note.builder()
                .title(title)
                .summary(summary)
                .content(content)
                .build();
    }
}

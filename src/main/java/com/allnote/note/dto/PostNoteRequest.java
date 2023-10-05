package com.allnote.note.dto;

import com.allnote.note.Note;
import com.allnote.user.User;

public record PostNoteRequest(
        String title,
        String summary,
        String content
) {
    public Note postNoteRequestToNote(User user) {
        return Note.builder()
                .title(title)
                .summary(summary)
                .content(content)
                .user(user)
                .build();
    }
}

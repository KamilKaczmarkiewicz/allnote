package com.allnote.initialize;

import com.allnote.note.Note;
import com.allnote.note.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
@AllArgsConstructor
public class InitializeData implements InitializingBean {

    private final NoteService noteService;

    @Override
    public void afterPropertiesSet() throws Exception {
        LinkedList<String> order = new LinkedList<>();
        order.add("title");
        order.add("asc");
        if (noteService.findAll(0, 3, order).isEmpty()) {
            Note note1 = Note.builder()
                    .title("title one")
                    .summary("sum one")
                    .content("kjfdlskajfkldsajfkldsjakf jkdasj fkdsja iofjsda io")
                    .build();
            Note note2 = Note.builder()
                    .title("title two")
                    .summary("sum two")
                    .content("kjfdlskajfkldsajfkldsjakf jkdasj fkdsja iofjsda io")
                    .build();
            Note note3 = Note.builder()
                    .title("title three")
                    .summary("sum three")
                    .content("kjfdlskajfkldsajfkldsjakf jkdasj fkdsja iofjsda io")
                    .build();

            noteService.create(note1);
            noteService.create(note2);
            noteService.create(note3);
        }
    }
}

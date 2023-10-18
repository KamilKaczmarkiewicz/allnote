package com.allnote.initialize;

import com.allnote.note.Note;
import com.allnote.note.NoteService;
import com.allnote.user.Role;
import com.allnote.user.User;
import com.allnote.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.LinkedList;

@Component
@AllArgsConstructor
public class InitializeData implements InitializingBean {

    private final NoteService noteService;

    private final UserService userService;

    @Override
    public void afterPropertiesSet() throws Exception {
        LinkedList<String> orderUsers = new LinkedList<>();
        orderUsers.add("username");
        orderUsers.add("asc");
        User a1 = User.builder()
                .firstName("Kamil")
                .lastName("Kowalski")
                .email("email@wp.pl")
                .username("kams")
                .password("password")
                .birthDate(LocalDate.of(1990, 1, 20))
                .build();
        a1.getRoles().add(Role.USER);
        a1.getRoles().add(Role.ADMIN);
        User a2 = User.builder()
                .firstName("Adam")
                .lastName("Lis")
                .email("adam@gmail.com")
                .username("adams")
                .password("password")
                .birthDate(LocalDate.of(1992, 4, 11))
                .build();
        a2.getRoles().add(Role.USER);
        User a3 = User.builder()
                .firstName("Ewa")
                .lastName("Las")
                .email("laska@gmail.com")
                .username("laska123")
                .password("password")
                .birthDate(LocalDate.of(2001, 10, 2))
                .build();
        a3.getRoles().add(Role.USER);
        User a4 = User.builder()
                .firstName("Krzysiek")
                .lastName("Las")
                .email("kry@gmail.com")
                .username("ziom")
                .password("password")
                .birthDate(LocalDate.of(2001, 10, 2))
                .build();
        a4.getRoles().add(Role.USER);
        if (userService.findAll(0, 3, orderUsers).isEmpty()) {

            userService.create(a1);
            userService.create(a2);
            userService.create(a3);
            userService.create(a4);
        }
        LinkedList<String> orderNotes = new LinkedList<>();
        orderNotes.add("title");
        orderNotes.add("asc");
        if (noteService.findAll(0, 3, orderNotes).isEmpty()) {
            Note note1 = Note.builder()
                    .title("title one")
                    .summary("sum one")
                    .content("Some random content I would say hehe")
                    .user(a1)
                    .build();
            Note note2 = Note.builder()
                    .title("title two")
                    .summary("sum two")
                    .content("I don't like walks")
                    .user(a1)
                    .build();
            Note note3 = Note.builder()
                    .title("title three")
                    .summary("sum three")
                    .content("I prefer to drink water")
                    .user(a2)
                    .build();
            Note note4 = Note.builder()
                    .title("title 4")
                    .summary("sum 4")
                    .content("training is fun thing to do")
                    .user(a2)
                    .build();
            Note note5 = Note.builder()
                    .title("title 5")
                    .summary("sum 5")
                    .content("today is nice day")
                    .user(a4)
                    .build();
            Note note6 = Note.builder()
                    .title("title 6")
                    .summary("sum 6")
                    .content("I am so I eat")
                    .user(a3)
                    .build();

            noteService.create(note1);
            noteService.create(note2);
            noteService.create(note3);
            noteService.create(note4);
            noteService.create(note5);
            noteService.create(note6);
        }
    }
}

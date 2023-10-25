package com.allnote.note;

import com.allnote.user.UserModelAssembler;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@AllArgsConstructor
public class NoteModelAssembler implements RepresentationModelAssembler<Note, NoteModel> {

    private final UserModelAssembler userModelAssembler;

    @Override
    public NoteModel toModel(Note entity) {
        NoteModel noteModel = NoteModel.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .summary(entity.getSummary())
                .content(entity.getContent())
                .createdTime(entity.getCreatedTime())
                .lastModifiedDate(entity.getLastModifiedDate())
                .user(userModelAssembler.toModel(entity.getUser()))
                .tags(entity.getTags())
                .build();
        Link link = linkTo(methodOn(NoteControllerDefault.class).getNote(entity.getId())).withSelfRel();
        noteModel.add(link);
        return noteModel;
    }
}

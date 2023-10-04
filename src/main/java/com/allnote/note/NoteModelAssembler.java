package com.allnote.note;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class NoteModelAssembler implements RepresentationModelAssembler<Note, NoteModel> {

    @Override
    public NoteModel toModel(Note entity) {
        NoteModel noteModel = NoteModel.builder()
                .note(entity)
                .build();
        Link link = linkTo(methodOn(NoteController.class).getNote(entity.getId())).withSelfRel();
        noteModel.add(link);
        return noteModel;
    }
}

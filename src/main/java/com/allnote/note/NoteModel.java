package com.allnote.note;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class NoteModel extends RepresentationModel<NoteModel> {
    private Note note;
}

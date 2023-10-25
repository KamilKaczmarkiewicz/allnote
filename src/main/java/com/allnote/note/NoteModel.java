package com.allnote.note;

import com.allnote.tag.Tag;
import com.allnote.user.UserModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class NoteModel extends RepresentationModel<NoteModel> {
    private long id;
    private String title;
    private String summary;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
    private LocalDateTime createdTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
    private LocalDateTime lastModifiedDate;
    private UserModel user;
    private Set<Tag> tags;
}

package com.allnote.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class UserModel extends RepresentationModel<UserModel> {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private LocalDate birthDate;
    private String profilePicturePath;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
    private LocalDateTime createdTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
    private LocalDateTime lastModifiedDate;
}

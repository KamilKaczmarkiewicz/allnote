package com.allnote.user;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, UserModel> {

    @Override
    public UserModel toModel(User entity) {
        UserModel userModel = UserModel.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .birthDate(entity.getBirthDate())
                .profilePicturePath(entity.getProfilePicturePath())
                .createdTime(entity.getCreatedTime())
                .lastModifiedDate(entity.getLastModifiedDate())
                .build();
        Link link = linkTo(methodOn(UserControllerDefault.class).getUserById(entity.getId())).withSelfRel();
        userModel.add(link);
        return userModel;
    }
}

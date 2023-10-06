package com.allnote.user;

import com.allnote.user.dto.PostUserRequest;
import com.allnote.user.dto.PutUserRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserController {
    @GetMapping("/api/users/{id}")
    UserModel getUser(@PathVariable("id") long id);

    @GetMapping("/api/users")
    PagedModel getUsers
            (@RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "3") int size,
             @RequestParam(defaultValue = "username,asc") List<String> sort);

    @PostMapping("/api/users")
    @ResponseStatus(HttpStatus.CREATED)
    void postUser(@RequestBody PostUserRequest request);

    @PutMapping("/api/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void putUser(@PathVariable("id") long id, @RequestBody PutUserRequest request);

    @DeleteMapping("/api/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable("id") long id);

    @GetMapping(path = "/api/users/{id}/profile-picture", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    byte[] getUserProfilePicture(@PathVariable("id") long id);

    @PutMapping("/api/users/{id}/profile-picture")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void putUserProfilePicture(@PathVariable("id") long id, @RequestParam("image") MultipartFile image);


    @DeleteMapping("/api/users/{id}/profile-picture")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUserProfilePicture(@PathVariable("id") long id);
}

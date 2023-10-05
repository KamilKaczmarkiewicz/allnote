package com.allnote.user;

import com.allnote.user.dto.PostUserRequest;
import com.allnote.user.dto.PutUserRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface UserController {
    @GetMapping("/api/users/{id}")
    public UserModel getUser(@PathVariable("id") long id);

    @GetMapping("/api/users")
    public PagedModel getUsers
            (@RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "3") int size,
             @RequestParam(defaultValue = "username,asc") List<String> sort) ;

    @PostMapping("/api/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void postUser(@RequestBody PostUserRequest request) ;

    @PutMapping("/api/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putUser(@PathVariable("id") long id, @RequestBody PutUserRequest request) ;

    @DeleteMapping("/api/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") long id) ;
}

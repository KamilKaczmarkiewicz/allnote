package com.allnote.user;

import com.allnote.user.dto.PostUserRequest;
import com.allnote.user.dto.PutUserRequest;
import com.allnote.user.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserControllerDefault implements UserController {

    private final UserService userService;

    private UserModelAssembler userModelAssembler;

    private PagedResourcesAssembler pagedResourcesAssembler;

    @Override
    public UserModel getUser(@PathVariable("id") long id) {
        User user = userService.find(id).orElseThrow(() -> new UserNotFoundException(id));
        return userModelAssembler.toModel(user);
    }

    @Override
    public PagedModel getUsers
            (@RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "3") int size,
             @RequestParam(defaultValue = "username,asc") List<String> sort) {
        Page<User> users = userService.findAll(page, size, sort);
        return pagedResourcesAssembler.toModel(users, userModelAssembler);
    }

    @Override
    public void postUser(@RequestBody PostUserRequest request) {
        userService.create(request.postUserRequestToUser());
    }

    @Override
    public void putUser(@PathVariable("id") long id, @RequestBody PutUserRequest request) {
        User user = userService.find(id).orElseThrow(() -> new UserNotFoundException(id));
        userService.update(request.putUserRequestToUser(user));
    }

    @Override
    public void deleteUser(@PathVariable("id") long id) {
        userService.delete(id);
    }

}

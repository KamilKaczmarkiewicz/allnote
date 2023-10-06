package com.allnote.user;

import com.allnote.user.dto.PostUserRequest;
import com.allnote.user.dto.PutUserRequest;
import com.allnote.user.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserControllerDefault implements UserController {

    private final UserService userService;

    private UserModelAssembler userModelAssembler;

    private PagedResourcesAssembler pagedResourcesAssembler;

    @Override
    public UserModel getUser(long id) {
        User user = userService.find(id).orElseThrow(() -> new UserNotFoundException(id));
        return userModelAssembler.toModel(user);
    }

    @Override
    public PagedModel getUsers(int page, int size, List<String> sort) {
        Page<User> users = userService.findAll(page, size, sort);
        return pagedResourcesAssembler.toModel(users, userModelAssembler);
    }

    @Override
    public void postUser(PostUserRequest request) {
        userService.create(request.postUserRequestToUser());
    }

    @Override
    public void putUser(long id, PutUserRequest request) {
        User user = userService.find(id).orElseThrow(() -> new UserNotFoundException(id));
        userService.update(request.putUserRequestToUser(user));
    }

    @Override
    public void deleteUser(long id) {
        userService.delete(id);
    }

    @Override
    public byte[] getUserProfilePicture(long id) {
        return userService.findProfilePicture(id);
    }

    @Override
    public void putUserProfilePicture(long id, MultipartFile image) {
        userService.updateProfilePicture(id, image);
    }

    @Override
    public void deleteUserProfilePicture(long id) {
        userService.deleteProfilePicture(id);
    }

}

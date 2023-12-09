package com.allnote.user;

import com.allnote.user.dto.ChangeUserPasswordRequest;
import com.allnote.user.dto.PostUserRequest;
import com.allnote.user.dto.PutUserRequest;
import com.allnote.user.exception.ForbiddenException;
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
    public UserModel getUserById(long userId) {
        User user = userService.find(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return userModelAssembler.toModel(user);
    }

    @Override
    public UserModel getUserByUsername(String username) {
        User user = (User) userService.loadUserByUsername(username);
        return userModelAssembler.toModel(user);
    }

    @Override
    public UserModel getUser() {
        User user = UserUtils.getUserFromPrincipal();
        return userModelAssembler.toModel(user);
    }

    @Override
    public PagedModel getUsers(int page, int size, List<String> sort) {
        Page<User> users = userService.findAll(page, size, sort);
        return pagedResourcesAssembler.toModel(users, userModelAssembler);
    }

    @Override
    public void postUser(PostUserRequest request) {
        if (!UserUtils.isUserAdmin()) {
            throw new ForbiddenException();
        }
        userService.create(request.postUserRequestToUser());
    }

    @Override
    public void putUser(long userId, PutUserRequest request) {
        if (!UserUtils.isUserAdminOrUserCaller(userId)) {
            throw new ForbiddenException();
        }
        User user = userService.find(userId).orElseThrow(() -> new UserNotFoundException(userId));
        userService.update(request.putUserRequestToUser(user));
    }

    @Override
    public void changeUserPassword(long userId, ChangeUserPasswordRequest request) {
        if (!UserUtils.isUserAdminOrUserCaller(userId)) {
            throw new ForbiddenException();
        }
        userService.changeUserPassword(userId, request);
    }

    @Override
    public void deleteUser(long userId) {
        if (!UserUtils.isUserAdminOrUserCaller(userId)) {
            throw new ForbiddenException();
        }
        userService.delete(userId);
    }

    @Override
    public byte[] getUserProfilePicture(long userId) {
        return userService.findProfilePicture(userId);
    }

    @Override
    public void putUserProfilePicture(long userId, MultipartFile image) {
        if (!UserUtils.isUserAdminOrUserCaller(userId)) {
            throw new ForbiddenException();
        }
        userService.updateProfilePicture(userId, image);
    }

    @Override
    public void deleteUserProfilePicture(long userId) {
        if (!UserUtils.isUserAdminOrUserCaller(userId)) {
            throw new ForbiddenException();
        }
        userService.deleteProfilePicture(userId);
    }

}

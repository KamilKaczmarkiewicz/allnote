package com.allnote.user;

import com.allnote.user.exception.InvalidFileExtensionException;
import com.allnote.user.exception.UserNotFoundException;
import com.allnote.utils.Constants;
import com.allnote.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> find(long id) {
        return userRepository.findById(id);
    }

    public Page<User> findAll(int page, int size, List<String> sort) {
        Pageable pr = PageRequest.of(page, size, Sort.by(Utils.createSortOrder(sort)));
        return userRepository.findAll(pr);
    }

    public void create(User user) {
        userRepository.save(user);
    }

    void update(User user) {
        userRepository.save(user);
    }

    void delete(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(user);
    }

    byte[] findProfilePicture(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Path defaultPath = Paths.get(Constants.DEFAULT_PROFILE_PICTURE_PATH);
        Path path = defaultPath;
        if (user.getProfilePicturePath() != null) {
            path = Paths.get(user.getProfilePicturePath());
            if (!Files.exists(path)) {
                path = defaultPath;
            }
        }
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong while getting profile picture. " + e);
        }
    }

    void updateProfilePicture(long userId, MultipartFile image) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        String fileExtension = StringUtils.getFilenameExtension(image.getOriginalFilename());
        if (!Constants.ACCEPTED_IMAGE_FORMATS.contains(fileExtension.toLowerCase())) {
            throw new InvalidFileExtensionException();
        }
        String filePath = Constants.PROFILE_PICTURE_PATH + "\\" + user.getId() + "." + fileExtension;
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                throw new RuntimeException("Something went wrong while deleting already existing profile picture. " + e);
            }
        }
        try {
            Files.copy(image.getInputStream(), path);
            user.setProfilePicturePath(filePath);
            userRepository.save(user);
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong while adding new profile picture. " + e);
        }
    }

    void deleteProfilePicture(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        if (user.getProfilePicturePath() != null) {
            Path path = Paths.get(user.getProfilePicturePath());
            if (Files.exists(path)) {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    throw new RuntimeException("Something went wrong while removing profile picture. " + e);
                }
            }
        }
        user.setProfilePicturePath(null);
        userRepository.save(user);
    }

}

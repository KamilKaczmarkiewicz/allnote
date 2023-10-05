package com.allnote.user;

import com.allnote.user.exception.UserNotFoundException;
import com.allnote.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

}

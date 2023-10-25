package com.allnote.tag;

import com.allnote.tag.exception.TagNotFoundException;
import com.allnote.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;


    public Optional<Tag> find(String tagName) {
        return tagRepository.findById(tagName);
    }

    public void create(Tag tag) {
        tagRepository.save(tag);
    }

    public Page<Tag> findAll(int page, int size, List<String> sort) {
        Pageable pr = PageRequest.of(page, size, Sort.by(Utils.createSortOrder(sort)));
        return tagRepository.findAll(pr);
    }

    public Page<Tag> findAllThatStartWith(String startsWith, int page, int size, List<String> sort) {
        Pageable pr = PageRequest.of(page, size, Sort.by(Utils.createSortOrder(sort)));
        return tagRepository.findByNameStartsWith(startsWith, pr);
    }

    public void delete(String tagId) {
        tagRepository.delete(tagRepository.findById(tagId).orElseThrow(
                () -> new TagNotFoundException(tagId)));
    }
}

package com.allnote.tag;

import com.allnote.tag.exception.TagNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagControllerDefault implements TagController {

    private final TagService tagService;

    private final PagedResourcesAssembler pagedResourcesAssembler;

    @Override
    public Tag getTag(String tagId) {
        return tagService.find(tagId).orElseThrow(() -> new TagNotFoundException(tagId));
    }

    @Override
    public PagedModel getTags(int page, int size, List<String> sort) {
        Page<Tag> pageTags = tagService.findAll(page, size, sort);
        return pagedResourcesAssembler.toModel(pageTags);
    }

    @Override
    public PagedModel getTagsStartingWith(String startName, int page, int size, List<String> sort) {
        Page<Tag> pageTags = tagService.findAllThatStartWith(startName, page, size, sort);
        return pagedResourcesAssembler.toModel(pageTags);
    }

    @Override
    public void postTag(Tag request) {
        if (request.getNotes() == null) {
            request.setNotes(new HashSet<>());
        }
        tagService.create(request);
    }

    @Override
    public void deleteTag(String tagId) {
        tagService.delete(tagId);
    }
}

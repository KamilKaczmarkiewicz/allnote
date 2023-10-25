package com.allnote.tag;

import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface TagController {


    @GetMapping("/api/tags/{tagId}")
    @ResponseStatus(HttpStatus.OK)
    Tag getTag(@PathVariable("tagId") String tagId);

    @GetMapping("/api/tags")
    @ResponseStatus(HttpStatus.OK)
    PagedModel getTags(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "5") int size,
                       @RequestParam(defaultValue = "name,asc") List<String> sort);

    @GetMapping("/api/tags/start-with")
    @ResponseStatus(HttpStatus.OK)
    PagedModel getTagsStartingWith(@RequestParam(defaultValue = "") String startName,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "5") int size,
                                   @RequestParam(defaultValue = "name,asc") List<String> sort);

    @PostMapping("/api/tags")
    @ResponseStatus(HttpStatus.CREATED)
    void postTag(@RequestBody Tag request);

    @DeleteMapping("/api/tags/{tagId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTag(@PathVariable("tagId") String tagId);

}

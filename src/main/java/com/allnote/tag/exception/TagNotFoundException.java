package com.allnote.tag.exception;

public class TagNotFoundException extends RuntimeException {

    public TagNotFoundException(String tagId) {
        super("tag with id: " + tagId + " not found!");
    }
}

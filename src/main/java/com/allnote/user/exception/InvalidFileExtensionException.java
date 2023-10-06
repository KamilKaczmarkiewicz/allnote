package com.allnote.user.exception;

import com.allnote.utils.Constants;

import java.util.stream.Collectors;

public class InvalidFileExtensionException extends RuntimeException {

    public InvalidFileExtensionException() {
        super("Invalid file extension. Try with: " +
                Constants.ACCEPTED_IMAGE_FORMATS.stream().collect(Collectors.joining(", ")));
    }
}

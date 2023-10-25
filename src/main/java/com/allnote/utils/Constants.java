package com.allnote.utils;

import java.util.Arrays;
import java.util.HashSet;

public class Constants {
    public static final String PROFILE_PICTURE_PATH = "C:\\Users\\Kams\\Desktop\\A_Projects\\local_storage_fo_projects\\allnote\\profile_picture";
    public static final String DEFAULT_PROFILE_PICTURE_PATH = PROFILE_PICTURE_PATH + "\\default_profile_picture.jpg";
    public static final HashSet<String> ACCEPTED_IMAGE_FORMATS = new HashSet<>(Arrays.asList("jpg", "png"));

    //    OPEN_AI
    public static final String GENERATE_SUMMARY_BASED_ON_CONTENT_PREFIX = "Generate summary and nothing more for the given text. Summary has to be in the same language as the text and can not be longer than 200 characters. Text: ";

}

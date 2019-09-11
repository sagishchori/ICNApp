package com.sagishchori.icnapp.utils;

public class StringUtils {

    /**
     * A regex for validating {@link String} to contain only letters, no digits or special characters
     */
    public static final String LETTERS_ONLY_REGEX = "^[a-zA-Z]+$";

    /**
     * A method to check if a given {@link String} is empty.
     *
     * @param s     The {@link String} to check
     *
     * @return      true - if {@link String} equals to null or its length is 0, false - otherwise
     */
    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }
}

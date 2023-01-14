package com.andr3a.giacomini.sbproject.utils;

import java.util.regex.Pattern;
import static com.andr3a.giacomini.sbproject.utils.Constants.EMAIL_REGEX_PATTERN;

public class Email {

    public static boolean emailPatternMatches(String email){
        return Pattern.compile(EMAIL_REGEX_PATTERN)
                .matcher(email)
                .matches();
    }
}
package br.com.votesystem.util;

import java.util.Arrays;

public class StringUtil {
    private static final String noDigitsPattern = "[^0-9]";

    public static final String clean(final String value) {
        String cleaned = value;

        if(cleaned != null) {
            cleaned = cleaned.replaceAll(noDigitsPattern, "").trim();
        }

        return cleaned;
    }

}

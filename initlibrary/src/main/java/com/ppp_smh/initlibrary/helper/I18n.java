package com.ppp_smh.initlibrary.helper;

import android.text.SpannableStringBuilder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class I18n {
    public static boolean isRtlLocale(Locale locale) {
        if (locale == null)
            return false;
        Set<String> lang = new HashSet<>();
        lang.add("ar"); // Arabic
        lang.add("dv"); // Divehi
        lang.add("fa"); // Persian (Farsi)
        lang.add("ha"); // Hausa
        lang.add("he"); // Hebrew
        lang.add("iw"); // Hebrew (old code)
        lang.add("ji"); // Yiddish (old code)
        lang.add("ps"); // Pashto, Pushto
        lang.add("ur"); // Urdu
        lang.add("yi"); // Yiddish
        Set<String> RTL = Collections.unmodifiableSet(lang);
        // Character.getDirectionality(locale.getDisplayName().charAt(0))
        // can lead to NPE (Java 7 bug)
        // https://bugs.openjdk.java.net/browse/JDK-6992272?page=com.atlassian.streams.streams-jira-plugin:activity-stream-issue-tab
        // using hard coded list of locale instead
        return RTL.contains(locale.getLanguage());
    }

    private static boolean startWithRtl(String text) {
        if (text == null || text.length() == 0 || text.trim().length() == 0) return false;
        return isRtlText(text.substring(0, 1));
    }

    private static boolean isRtlText(String text) {
        char[] chars = text.toCharArray();
        for (char c : chars) {
            if (c >= 0x5D0 && c <= 0x6ff) {
                return true;
            }
        }
        return false;
    }

    public static String fixRtl(String text) {
        if (startWithRtl(text))
            return "\u200e" + text;
        else return text;
    }

    public static CharSequence fixText(Locale locale, CharSequence text) {
        switch (locale.getLanguage()) {
            case "fa":
                return fixPersianNumber(text);
            default:
                return text;
        }
    }

    private static CharSequence fixPersianNumber(CharSequence text) {
        // SpannableStringBuilder will loose its styles, so we skip it.
        if (text instanceof SpannableStringBuilder) return text;

        if (text != null) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < text.length(); ++i) {
                char c = text.charAt(i);
                if (c >= 48 && c <= 57) {
                    c = (char) (c + 1728);
                }

                sb.append(c);
            }

            text = sb;
        }

        return text;
    }

    public static boolean isValidIranMobile(String mobile) {
        Pattern pattern = Pattern.compile("09\\d{9}");
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }
}

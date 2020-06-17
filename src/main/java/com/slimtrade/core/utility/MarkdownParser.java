package com.slimtrade.core.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A crude parser for converting basic markdown to html.
 */
public class MarkdownParser {

    private static final Pattern boldItalicPattern = Pattern.compile("(.*)(\\*){3}(.+)(\\*){3}(.*)");
    private static final Pattern boldPattern = Pattern.compile("(.*)(\\*){2}(.+)(\\*){2}(.*)");
    private static final Pattern italicsPattern = Pattern.compile("(.*)(\\*)(.+)(\\*)(.*)");
    private static final Pattern linkPattern = Pattern.compile("(.*)\\[(.+)\\]\\((.+)\\)(.*)");
    private static Matcher matcher;
    private static boolean header;

    public static String getHtmlFromMarkdown(String string) {
        header = false;
        string = string.trim();
        string = convertLinks(string);
        string = convertBoldItalics(string);
        string = convertBold(string);
        string = convertItalics(string);

        string = convertHeader(string);
        if (!header) {
            return string + "<br>";
        }
        return (string);
    }

    private static String convertHeader(String string) {
        header = true;
        if (string.startsWith("####")) {
            string = string.replaceFirst("####", "");
            return "<h4>" + string + "</h4>";
        } else if (string.startsWith("###")) {
            string = string.replaceFirst("###", "");
            return "<h3>" + string + "</h3>";
        } else if (string.startsWith("##")) {
            string = string.replaceFirst("##", "");
            return "<h2>" + string + "</h2>";
        } else if (string.startsWith("#")) {
            string = string.replaceFirst("#", "");
            return "<h1>" + string + "</h1>";
        }
        header = false;
        return string;
    }

    private static String convertLinks(String string) {
        matcher = linkPattern.matcher(string);
        while (matcher.matches()) {
            System.out.println("LINK");
            string = matcher.replaceFirst("$1<a href=\"$3\">$2</a>$4");
            matcher = linkPattern.matcher(string);
        }
        return string;
    }

    private static String convertBoldItalics(String string) {
        matcher = boldItalicPattern.matcher(string);
        // Bold & Italics
        while (matcher.matches()) {
            string = matcher.replaceFirst("$1<b><i>$3</i></b>$5");
            matcher = boldItalicPattern.matcher(string);
        }
        return string;
    }

    private static String convertBold(String string) {
        matcher = boldPattern.matcher(string);
        while (matcher.matches()) {
            string = matcher.replaceFirst("$1<b>$3</b>$5");
            matcher = boldPattern.matcher(string);
        }
        return string;
    }

    private static String convertItalics(String string) {
        matcher = italicsPattern.matcher(string);
        while (matcher.matches()) {
            string = matcher.replaceFirst("$1<i>$3</i>$5");
            matcher = italicsPattern.matcher(string);
        }
        return string;
    }

}

package github.zmilla93.modules.updater;

import java.io.File;
import java.util.regex.Matcher;

public class UpdateUtil {

    public static boolean validateDirectory(String directory) {
        File file = new File(directory);
        if (file.exists()) return file.isDirectory();
        return file.mkdirs();
    }

    public static String cleanFileSeparators(String path) {
        return path.replaceAll("[/\\\\]", Matcher.quoteReplacement(File.separator));
    }

}

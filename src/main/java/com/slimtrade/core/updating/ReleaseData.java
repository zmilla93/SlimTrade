package com.slimtrade.core.updating;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.utility.MarkdownParser;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;

public class ReleaseData {

    public String tag;
    public String rawPatchNotes;
    private String cleanPatchNotes;
    private String colorPatchNotes;
    private Color cachedColor;

    public ReleaseData(JSONObject json) {
        try {
            tag = json.getString("tag_name");
            rawPatchNotes = json.getString("body");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getColorPatchNotes(Color color) {
        if (cleanPatchNotes == null) {
            cleanPatchNotes = getCleanPatchNotes();
        }
        if (colorPatchNotes == null || cachedColor == null || color != cachedColor) {
            cachedColor = color;
            colorPatchNotes = cleanPatchNotes.replace("color:rgb({RBG})", "color:rgb(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ")");
        }
        return colorPatchNotes;
    }

    private String getCleanPatchNotes() {
        String[] lines = rawPatchNotes.split("(\\n|\\\\r\\\\n)");
        StringBuilder builder = new StringBuilder();
        Color c = ColorManager.TEXT;
        builder.append("<body style=\"color:rgb({RBG})\">");
        builder.append("<h1>SlimTrade ").append(tag).append("</h1>");
        for (String s : lines) {
            if (s.toLowerCase().contains("how to install")) {
                break;
            }
            builder.append(MarkdownParser.getHtmlFromMarkdown(s));
        }
        builder.append("</body>");
        return builder.toString();
    }

}

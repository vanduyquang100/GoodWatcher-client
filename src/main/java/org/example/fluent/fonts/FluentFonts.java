package org.example.fluent.fonts;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FluentFonts extends JComponent{
    public static Font DEFAULT_FONT = null;
    public static Font DEFAULT_FONT_BOLD = null;
    public static Font DEFAULT_FONT_STRONG = null;

    public static Font DISPLAY = null;
    public static Font TITLE_LARGE  = null;
    public static Font TITLE = null;
    public static Font SUBTITLE = null;
    public static Font SUBTITLE_EXTRA_BOLD = null;
    public static Font BODY_LARGE = null;
    public static Font BODY_STRONG = null;
    public static Font BODY  = null;

    public FluentFonts() {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/SegoeUIVariableStaticDisplay.ttf"));
            setFont(font);
            DEFAULT_FONT = font.deriveFont(Font.PLAIN);
            DEFAULT_FONT_BOLD = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/SegoeUIVariableStaticDisplaySemibold.ttf"));
            DEFAULT_FONT_STRONG = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/SegoeUIVariableStaticDisplayBold.ttf"));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }


        DISPLAY = DEFAULT_FONT_STRONG.deriveFont(68f);
        TITLE_LARGE = DEFAULT_FONT_STRONG.deriveFont(54f);
        TITLE = DEFAULT_FONT_STRONG.deriveFont(28f);
        SUBTITLE = DEFAULT_FONT_STRONG.deriveFont(20f);
        BODY_LARGE = DEFAULT_FONT_STRONG.deriveFont(18f);
        BODY_STRONG = DEFAULT_FONT_BOLD.deriveFont(14f);
        BODY = DEFAULT_FONT.deriveFont(14f);
    }

    public static String truncateStringWithEllipsis(String text, int availableWidth, FontMetrics metrics) {
        String ellipsis = "...";
        int maxLength = text.length() - 1;
        while (metrics.stringWidth(text.substring(0, maxLength) + ellipsis) > availableWidth && maxLength > 0) {
            maxLength--;
        }
        return text.substring(0, maxLength) + ellipsis;
    }
}

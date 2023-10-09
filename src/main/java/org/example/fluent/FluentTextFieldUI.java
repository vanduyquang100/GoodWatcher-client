package org.example.fluent;

import org.example.fluent.fonts.FluentFonts;

import javax.swing.plaf.basic.BasicTextFieldUI;
import java.awt.*;

public class FluentTextFieldUI extends BasicTextFieldUI {
    private final String placeholder;
    private final Color placeholderColor;

    public FluentTextFieldUI(String placeholder, Color placeholderColor) {
        super();
        this.placeholder = placeholder;
        this.placeholderColor = placeholderColor;
    }

    @Override
    protected void paintSafely(Graphics g) {
        super.paintSafely(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        FluentTextField parent = (FluentTextField) getComponent();
        if (parent.getText().isEmpty()) {
            g.setColor(placeholderColor);
            FontMetrics metrics = g2d.getFontMetrics();
            g.setFont(FluentFonts.BODY);
            g.drawString(placeholder, parent.getInsets().left, (parent.getHeight() - metrics.getHeight()) / 2 + metrics.getAscent());
        }
        g2d.dispose();
    }

}
package org.example.fluent;

import org.example.fluent.colors.FluentColors;
import org.example.fluent.fonts.FluentFonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FluentButton extends JButton {
    private static final int OFFSET_SIZE = 2; // size change of the button during the animation
    private static final int ARC_RADIUS = 4;
    private static final int PADDING = 9;
    private static final int BORDER = 1;
    private int buttonState  = 0; // 0: normal; 1: hovered; 2: pressed.


    public FluentButton(String buttonContent) {
        super(buttonContent);
        setOpaque(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        this.setFont(FluentFonts.BODY);
        this.setBackground(FluentColors.BUTTON_BACKGROUND_NORMAL);
        this.setForeground(FluentColors.BUTTON_FOREGROUND_NORMAL);
        setPreferredSize(new Dimension(120, 32));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (buttonState < 1) {
                    buttonState = 1;
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
                if (buttonState < 2) {
                    buttonState = 2;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (buttonState == 2) {
                    buttonState = 1;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (buttonState != 0) {
                    buttonState = 0;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw a custom graphic
        int currentOffsetSize = OFFSET_SIZE * ((buttonState == 2) ? 1 : 0);
        // Border
        Color[] colors = { FluentColors.BUTTON_BACKGROUND_HOVERED, FluentColors.BUTTON_BACKGROUND_PRESSED};

        // Create a gradient paint object
        GradientPaint gradient = new GradientPaint(0, 0, colors[0], 0, getHeight(), (buttonState != 2) ? colors[1] : colors[0], false);

        // Set the paint to the gradient
        g2d.setPaint(gradient);
        g2d.fillRoundRect(0, currentOffsetSize, getWidth(), getHeight() - currentOffsetSize, (ARC_RADIUS + BORDER) * 2, (ARC_RADIUS + BORDER) * 2);

        // Fill
        if (buttonState == 0) {
            g2d.setColor(getBackground());
        } else if (buttonState == 1) {
            g2d.setColor(FluentColors.BUTTON_BACKGROUND_HOVERED);
        } else {
            g2d.setColor(FluentColors.BUTTON_BACKGROUND_PRESSED);
        }
        g2d.fillRoundRect(BORDER, currentOffsetSize + BORDER, getWidth() - BORDER * 2, getHeight() - currentOffsetSize - BORDER * 2, ARC_RADIUS * 2, ARC_RADIUS * 2);

        // Text
        String contentText = getText();
        FontMetrics metrics = g2d.getFontMetrics(getFont());
        int availableWidth = getWidth() - PADDING * 2;
        int textWidth = metrics.stringWidth(getText());
        if (textWidth > availableWidth) {
            contentText = FluentFonts.truncateStringWithEllipsis(contentText, availableWidth, metrics);
        }
        g2d.setColor(getForeground());
        g2d.drawString(contentText, getWidth() / 2 - g2d.getFontMetrics().stringWidth(contentText) / 2,
                (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent()  + currentOffsetSize - 1 );
        g2d.dispose();
    }

}

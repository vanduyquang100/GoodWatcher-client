package org.example.fluent;

import org.example.fluent.colors.FluentColors;
import org.example.fluent.fonts.FluentFonts;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class FluentTextField extends JTextField {
    private static final float ANTI_ALIASING = 0.15f;
    private static final int ARC_RADIUS = 4;
    private static final int PADDING = 10;
    private static final int BORDER = 1;
    private int textFieldState  = 0; // 0: not focus; 1: focus; highlighted = 2.
    Highlighter highlighter;
    DefaultHighlighter.DefaultHighlightPainter highlightPainter;
    public FluentTextField(String text) {
        super("");
        this.setPreferredSize(new Dimension(160, 32));
        this.setFont(FluentFonts.BODY);
        this.setUI(new FluentTextFieldUI(text, FluentColors.LIGHT_OVERLAY));
        Insets currentInsets = this.getMargin();
        currentInsets.left += PADDING;
        currentInsets.right += PADDING;
        this.setOpaque(false);
        this.setMargin(currentInsets);
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textFieldState != 1) {
                    textFieldState = 1;
                    repaint();
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textFieldState != 0) {
                    textFieldState = 0;
                    repaint();
                }
            }
        });

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (textFieldState != 1 && textFieldState != 2) {
                    textFieldState = 2;
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (textFieldState == 2) {
                    textFieldState = 0;
                    repaint();
                }
            }
        });
    }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            Shape clip = new RoundRectangle2D.Float(-ANTI_ALIASING, -ANTI_ALIASING, getWidth() + 2 * ANTI_ALIASING, getHeight() + 2* ANTI_ALIASING, (ARC_RADIUS + BORDER - ANTI_ALIASING) * 2, (ARC_RADIUS + BORDER - ANTI_ALIASING) * 2);
            g2d.clip(clip);
            g2d.setColor(FluentColors.FRAME_BORDER);
            g2d.fillRoundRect(-1, -1, getWidth() + 2, getHeight() + 2, (ARC_RADIUS + BORDER + 1) * 2, (ARC_RADIUS + BORDER + 1) * 2);
            g2d.setColor(getBackground());
            g2d.fillRoundRect(BORDER, BORDER, getWidth() - BORDER * 2, getHeight() - BORDER * 2, ARC_RADIUS * 2, ARC_RADIUS * 2);

            int offset = textFieldState;
            if (textFieldState == 0) {
                g2d.setColor(FluentColors.DARK_OVERLAY);
            } else if (textFieldState == 1) {
                g2d.setColor(FluentColors.TEXT_FIELD_BORDER_FOCUS);
            } else {
                offset = 0;
                g2d.setColor(FluentColors.TEXT_FIELD_FILL_HOVERED);
                g2d.fillRoundRect(BORDER, BORDER, getWidth() - BORDER * 2, getHeight() - BORDER * 2, ARC_RADIUS * 2, ARC_RADIUS * 2);
                g2d.setColor(FluentColors.DARK_OVERLAY);
            }
            // Fill
            g2d.fillRect(0, getHeight() - (offset + 1), getWidth(), 2 * offset + 1);
            g.setColor(FluentColors.LIGHT_TRANSPARENT);
            g2d.fillRect(0, getHeight() - (offset + 1), getWidth() , offset);
            super.paintComponent(g);
            // Draw a custom graphic
            g2d.dispose();
        }
}

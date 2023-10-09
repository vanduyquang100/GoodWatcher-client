package org.example.fluent;

import org.example.fluent.colors.FluentColors;
import org.example.fluent.fonts.FluentFonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FluentPrimaryButton extends JButton {
    private static final int ANIMATION_DURATION = 20; // duration of the animation in milliseconds
    private static final int OFFSET_SIZE = 2; // size change of the button during the animation
    private static final int ARC_RADIUS = 4;
    private static final int PADDING = 9;
    private static final int BORDER = 1;
    private int buttonState  = 0; // 0: normal; 1: hovered; 2: pressed.


    public FluentPrimaryButton(String buttonContent) {
        super(buttonContent);
        setOpaque(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        this.setFont(FluentFonts.BODY);
        this.setBackground(FluentColors.PRIMARY_BUTTON_BACKGROUND_NORMAL);
        this.setForeground(FluentColors.PRIMARY_BUTTON_FOREGROUND_NORMAL);
        setPreferredSize(new Dimension(120, 32));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (buttonState < 1) {
                    buttonState = 1;
                    animateToColor(buttonState);
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
                if (buttonState < 2) {
                    buttonState = 2;
                    animateToColor(buttonState);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (buttonState == 2) {
                    buttonState = 1;
                    animateToColor(buttonState);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (buttonState != 0) {
                    buttonState = 0;
                    animateToColor(buttonState);
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
        Color[] colors = { FluentColors.PRIMARY_BUTTON_BACKGROUND_HOVERED, FluentColors.PRIMARY_BUTTON_BACKGROUND_PRESSED};

        // Create a gradient paint object
        GradientPaint gradient = new GradientPaint(0, 0, colors[0], 0, getHeight(), (buttonState != 2) ? colors[1] : colors[0], false);

        // Set the paint to the gradient
        g2d.setPaint(gradient);
        g2d.fillRoundRect(0, currentOffsetSize, getWidth(), getHeight() - currentOffsetSize, (ARC_RADIUS + BORDER) * 2, (ARC_RADIUS + BORDER) * 2);

        // Fill
        g2d.setColor(getBackground());
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

    private void animateToColor(int targetButtonState) {
        Color startColorBackground = getBackground();
        Color startColorForeground = getForeground();
        Color targetColorBackground = null;
        Color targetColorForeground = null;

        if (targetButtonState == 1) {
            targetColorBackground = FluentColors.PRIMARY_BUTTON_BACKGROUND_HOVERED;
            targetColorForeground = FluentColors.PRIMARY_BUTTON_FOREGROUND_HOVERED;
        } else if (targetButtonState == 2) {
            targetColorBackground = FluentColors.PRIMARY_BUTTON_BACKGROUND_PRESSED;
            targetColorForeground = FluentColors.PRIMARY_BUTTON_FOREGROUND_PRESSED;
        } else {
            targetColorBackground = FluentColors.PRIMARY_BUTTON_BACKGROUND_NORMAL;
            targetColorForeground = FluentColors.PRIMARY_BUTTON_FOREGROUND_NORMAL;
        }

        float[] startHSBBackground = Color.RGBtoHSB(startColorBackground.getRed(), startColorBackground.getGreen(), startColorBackground.getBlue(), null);
        float[] targetHSBBackground = Color.RGBtoHSB(targetColorBackground.getRed(), targetColorBackground.getGreen(), targetColorBackground.getBlue(), null);
        float[] startHSBForeground = Color.RGBtoHSB(startColorForeground.getRed(), startColorForeground.getGreen(), startColorForeground.getBlue(), null);
        float[] targetHSBForeground = Color.RGBtoHSB(targetColorForeground.getRed(), targetColorForeground.getGreen(), targetColorForeground.getBlue(), null);
        int steps = 10; // Number of animation steps

        // Animate the background color of the button
        Timer timer = new Timer(ANIMATION_DURATION / steps, new ActionListener() {
            int step = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                float ratio = (float) step / steps;
                Color currentColorBackground = shiftColor(ratio, startHSBBackground, targetHSBBackground);
                setBackground(currentColorBackground);
                Color currentColorForeground = shiftColor(ratio, startHSBForeground, targetHSBForeground);
                setForeground(currentColorForeground);

                step++;
                if (step > steps || targetButtonState != buttonState) {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();
    }

    private Color shiftColor(float ratio, float[] startHSBBackground, float[] targetHSBBackground) {
        float[] currentHSBBackground = new float[] {
                startHSBBackground[0] + (targetHSBBackground[0] - startHSBBackground[0]) * ratio,
                startHSBBackground[1] + (targetHSBBackground[1] - startHSBBackground[1]) * ratio,
                startHSBBackground[2] + (targetHSBBackground[2] - startHSBBackground[2]) * ratio
        };
        return new Color(Color.HSBtoRGB(currentHSBBackground[0], currentHSBBackground[1], currentHSBBackground[2]));
    }

}

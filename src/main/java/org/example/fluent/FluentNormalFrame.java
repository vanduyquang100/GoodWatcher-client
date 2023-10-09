package org.example.fluent;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class FluentNormalFrame extends JFrame {
    public FluentNormalFrame(Color backgroundColor, String frameName) {
        super(frameName);
        getContentPane().setBackground(backgroundColor);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/app_icon.png")));
        setIconImage(icon.getImage());
        setVisible(true);
    }

    @Override
    public void add(Component comp, Object constraints ) {
        super.add(comp, BorderLayout.CENTER);
    }
}

package org.example.fluent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.fluent.fonts.FluentFonts.TITLE_LARGE;

public class FluentFrame extends JFrame {
    JTextArea textArea;
    public FluentFrame(Color backgroundColor, String frameName) {
        super(frameName);
        getContentPane().setBackground(backgroundColor);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        headerPanel.setBackground(backgroundColor);

        textArea = new JTextArea(frameName);
        textArea.setFont(TITLE_LARGE);
        textArea.setBackground(backgroundColor);
        textArea.setFocusable(false);
        textArea.setEditable(false);
        textArea.setMargin(new Insets(0, 0, 0, 0));
        int leftMargin = 25;
        int topMargin = 45;
        textArea.setBorder(new EmptyBorder(topMargin, leftMargin, 0, 0));
        headerPanel.add(textArea);

        int headerPanelMaxHeight = 150;
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, headerPanelMaxHeight));

        super.add(headerPanel, BorderLayout.NORTH);

        // Call pack() again after setting the location to recalculate the preferred size
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/app_icon.png"));
        setIconImage(icon.getImage());
        setVisible(true);
    }

    @Override
    public void add(Component comp, Object constraints ) {
        super.add(comp, BorderLayout.CENTER);
    }

    public void setTitle(String newName) {
        super.setTitle(newName);
        textArea.setText(newName);
    }
}

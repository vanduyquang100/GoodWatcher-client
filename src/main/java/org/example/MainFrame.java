package org.example;

import org.example.fluent.FluentPrimaryButton;
import org.example.fluent.FluentNormalFrame;
import org.example.fluent.colors.FluentColors;
import org.example.fluent.fonts.FluentFonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

import static org.example.fluent.fonts.FluentFonts.BODY;

public class MainFrame {
    private static final int BORDER = 25;
    public MainFrame() {
        Runnable mainFrame = () -> {
            new FluentFonts();
            JFrame newFrame = new FluentNormalFrame(FluentColors.FRAME_BACKGROUND, "Client");
            newFrame.setSize(550, 350);
            newFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            newFrame.setResizable(false);

// Create the main panel with BorderLayout
            JPanel mainPanel = new JPanel(new GridLayout(1, 1));
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setBackground(FluentColors.FRAME_BACKGROUND);
            contentPanel.setBorder(new EmptyBorder(BORDER, BORDER, BORDER, BORDER));

            JPanel bannerPanel = new JPanel(new GridLayout(1, 1));
            int bannerPanelHeight = 150;
            ImageIcon banner = new ImageIcon(getClass().getResource("/images/banner.png"));
            JLabel  bannerContainer = new JLabel(banner);
            bannerPanel.add(bannerContainer);
            bannerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, bannerPanelHeight));
            bannerPanel.setBackground(FluentColors.FRAME_BACKGROUND);

            JPanel leftPanel = new JPanel(new GridBagLayout());
            leftPanel.setBackground(FluentColors.FRAME_BACKGROUND);
            JTextArea message = new JTextArea("Successfully connected to the server. You can now minimize this window.");
            message.setFont(BODY);
            message.setBackground(FluentColors.FRAME_BACKGROUND);
            message.setFocusable(false);
            message.setEditable(false);
            message.setMargin(new Insets(0, 0, 0, 0));
            leftPanel.add(message, new GridBagConstraints());


// Create the button panel with FlowLayout for right alignment
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setBackground(FluentColors.FRAME_BACKGROUND);

            FluentPrimaryButton button = new FluentPrimaryButton("Minimize");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(button);
                    // Hide the current frame
                    currentFrame.setExtendedState(Frame.ICONIFIED);
                }
            });
            buttonPanel.add(button);

// Add the panels to the main panel
            contentPanel.add(bannerPanel, BorderLayout.NORTH);
            contentPanel.add(leftPanel, BorderLayout.CENTER);
            contentPanel.add(buttonPanel, BorderLayout.SOUTH);
            mainPanel.add(contentPanel);


            newFrame.add(mainPanel);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            newFrame.setLocation(dim.width/2-newFrame.getWidth()/2, dim.height/2-newFrame.getHeight()/2);
            newFrame.setVisible(true);
        };
        SwingUtilities.invokeLater(mainFrame);
    }
}

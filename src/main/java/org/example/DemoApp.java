package org.example;

import org.example.controllers.ConnectAction;
import org.example.fluent.*;
import org.example.fluent.colors.FluentColors;
import org.example.fluent.fonts.FluentFonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static org.example.fluent.fonts.FluentFonts.*;

public class DemoApp {
    private static final int BORDER = 25;
    public DemoApp() {
        Runnable mainFrame = () -> {
            JFrame newFrame = new FluentFrame(FluentColors.FRAME_BACKGROUND, "Connect");
            newFrame.setSize(550, 350);
            newFrame.setResizable(false);

// Create the main panel with BorderLayout
            JPanel mainPanel = new JPanel(new GridLayout(1, 1));
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setBackground(FluentColors.FRAME_BACKGROUND);
            contentPanel.setBorder(new EmptyBorder(0, BORDER, BORDER, BORDER));

            JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            leftPanel.setBackground(FluentColors.FRAME_BACKGROUND);
            JTextArea instruction = new JTextArea("Type in the code showing on your server's app");
            instruction.setFont(BODY);
            instruction.setBackground(FluentColors.FRAME_BACKGROUND);
            instruction.setFocusable(false);
            instruction.setEditable(false);
            instruction.setMargin(new Insets(0, 0, 0, 0));
            leftPanel.add(instruction);

// Create the center panel with BoxLayout to center the text field
            JPanel centerPanel = new JPanel(new GridBagLayout());
            centerPanel.setBackground(FluentColors.FRAME_BACKGROUND);
            FluentTextField textField = new FluentTextField("Enter the connection code");
            textField.setPreferredSize(new Dimension(newFrame.getWidth() - 2 * (BORDER + 10), 32));
            centerPanel.add(textField, new GridBagConstraints());

// Create the button panel with FlowLayout for right alignment
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setBackground(FluentColors.FRAME_BACKGROUND);

            FluentPrimaryButton button = new FluentPrimaryButton("Connect");
            buttonPanel.add(button);

// Add the panels to the main panel
            contentPanel.add(leftPanel, BorderLayout.NORTH);
            contentPanel.add(centerPanel, BorderLayout.CENTER);
            contentPanel.add(buttonPanel, BorderLayout.SOUTH);
            mainPanel.add(contentPanel);

            newFrame.add(mainPanel);
            button.addActionListener(new ConnectAction(textField, button));
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            newFrame.setLocation(dim.width/2-newFrame.getWidth()/2, dim.height/2-newFrame.getHeight()/2);
            newFrame.setVisible(true);
        };
        SwingUtilities.invokeLater(mainFrame);
    }
}

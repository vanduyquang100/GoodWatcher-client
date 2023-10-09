package org.example.fluent;

import org.example.fluent.colors.FluentColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import static org.example.fluent.fonts.FluentFonts.BODY;

public class FluentWarningDialog extends JDialog {
    private static final int BORDER = 25;
    public FluentWarningDialog(FluentFrame owner, Color backgroundColor, String message) {
        super(owner, "Warning", true);
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/app_icon.png"));
        setIconImage(icon.getImage());
        getContentPane().setBackground(backgroundColor);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setSize(550, 250);
        setResizable(false);

// Create the main panel with BorderLayout
        JPanel mainPanel = new JPanel(new GridLayout(1, 1));
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(FluentColors.FRAME_BACKGROUND);
        contentPanel.setBorder(new EmptyBorder(BORDER, BORDER, BORDER, BORDER));

        JPanel bannerPanel = new JPanel(new GridLayout(1, 1));
        int bannerPanelHeight = 150;
        bannerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, bannerPanelHeight));
        bannerPanel.setBackground(FluentColors.FRAME_BACKGROUND);

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(FluentColors.FRAME_BACKGROUND);
        JTextArea messageText = new JTextArea(message);
        messageText.setFont(BODY);
        messageText.setBackground(FluentColors.FRAME_BACKGROUND);
        messageText.setFocusable(false);
        messageText.setEditable(false);
        messageText.setMargin(new Insets(0, 0, 0, 0));
        leftPanel.add(messageText, new GridBagConstraints());



// Create the button panel with FlowLayout for right alignment
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(FluentColors.FRAME_BACKGROUND);

        FluentButton button = new FluentButton("Okay");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog currentDialog = (JDialog) SwingUtilities.getWindowAncestor(button);
                currentDialog.dispose();
            }
        });
        buttonPanel.add(button);

// Add the panels to the main panel
        contentPanel.add(bannerPanel, BorderLayout.NORTH);
        contentPanel.add(leftPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(contentPanel);


        add(mainPanel);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setVisible(true);
    }

    @Override
    public void add(Component comp, Object constraints ) {
        super.add(comp, BorderLayout.CENTER);
    }
}

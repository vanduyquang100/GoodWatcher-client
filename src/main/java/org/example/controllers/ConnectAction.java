package org.example.controllers;

import org.example.controllers.background.CommunicationThread;
import org.example.fluent.FluentFrame;
import org.example.fluent.FluentWarningDialog;
import org.example.helpers.IpEncoder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ConnectAction implements ActionListener {
    private JFrame currentWindow;
    private JButton currentButton;
    private JTextField hexaCodeField;

    public ConnectAction(JTextField code, JButton currentButton) {
        this.currentButton = currentButton;
        this.currentWindow = (JFrame) SwingUtilities.getWindowAncestor(currentButton);
        this.hexaCodeField = code;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        currentButton.setText("Please wait");
        ArrayList<Object> decodeResult = null;
        try {
            decodeResult = IpEncoder.decodeHex(hexaCodeField.getText());
            String Ip = (String)decodeResult.get(0);
            int port = (Integer)decodeResult.get(1);
            Thread thread = new Thread(new CommunicationThread(Ip, port, currentButton));
            thread.start();
        }
        catch (IllegalArgumentException exception) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // Close the current window
                    new FluentWarningDialog((FluentFrame)currentWindow, currentWindow.getBackground(), exception.getMessage());
                }
            });
            currentButton.setText("Connect");
        }
    }
}

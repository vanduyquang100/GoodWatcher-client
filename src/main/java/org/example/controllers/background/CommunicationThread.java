package org.example.controllers.background;

import org.example.MainFrame;
import org.example.fluent.FluentFrame;
import org.example.fluent.FluentWarningDialog;
import org.example.helpers.PCInfoEncoder;
import org.example.models.FolderView;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.nio.file.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.nio.file.StandardWatchEventKinds.*;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;

public class CommunicationThread implements Runnable {
    private String Ip = "";
    private int port = 0;
    public AtomicBoolean threadStopped = new AtomicBoolean(false);
    private final JFrame currentWindow;
    private final JButton currentButton;
    private final FolderView folderView;

    public CommunicationThread(String Ip, int port, JButton calledButton) {
        folderView = new FolderView();
        this.Ip = Ip;
        this.port = port;
        this.currentButton = calledButton;
        this.currentWindow = (JFrame) SwingUtilities.getWindowAncestor(currentButton);
    }

    @Override
    public void run() {
        connect();
    }

    private void connect() {
        try {
            Socket s = new Socket(Ip, port);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // Close the current window
                    new MainFrame();
                    currentWindow.dispose();
                }
            });
            System.out.println(s.getPort());

            InputStream is = s.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            OutputStream os = s.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

            String sentMessage = PCInfoEncoder.encodePCInfo();
            System.out.println("Talking to Server");
            System.out.println("Sending " + sentMessage);
            bw.write(sentMessage);
            bw.newLine();
            bw.flush();

            while (!threadStopped.get()) {
                String receiveMessage = br.readLine();
                System.out.println("Received " + receiveMessage);

                if (receiveMessage.startsWith("cd:")) {
                    int startIndex = 3;
                    folderView.changeDirectory(receiveMessage.substring(startIndex));
                    String joinedString = "list:" + String.join("\t", folderView.getSubFolderNames());
                    bw.write(joinedString);
                    bw.newLine();
                    bw.flush();
                }

                if (receiveMessage.startsWith("path:")) {
                    int startIndexOfPath = 5;
                    try {
                        folderView.changeDirectory(receiveMessage.substring(startIndexOfPath));
                        folderView.getPath(); // to invoke exception
                        System.out.println("Watch folder successfully installed.");
                        bw.write("0");
                        bw.newLine();
                        bw.flush();
                        Thread folderTracking = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (!threadStopped.get()) {
                                    String pathString = folderView.getPath().toString();
                                    try {
                                        WatchService watcher = FileSystems.getDefault().newWatchService();
                                        Path filePath = Paths.get(pathString);
                                        WatchKey key = filePath.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
                                        do {
                                            watcher.take();
                                            for (WatchEvent<?> event : key.pollEvents()) {
                                                String newEvent = "";
                                                WatchEvent.Kind<?> kind = event.kind();
                                                WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
                                                Path pathInfo = pathEvent.context();
                                                if (Files.isDirectory(((Path)(key.watchable())).resolve(pathInfo))) {
                                                    newEvent += "^";
                                                } else {
                                                    newEvent += "*";
                                                }

                                                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                                                    newEvent += "+:" + pathInfo.getFileName();
                                                } else if (kind == ENTRY_DELETE) {
                                                    newEvent += "-:" + pathInfo.getFileName();
                                                } else {
                                                    newEvent += newEvent + ":" + pathInfo.getFileName();
                                                }
                                                System.out.println(newEvent);
                                                bw.write(newEvent);
                                                bw.newLine();
                                                bw.flush();
                                            }

                                        } while (key.reset());

                                    } catch (IOException | InterruptedException e) {
                                        throw new IllegalArgumentException("File doesn't exist.");
                                    }
                                }
                            }
                        });
                        folderTracking.start();
                    } catch (IllegalStateException e) {
                        bw.write("1");
                        bw.newLine();
                        bw.flush();
                        continue;
                    }
                }
            }

            bw.close();
            br.close();
        } catch (IOException e) {
            System.out.println("Cannot connect to the server.");
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // Close the current window
                    new FluentWarningDialog((FluentFrame) currentWindow, currentWindow.getBackground(), "Cannot connect to the server.");
                }
            });
            currentButton.setText("Connect");
        }
    }
}

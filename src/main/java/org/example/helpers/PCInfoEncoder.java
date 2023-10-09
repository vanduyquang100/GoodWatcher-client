package org.example.helpers;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class PCInfoEncoder {

    private static final String DELIMITER = ",";

    public static String encodePCInfo() {
        try {
            String pcName = InetAddress.getLocalHost().getHostName();
            String macAddress = getMacAddress();

            return pcName + DELIMITER + macAddress;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static ArrayList<String> decodePCInfo(String encodedString) {
        ArrayList<String> result = new ArrayList<>();
        String[] parts = encodedString.split(DELIMITER);
        if (parts.length == 2) {
            String pcName = parts[0];
            String macAddress = parts[1];

            result.add(pcName);
            result.add(macAddress);
        } else {
            System.out.println("Invalid encoded string");
        }
        return result;
    }

    private static String getMacAddress() {
        try {
            InetAddress ipAddress = InetAddress.getLocalHost();
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(ipAddress);

            byte[] macBytes = networkInterface.getHardwareAddress();
            StringBuilder macAddress = new StringBuilder();

            for (byte b : macBytes) {
                macAddress.append(String.format("%02X", b));
                macAddress.append("-");
            }

            if (macAddress.length() > 0) {
                macAddress.setLength(macAddress.length() - 1);
            }

            return macAddress.toString();
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
            return "";
        }
    }
}

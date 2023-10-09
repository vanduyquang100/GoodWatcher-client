package org.example.helpers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class IpEncoder {
    public static String convertToHex(String ipv4Address, int port) throws UnknownHostException {
        // Parse the IP address string into a byte array
        byte[] ipBytes = InetAddress.getByName(ipv4Address).getAddress();

        // Convert each byte of the IP address to its hexadecimal representation
        StringBuilder hexBuilder = new StringBuilder();
        for (byte b : ipBytes) {
            String hex = String.format("%02X", b);
            hexBuilder.append(hex);
        }

        // Convert the port number to its hexadecimal representation (4-digit)
        String hexPort = String.format("%04X", port);

        // Combine the IP address and port in hexadecimal form
        return hexBuilder.toString() + hexPort;
    }

    public static ArrayList<Object> decodeHex(String hexCode) {
        // Split the hexadecimal string into IP address and port parts
        String hexIpAddress = "";
        String hexPort = "";
        try {
            hexIpAddress = hexCode.substring(0, hexCode.length() - 4);
            hexPort = hexCode.substring(hexCode.length() - 4);
        } catch(IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Unfinished connection code.");
        }

        // Convert the IP address from hexadecimal to bytes
        byte[] ipBytes = new byte[4];
        for (int i = 0; i < hexIpAddress.length(); i += 2) {
            String hexByte = hexIpAddress.substring(i, i + 2);
            int decimalValue = Integer.parseInt(hexByte, 16);
            ipBytes[i / 2] = (byte) decimalValue;
        }

        ArrayList<Object> result = new ArrayList<>();
        try {
            // Create an InetAddress object using the IP bytes
            InetAddress ipAddress = InetAddress.getByAddress(ipBytes);
            result.add(ipAddress.getHostAddress());
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("The connection code is incorrect.");
        }

        // Convert the port from hexadecimal to decimal
        result.add(Integer.parseInt(hexPort, 16));
        return result;
    }
}

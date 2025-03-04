package utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * <p>This class used for get local machine network IP address</p>
 * @author Kim Chansokpheng
 * @version 1.0
 */
public class GetMachineIP {
    public static String getMachineIP() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();

                // Ignore loopback and inactive interfaces
                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress inetAddress = addresses.nextElement();
                    // Check if it's an IPv4 address and part of a private network
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        String ip = inetAddress.getHostAddress();
                        if (ip.startsWith("192.168.") || ip.startsWith("10.") || ip.startsWith("172.")) {
//                            System.out.println("Local Network IP: " + ip);
                            return ip;
                        }
//                        if (ip.startsWith("192.168.")) {
////                            System.out.println("Local Network IP: " + ip);
//                            return ip;
//                        }
                    }
                }
            }
        } catch (SocketException e) {
            System.err.println("Error getting local IP: " + e.getMessage());
        }
        return null;
    }
}

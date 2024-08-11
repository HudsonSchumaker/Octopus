package br.com.schumaker.octopus.framework.hardware;

import java.net.InetAddress;

public class Machine {

    public static int getNumberProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getInstallDir() {
        return System.getProperty("user.dir");
    }

    public static String getOsName() {
        return System.getProperty("os.name");
    }

    public static String getOsVersion() {
        return System.getProperty("os.version");
    }

    public static String getOsArch() {
        return System.getProperty("os.arch");
    }

    public static String getJavaVersion() {
        return System.getProperty("java.version");
    }

    public static String getJavaVendor() {
        return System.getProperty("java.vendor");
    }

    public static String getJvmName() {
        return System.getProperty("java.vm.name");
    }

    public static String getLoggedUserName() {
        return System.getProperty("user.name");
    }
}

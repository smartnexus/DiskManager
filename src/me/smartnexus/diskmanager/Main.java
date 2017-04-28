package me.smartnexus.diskmanager;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static List<DiskDevice> copying = new ArrayList<>();

    public static void main(String[] args) {
        if(authorize()) {
            start();
        }
    }

    public static boolean authorize() {
        Console console = System.console();
        String pass = new String(console.readPassword("Please enter PIN to use the app: "));
        if(pass.equalsIgnoreCase("07" + (new SimpleDateFormat("HHmm").format(new Date())))) {
            System.out.println("App unlocked!");
            System.out.println("Now scanning for new media devices...");
            return true;
        } else {
            System.out.println("Passcode is not correct. Try again!");
            return false;
        }
    }

    public static void start() {
        Timer timer = new Timer();
        TimerTask scan = new TimerTask() {
            @Override
            public void run() {
                List<DiskDevice> devices = DiskDetector.getStorageDevicesDevices();
                if(devices.size() >= 1) {
                    for(DiskDevice device : devices) {
                        if(!copying.contains(device)) {
                            System.out.println("[DeviceDetector] New media device found: '" + device.getRootDirectory() + "'");
                            copying.add(device);
                            copyNewDevices();
                        }
                    }
                }

            }
        };
        timer.schedule(scan, 2000, 2000);
    }

    public static void copyNewDevices() {
        for (DiskDevice toCopy : copying) {
            System.out.println("[CopyTask] Starting to copy: '" + toCopy.getRootDirectory() + "'");
            File source = toCopy.getRootDirectory();
            File dest = new File("Examples/" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) +  "/" + toCopy.getDeviceName());
            try {
                FileUtils.copyDirectory(source, dest);
                System.out.println("[CopyTask] Finished copying: '" + toCopy.getRootDirectory() + "'");
            } catch (IOException e) {
                System.out.println("[CopyTask] Error copying: '" + toCopy.getRootDirectory() + "'");
                copying.remove(toCopy);
                start();
            }
        }
    }
}

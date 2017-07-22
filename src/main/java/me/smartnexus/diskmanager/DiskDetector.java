package me.smartnexus.diskmanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DiskDetector {

    static final String CMD_DF = "df";

    public static String executeCommand(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }

    static DiskDevice getDiskDevice(final String rootPath, final String deviceName) {
        final File root = new File(rootPath);
        try {
            return new DiskDevice(root, deviceName);
        } catch (IllegalArgumentException e) {
            System.out.println("Could not add Device: " + e.getMessage());
        }
        return null;
    }

    public static List<DiskDevice> getStorageDevicesDevices() {
        List<DiskDevice> listDevices = new ArrayList<>();
        String response = executeCommand(CMD_DF);

        String[] group = response.split("%");
        for (String path : group) {
            if(path.contains("/media/") || path.contains("/mnt/")) {
                listDevices.add(getDiskDevice(path.trim(), null));
            }
        }

        return listDevices;
    }
}

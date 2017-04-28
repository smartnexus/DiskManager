package me.smartnexus.diskmanager;

import java.io.File;


public class DiskDevice {
    private final File rootDirectory;
    private final String deviceName;

    public DiskDevice(final File rootDirectory, String deviceName){
        if(rootDirectory == null || !rootDirectory.isDirectory()){
            throw new IllegalArgumentException("Invalid root file!");
        }

        this.rootDirectory = rootDirectory;

        if(deviceName == null || deviceName.isEmpty()) {
            deviceName = rootDirectory.getName();
        }

        this.deviceName = deviceName;
    }

    public File getRootDirectory() {
        return rootDirectory;
    }

    public String getDeviceName() {
        return deviceName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.rootDirectory != null ? this.rootDirectory.hashCode() : 0);
        hash = 89 * hash + (this.deviceName != null ? this.deviceName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DiskDevice other = (DiskDevice) obj;
        return this.rootDirectory == other.rootDirectory || (this.rootDirectory != null && this.rootDirectory.equals(other.rootDirectory));
    }

    @Override
    public String toString() {
        return "RemovableDevice [Root=" + rootDirectory + ", Device Name=" + deviceName
                + "]";
    }
}

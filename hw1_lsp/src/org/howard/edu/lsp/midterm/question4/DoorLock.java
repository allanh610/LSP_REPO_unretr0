package org.howard.edu.lsp.midterm.question4;

/**
 * Network-connected and battery-powered door lock
 */
public class DoorLock extends Device implements Networked, BatteryPowered {
    private int batteryPercent;

    public DoorLock(String id, String location, int initialBattery) {
        super(id, location);
        this.setBatteryPercent(initialBattery); // Use setter for validation
    }

    @Override
    public void connect() {
        setConnected(true);
    }

    @Override
    public void disconnect() {
        setConnected(false);
    }

    @Override
    public boolean isConnected() {
        return super.isConnected();
    }

    @Override
    public int getBatteryPercent() {
        return this.batteryPercent;
    }

    @Override
    public void setBatteryPercent(int percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("battery 0..100");
        }
        this.batteryPercent = percent;
    }

    @Override
    public String getStatus() {
        String connStatus = isConnected() ? "up" : "down";
        return "DoorLock[id=" + getId() + ", loc=" + getLocation() +
                ", conn=" + connStatus + ", batt=" + batteryPercent + "%]";
    }
}

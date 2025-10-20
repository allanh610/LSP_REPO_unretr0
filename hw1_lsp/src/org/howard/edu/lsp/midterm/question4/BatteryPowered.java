package org.howard.edu.lsp.midterm.question4;

public interface BatteryPowered {

	// Returns current battery percentage
    int getBatteryPercent();

    // Update battery percentage
    // and throws IllegalArgumentException if outside 0-100
    void setBatteryPercent(int percent);
}

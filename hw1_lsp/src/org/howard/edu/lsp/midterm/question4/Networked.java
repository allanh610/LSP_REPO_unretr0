package org.howard.edu.lsp.midterm.question4;

public interface Networked {
    
	// Brings the device online
    void connect();
    // Sets the device offline
    void disconnect();
    // Reports the current connection state
    boolean isConnected();
}

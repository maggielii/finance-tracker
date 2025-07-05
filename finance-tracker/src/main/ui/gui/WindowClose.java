package ui.gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import model.EventLog;
import model.exception.LogException;
import ui.ConsolePrinter;

// Represents functionality when window closes
public class WindowClose implements WindowListener {

    private ConsolePrinter cp;

    @Override
    public void windowOpened(WindowEvent e) {
    }

    // REQUIRES: EventLog is created
    // EFFECTS: Prints the event log when window closes. If LogException occurs then prints an error message
    @Override
    public void windowClosing(WindowEvent e) {
        cp = new ConsolePrinter();
        
        try {
            cp.printLog(EventLog.getInstance());
        } catch (LogException exception) {
            System.out.println(exception.getMessage() + "System Error");
        }

        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
    
}

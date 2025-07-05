package ui;

import model.Event;
import model.EventLog;
import model.exception.LogException;

// CITATION: Used this class from https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
/**
 * Represents a screen printer for printing event log to screen.
 */
public class ConsolePrinter implements LogPrinter {

    @Override
    public void printLog(EventLog el) throws LogException {
        for (Event next : el) {
            System.out.println(next.toString() + "\n\n");
        }
    }
}

// Copyright (c) Benjamin Bergman 2024.

package com.pluralsight;

import com.googlecode.lanterna.terminal.*;

import java.io.*;

/**
 * Represents a library user interface.
 */
public class LibraryUI {
    /**
     * Entry point to run a library interface.
     *
     * @param args Ignored.
     * @throws IOException If terminal creation fails.
     */
    public static void main(String[] args) throws IOException {
        new LibraryUI().runInterface();
    }

    private void runInterface() throws IOException {
        TerminalFactory terminalFactory = new DefaultTerminalFactory();
        try (var term = terminalFactory.createTerminal()) {
            term.putString("Howdy");
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
    }
}

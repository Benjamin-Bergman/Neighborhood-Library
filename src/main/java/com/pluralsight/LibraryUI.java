// Copyright (c) Benjamin Bergman 2024.

package com.pluralsight;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.LinearLayout.*;
import com.googlecode.lanterna.terminal.*;

import java.io.*;
import java.util.*;

/**
 * Represents a library user interface.
 */
public final class LibraryUI extends BasicWindow {
    private LibraryUI() {
        super("Neighborhood Library");

        setHints(List.of(Hint.CENTERED));

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        var justify = LinearLayout.createLayoutData(Alignment.Fill, GrowPolicy.CanGrow);
        panel.addComponent(new Button("Show available books").setLayoutData(justify));
        panel.addComponent(new Button("Show checked-out books").setLayoutData(justify));
        panel.addComponent(new Button("Exit", this::close).setLayoutData(justify));
        setComponent(panel);
    }

    /**
     * Entry point to run a library interface.
     *
     * @param args Ignored.
     * @throws IOException On an IO error.
     */
    public static void main(String[] args) throws IOException {
        try (var screen = new DefaultTerminalFactory().createScreen()) {
            screen.startScreen();

            var gui = new MultiWindowTextGUI(screen);
            var window = new LibraryUI();
            gui.addWindow(window);
            window.waitUntilClosed();

            screen.stopScreen();
        }
    }
}

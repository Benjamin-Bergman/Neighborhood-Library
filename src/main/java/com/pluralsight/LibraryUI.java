// Copyright (c) Benjamin Bergman 2024.

package com.pluralsight;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.LinearLayout.*;
import com.googlecode.lanterna.gui2.dialogs.*;
import com.googlecode.lanterna.input.*;
import com.googlecode.lanterna.terminal.*;

import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Represents a library management application.
 */
public final class LibraryUI extends BasicWindow {
    private static final int ISBN_LENGTH = 10;
    private final Collection<Book> books;

    /**
     * Creates a new {@link BasicWindow} that runs this application.
     */
    @SuppressWarnings({"ReassignedVariable", "WeakerAccess"})
    public LibraryUI() {
        super("Library Tracker");

        Collection<Book> tempBooks = null;
        try (InputStream is = LibraryUI.class.getResourceAsStream("/Books.txt")) {
            if (is != null)
                tempBooks = new BufferedReader(new InputStreamReader(is))
                        .lines()
                        .parallel()
                        .map(s -> new Book(s.substring(0, ISBN_LENGTH), s.substring(ISBN_LENGTH)))
                        .collect(Collectors.toList());
        } catch (IOException ignored) {
        }
        //noinspection SpellCheckingInspection
        books = (tempBooks == null) ? List.of(new Book[]{
                new Book("0310450470", "NIV Bible"),
                new Book("142261039X", "Artscroll English Tanach"),
                new Book("097730096X", "The Clear Quran"),
        }) : tempBooks;

        setHints(List.of(Hint.CENTERED));

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        var justify = LinearLayout.createLayoutData(Alignment.Fill, GrowPolicy.CanGrow);
        panel.addComponent(new Button("Check out a book", this::showAvailable).setLayoutData(justify));
        panel.addComponent(new Button("Return a book", this::showCheckedOut).setLayoutData(justify));
        panel.addComponent(new Button("Exit", this::close).setLayoutData(justify));
        setComponent(panel);
    }

    /**
     * Runs the program as a standalone application.
     *
     * @param args Unused.
     * @throws IOException Thrown if TUI creation fails.
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

    @Override
    public boolean handleInput(KeyStroke key) {
        if (key.getKeyType() == KeyType.Character)
            //noinspection SwitchStatementWithoutDefaultBranch
            switch (key.getCharacter()) {
                case 'c', 'C' -> showAvailable();
                case 'r', 'R' -> showCheckedOut();
                case 'e', 'E' -> close();
            }

        return super.handleInput(key);
    }

    @SuppressWarnings("FeatureEnvy")
    private void showAvailable() {
        showBookList(
                "Check Out Book",
                bk -> !bk.isCheckedOut(),
                bk -> "%s - %s".formatted(bk.getIsbn(), bk.getTitle()),
                this::checkoutBook,
                "Sorry, there's nothing to check out right now.");
    }

    @SuppressWarnings("FeatureEnvy")
    private void showCheckedOut() {
        showBookList(
                "Check In Book",
                Book::isCheckedOut,
                bk -> "[%s] %s - %s".formatted(bk.getCheckedOutTo(), bk.getIsbn(), bk.getTitle()),
                this::checkinBook,
                "Sorry, there's nothing to check in right now.");
    }

    private void showBookList(
            String title,
            Predicate<? super Book> filter,
            Function<? super Book, String> display,
            Consumer<? super Book> function,
            String failureMessage) {

        var usedBooks = books.stream().filter(filter).toList();

        if (usedBooks.isEmpty()) {
            MessageDialog.showMessageDialog(
                    getTextGUI(),
                    "Error",
                    failureMessage,
                    MessageDialogButton.OK
            );
            return;
        }

        var builder = new ActionListDialogBuilder()
                .setTitle(title);

        for (var book : usedBooks)
            //noinspection ObjectAllocationInLoop
            builder.addAction(display.apply(book), () -> function.accept(book));

        builder.build().showDialog(getTextGUI());
    }

    private void checkoutBook(Book bk) {
        String name = TextInputDialog.showDialog(
                getTextGUI(),
                "Enter Name",
                "Please enter you name for our records.",
                "");

        if ((name == null) || name.isEmpty() || name.isBlank()) {
            MessageDialog.showMessageDialog(
                    getTextGUI(),
                    "Canceled",
                    "The book has not been checked out.",
                    MessageDialogButton.OK);
            return;
        }

        bk.checkout(name);
        MessageDialog.showMessageDialog(
                getTextGUI(),
                "Success",
                "Thank you for checking out %s.".formatted(bk.getTitle()),
                MessageDialogButton.OK);
    }

    private void checkinBook(Book bk) {
        bk.checkin();
        MessageDialog.showMessageDialog(
                getTextGUI(),
                "Success",
                "Thank you for returning %s.".formatted(bk.getTitle()),
                MessageDialogButton.OK);
    }
}

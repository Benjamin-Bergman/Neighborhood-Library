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

final class LibraryUI extends BasicWindow {
    @SuppressWarnings({"SpellCheckingInspection", "HardcodedFileSeparator"})
    private final Book[] books = {
            new Book("1853260088", "Moby Dick"),
            new Book("0593084691", "Humble Pi: When Math Goes Wrong in the Real World"),
            new Book("1593272812", "Land of Lisp: Learn to Program in Lisp, One Game at a Time!"),
            new Book("0310450470", "NIV Bible"),
            new Book("142261039X", "Artscroll English Tanach"),
            new Book("097730096X", "The Clear Quran"),
            new Book("133813437X", "The Silver Eyes: Five Nights at Freddy’s"),
            new Book("1568811306", "Winning Ways for Your Mathematical Plays: Volume 1"),
            new Book("0743477111", "Romeo and Juliet"),
            new Book("0765382032", "The Three-Body Problem"),
            new Book("0762499869", "Math Games with Bad Drawings: 75 1/4 Simple, Challenging, Go-Anywhere Games―And Why They Matter"),
            new Book("0525537090", "How To: Absurd Scientific Advice for Common Real-World Problems")
    };

    private LibraryUI() {
        super("Neighborhood Library");

        setHints(List.of(Hint.CENTERED));

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        var justify = LinearLayout.createLayoutData(Alignment.Fill, GrowPolicy.CanGrow);
        panel.addComponent(new Button("Check out a book", this::showAvailable).setLayoutData(justify));
        panel.addComponent(new Button("Return a book", this::showCheckedOut).setLayoutData(justify));
        panel.addComponent(new Button("Exit", this::close).setLayoutData(justify));
        setComponent(panel);
    }

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
                "Check out a book",
                bk -> !bk.isCheckedOut(),
                bk -> "%s - %s".formatted(bk.getIsbn(), bk.getTitle()),
                this::checkoutBook,
                "Sorry, there's nothing to check out right now.");
    }

    @SuppressWarnings("FeatureEnvy")
    private void showCheckedOut() {
        showBookList(
                "Check in a book",
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
        var usedBooks = Arrays.stream(books).filter(filter).toList();

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
                .setTitle(title)
                .setDescription("Choose a book");

        for (var book : usedBooks)
            //noinspection ObjectAllocationInLoop
            builder.addAction(display.apply(book), () -> function.accept(book));

        builder.build().showDialog(getTextGUI());
    }

    private void checkoutBook(Book bk) {
        String name = TextInputDialog.showDialog(
                getTextGUI(),
                "Name",
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

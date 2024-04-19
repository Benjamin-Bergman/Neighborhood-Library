// Copyright (c) Benjamin Bergman 2024.

package com.pluralsight;

/**
 * Represents a single book. Can be checked out to a specific person.
 * Otherwise, the book itself is immutable.
 */
@SuppressWarnings({"NewClassNamingConvention", "WeakerAccess"})
public final class Book {
    @SuppressWarnings("FieldNamingConvention")
    private static int idCounter = 1;
    @SuppressWarnings("FieldNamingConvention")
    private final String isbn;
    private final String title;
    @SuppressWarnings("FieldNamingConvention")
    private final int id;
    private boolean checkedOut;
    private String checkedOutTo = "";

    /**
     * @param isbn  The ISBN of this book.
     * @param title The title of this book.
     */
    @SuppressWarnings("ParameterHidesMemberVariable")
    public Book(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
        checkedOut = false;
        id = idCounter;
        //noinspection AssignmentToStaticFieldFromInstanceMethod
        idCounter++;
    }

    /**
     * @return The unique ID for this book.
     */
    public int getId() {
        return id;
    }

    /**
     * @return This book's ISBN.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @return This book's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return {@code true} if someone has checked out this book.
     */
    public boolean isCheckedOut() {
        return checkedOut;
    }

    /**
     * @return The person who checked out this book, or {@code ""} if no such person exists.
     */
    public String getCheckedOutTo() {
        return checkedOutTo;
    }

    /**
     * Checks this book out to the specified person.
     *
     * @param name The person checking out the book.
     */
    public void checkout(String name) {
        checkedOut = true;
        checkedOutTo = name;
    }

    /**
     * Check in this book, meaning no person has it checked out.
     */
    public void checkin() {
        checkedOut = false;
        checkedOutTo = "";
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Book other) && (getId() == other.getId());
    }

    @Override
    public int hashCode() {
        return getId();
    }
}

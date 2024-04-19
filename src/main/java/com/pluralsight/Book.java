// Copyright (c) Benjamin Bergman 2024.

package com.pluralsight;

@SuppressWarnings("NewClassNamingConvention")
class Book {
    @SuppressWarnings("FieldNamingConvention")
    private static int idCounter = 1;
    @SuppressWarnings("FieldNamingConvention")
    private final String isbn;
    private final String title;
    @SuppressWarnings("FieldNamingConvention")
    private final int id;
    private boolean checkedOut;
    private String checkedOutTo = "";

    @SuppressWarnings("ParameterHidesMemberVariable")
    Book(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
        checkedOut = false;
        id = idCounter;
        //noinspection AssignmentToStaticFieldFromInstanceMethod
        idCounter++;
    }

    @SuppressWarnings("WeakerAccess")
    int getId() {
        return id;
    }

    String getIsbn() {
        return isbn;
    }

    String getTitle() {
        return title;
    }

    boolean isCheckedOut() {
        return checkedOut;
    }

    String getCheckedOutTo() {
        return checkedOutTo;
    }

    void checkout(String name) {
        checkedOut = true;
        checkedOutTo = name;
    }

    void checkin() {
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

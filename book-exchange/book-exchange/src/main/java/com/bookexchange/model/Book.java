package com.bookexchange.model;

public class Book {
    private String title;
    private String seller;
    private String contact;
    private double price;

    // Add constructor
    public Book(String title, String seller, String contact, double price) {
        this.title = title;
        this.seller = seller;
        this.contact = contact;
        this.price = price;
    }

    // Add getters
    public String getTitle() {
        return title;
    }

    public String getSeller() {
        return seller;
    }

    public String getContact() {
        return contact;
    }

    public double getPrice() {
        return price;
    }
}

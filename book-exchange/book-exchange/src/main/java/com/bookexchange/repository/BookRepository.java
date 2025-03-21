package com.bookexchange.repository;

import com.bookexchange.model.Book;
import com.bookexchange.model.User;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private static final List<Book> books = new ArrayList<>();
    static {
        // Predefined books
        books.add(new Book("Java Programming", "Giridhar", "9876543210", 450.0));
        books.add(new Book("Data Structures", "Giridhar", "9876543210", 500.0));
        books.add(new Book("Algorithms Unlocked", "Giridhar", "9876543210", 600.0));
        books.add(new Book("Machine Learning Basics", "Rajesh", "9876543210", 750.0));
        books.add(new Book("Python for Beginners", "Rajesh", "9876543210", 350.0));
        books.add(new Book("Effective Java", "Giridhar", "9876543210", 550.0));
        books.add(new Book("C++ Primer", "Rajesh", "9876543210", 700.0));
        books.add(new Book("Operating Systems", "Giridhar", "9876543210", 650.0));
        books.add(new Book("Deep Learning", "Rajesh", "9876543210", 800.0));
        books.add(new Book("Database Management", "Giridhar", "9876543210", 400.0));
    }
    private static final List<User> users = new ArrayList<>();

    static {
        // Predefined users
        users.add(new User("Giridhar", "Testing1234!", "SELLER"));
        users.add(new User("Rajesh", "Student1234!", "BUYER"));
    }




    public static synchronized void addBook(Book book) {
        books.add(book);
    }

    public static synchronized List<Book> getAllBooks() {
        System.out.println("Fetching books: " + books.size() + " available.");
        return new ArrayList<>(books);  // Ensure new list is returned
    }


    public static synchronized boolean removeBook(String title) {
        return books.removeIf(b -> b.getTitle().equalsIgnoreCase(title));
    }

    public static User authenticate(String username, String password) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }


}

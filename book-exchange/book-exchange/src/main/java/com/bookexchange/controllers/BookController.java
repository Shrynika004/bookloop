package com.bookexchange.controllers;

import com.bookexchange.model.Book;
import com.bookexchange.model.User;
import com.bookexchange.repository.BookRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "BookServlet", urlPatterns = {"/login", "/sell", "/buy", "/logout", "/books"})
public class BookController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        HttpSession session = request.getSession(false);

        if ("/login".equals(path)) {
            request.getRequestDispatcher("/WEB-INF/views/login.html").forward(request, response);
        } else if ("/books".equals(path)) {
            if (session == null || session.getAttribute("user") == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Get all available books
            List<Book> books = BookRepository.getAllBooks();
            request.setAttribute("books", books);
            request.setAttribute("user", session.getAttribute("user"));

            // Handle success messages
            String sellMessage = (String) session.getAttribute("sellMessage");
            if (sellMessage != null) {
                request.setAttribute("sellMessage", sellMessage);
                session.removeAttribute("sellMessage");
            }

            String purchaseMessage = (String) session.getAttribute("purchaseMessage");
            if (purchaseMessage != null) {
                request.setAttribute("purchaseMessage", purchaseMessage);
                session.removeAttribute("purchaseMessage");
            }

            request.getRequestDispatcher("/WEB-INF/views/books.html").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String path = request.getServletPath();
        HttpSession session = request.getSession();

        switch (path) {
            case "/login":
                handleLogin(request, response, session); // Add this line!
                break;
            case "/sell":
                handleSell(request, session, response);
                break;
            case "/buy":
                handleBuy(request, session, response);
                break;
            case "/logout":
                session.invalidate();
                response.sendRedirect(request.getContextPath() + "/login");
                break;
        }
    }



    private void handleLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = BookRepository.authenticate(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/books");
        } else {
            response.sendRedirect(request.getContextPath() + "/login?error=true");
        }
    }

    private void handleSell(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws IOException {
        User user = (User) session.getAttribute("user");
        if (user == null || !"SELLER".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/books");
            return;
        }

        // Add book to repository
        Book book = new Book(
                request.getParameter("title"),
                user.getUsername(),
                request.getParameter("contact"),
                Double.parseDouble(request.getParameter("price"))
        );
        BookRepository.addBook(book);

        // Set success message and redirect
        session.setAttribute("sellMessage", "Book added successfully!");
        response.sendRedirect(request.getContextPath() + "/books");
    }

    private void handleBuy(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws IOException {
        String bookTitle = request.getParameter("bookTitle");

        boolean removed = BookRepository.removeBook(bookTitle);
        if (removed) {
            session.setAttribute("purchaseMessage", "You have purchased the book: " + bookTitle);
        } else {
            session.setAttribute("purchaseMessage", "Error: Book not found!");
        }

        response.sendRedirect(request.getContextPath() + "/books");
    }
}

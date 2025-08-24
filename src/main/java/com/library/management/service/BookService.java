package com.library.management.service;

import com.library.management.entity.Book;
import com.library.management.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service  // 1️⃣ Marks this as a Service layer (business logic)
public class BookService {

    private final BookRepository bookRepository;

    // 2️⃣ Constructor Injection (best practice in Spring Boot)
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // 3️⃣ Add a new book
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // 4️⃣ Get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // 5️⃣ Find a book by ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // 6️⃣ Borrow a book (mark unavailable)
    public String borrowBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            Book b = book.get();
            if (b.isAvailable()) {
                b.setAvailable(false);
                bookRepository.save(b);
                return "✅ You borrowed: " + b.getTitle();
            } else {
                return "⚠️ Sorry, " + b.getTitle() + " is already borrowed.";
            }
        }
        return "❌ Book not found with ID: " + id;
    }

    // 7️⃣ Return a book (mark available again)
    public String returnBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            Book b = book.get();
            if (!b.isAvailable()) {
                b.setAvailable(true);
                bookRepository.save(b);
                return "✅ You returned: " + b.getTitle();
            } else {
                return "⚠️ " + b.getTitle() + " was not borrowed.";
            }
        }
        return "❌ Book not found with ID: " + id;
    }

    // 8️⃣ Delete a book by ID
    public String deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return "🗑️ Book deleted with ID: " + id;
        }
        return "❌ Book not found with ID: " + id;
    }
}

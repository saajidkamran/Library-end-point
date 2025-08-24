package com.library.management.service;

import com.library.management.entity.Book;
import com.library.management.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class BookServiceTest {
    private BookService bookService;
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);
    }
    @Test
    void addBook() {
        Book book = new Book("Saajid","sk");
        when(bookRepository.save(book)).thenReturn(book);
        Book savedBook = bookService.addBook(book);
        assertEquals("Saajid",savedBook.getTitle());
        verify(bookRepository, times(1)).save(book);
    }
    @Test
    void getAllBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(
                new Book("Book A", "Author A"),
                new Book("Book B", "Author B")
        ));
        List<Book> books = bookService.getAllBooks();
        assertEquals("Book A", books.get(0).getTitle());
        assertEquals("Book B", books.get(1).getTitle());
        assertEquals(2, books.size());
    }
    @Test
    void testBorrowBookSucessfully() {
        Book book = new Book("Saajid","sk");
        book.setAvailable(true);
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        String result = bookService.borrowBook(book.getId());
        assertEquals("✅ You borrowed: Saajid", result);
        assertFalse(book.isAvailable());
        verify(bookRepository).save(book);
    }
    @Test
    void testBorrowBookFail() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        String result = bookService.borrowBook(1L);
        assertEquals("❌ Book not found with ID: 1", result);
    }
    @Test
    void testReturnBookSuccess() {
        Book book = new Book("Return Book", "Tester");
        book.setAvailable(false);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        String result = bookService.returnBook(1L);

        assertEquals("✅ You returned: Return Book", result);
        assertTrue(book.isAvailable());
    }

}

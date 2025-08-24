package com.library.management.controller;

import com.library.management.entity.Book;
import com.library.management.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(BookController.class)  // 1️⃣ loads only controller for testing
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean  // 2️⃣ Spring injects mock BookService
    private BookService bookService;

    @Test
    void testGetAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(
                List.of(new Book("Book A", "Author A"))
        );

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("Book A")));
    }

    @Test
    void testGetBookById() throws Exception {
        when(bookService.getBookById(1L))
                .thenReturn(Optional.of(new Book("Book B", "Author B")));

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Book B")));
    }
}

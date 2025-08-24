package com.library.management;

import com.library.management.entity.Book;
import com.library.management.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest  // 1️⃣ Load full Spring Boot app context
@AutoConfigureMockMvc // 2️⃣ Allows HTTP request testing without starting real server
@Transactional // 3️⃣ Each test runs in isolation (rolls back after)
class BookIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testAddAndGetBook() throws Exception {
        // 4️⃣ Add a book via API
        mockMvc.perform(post("/books")
                        .contentType("application/json")
                        .content("{\"title\":\"Test Book\", \"author\":\"Tester\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test Book")));

        // 5️⃣ Verify it exists in DB
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("Test Book")));
    }

    @Test
    void testBorrowAndReturnBook() throws Exception {
        // Add book directly in repository
        Book book = new Book("Borrowed Book", "Tester");
        bookRepository.save(book);

        Long bookId = book.getId(); // ✅ capture actual ID

        // Borrow it
        mockMvc.perform(put("/books/" + bookId + "/borrow"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("✅ You borrowed")));
//TODO:add one to update  retiurn book
        // Return it
        mockMvc.perform(put("/books/" + bookId +"/borrow"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("✅ You returned")));
    }

}

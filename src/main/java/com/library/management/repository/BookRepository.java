package com.library.management.repository;

import com.library.management.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  // 1️⃣ Marks this as a Spring Data Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // 2️⃣ JpaRepository already gives us CRUD methods:
    // save(), findById(), findAll(), deleteById(), etc.

    // 3️⃣ We can also add custom queries by method name:
    Book findByTitle(String title); // finds a book by its title
}

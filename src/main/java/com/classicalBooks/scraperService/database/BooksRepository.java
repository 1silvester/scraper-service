package com.classicalBooks.scraperService.database;

import com.classicalBooks.scraperService.models.Books;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Books, Long> {

        Books findByTitle(String title);
}

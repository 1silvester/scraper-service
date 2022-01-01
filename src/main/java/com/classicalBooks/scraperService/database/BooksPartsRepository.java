package com.classicalBooks.scraperService.database;

import com.classicalBooks.scraperService.models.BooksParts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksPartsRepository extends JpaRepository<BooksParts, Long> {

    BooksParts getByAuthorAndBookAndPart(String author, String book, String part);
}

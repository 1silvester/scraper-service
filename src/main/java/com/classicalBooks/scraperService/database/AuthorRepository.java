package com.classicalBooks.scraperService.database;


import com.classicalBooks.scraperService.models.Authors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Authors, Long> {
}

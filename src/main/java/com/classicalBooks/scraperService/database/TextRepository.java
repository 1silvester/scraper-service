package com.classicalBooks.scraperService.database;

import com.classicalBooks.scraperService.models.Text;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextRepository extends JpaRepository<Text, Long> {

}

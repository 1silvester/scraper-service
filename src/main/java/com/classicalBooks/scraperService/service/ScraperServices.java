package com.classicalBooks.scraperService.service;

import java.util.Map;

public interface ScraperServices {

    Map<String, String> listAuthors();
    Map<String, String> searchBooksByAuthors(String author);
    Map<String, String> searchBooks(String author, String title);
    Map<String, String> searchBooksByPart(String author, String book, String part);
}

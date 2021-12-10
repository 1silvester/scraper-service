package com.classicalBooks.scraperService.service;

import java.util.Map;

public interface ScraperServices {

    public Map<String, String> listAuthors();
    public Map<String, String> searchBooksByAuthors(String author);
    public Map<String, String> searchBooks(String title);
    public Map<String, String> searchBooksByPart(String author, String book, String part);
}

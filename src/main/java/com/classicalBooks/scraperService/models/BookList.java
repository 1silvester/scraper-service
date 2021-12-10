package com.classicalBooks.scraperService.models;


import java.util.List;

public class BookList {

//    @Id
    private String author;
    private List<String> bookList;

    public BookList()
    {}

    public BookList(String author, List<String> bookList) {
        this.author = author;
        this.bookList = bookList;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getBookList() {
        return bookList;
    }

    public void setBookList(List<String> bookList) {
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return "BookList{" +
                "author='" + author + '\'' +
                ", bookList=" + bookList +
                '}';
    }
}

package com.classicalBooks.scraperService.models;

import javax.persistence.*;

@Entity(name = "BooksPartsRepository")
public class BooksParts {


    @Id
    @SequenceGenerator(
            name = "booksParts_sequence",
            sequenceName = "booksParts_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "booksPart_sequence"
    )
    private long id;
    @Column(
            name = "chapter",
            updatable = true,
            columnDefinition = "Text"
    )
    private String chapter;
    @Column(
            name = "chapterLink",
            updatable = true,
            columnDefinition = "Text"
    )
    private String chapterLink;

    @ManyToOne
    private Books books;


    public BooksParts() {
    }

    public BooksParts(String chapter, String chapterLink) {
        this.chapter = chapter;
        this.chapterLink = chapterLink;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getChapterLink() {
        return chapterLink;
    }

    public void setChapterLink(String chapterLink) {
        this.chapterLink = chapterLink;
    }

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }
}

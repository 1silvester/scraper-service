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
            name = "book",
            updatable = false,
            unique = false,
            columnDefinition = "Text"
    )
    private String book;
    @Column(
            name = "part",
            updatable = true,
            columnDefinition = "Text",
            unique = true
    )
    private String part;
    @Column(
            name = "partLink",
            updatable = true,
            columnDefinition = "Text",
            unique = true
    )
    private String partLink;

    @Column(
            name = "author",
            updatable = true,
            columnDefinition = "Text"
    )
    private String author;

    @ManyToOne
    private Books books;


    public BooksParts() {
    }

    public BooksParts(String author, String book, String part, String partLink)
    {
        this.author = author;
        this.book = book;
        this.part = part;
        this.partLink = partLink;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getPartLink() {
        return partLink;
    }

    public void setPartLink(String partLink) {
        this.partLink = partLink;
    }

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }
}

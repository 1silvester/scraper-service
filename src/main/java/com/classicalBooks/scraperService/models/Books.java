package com.classicalBooks.scraperService.models;


import javax.persistence.Entity;
import javax.persistence.*;


@Entity(name = "Books")
public class Books {

    @Id
    @SequenceGenerator(
            name = "books_sequence",
            sequenceName = "books_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "books_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "title",
            updatable = false,
            columnDefinition = "TEXT"
    )
    private String title;

    @Column(
            name = "titleLink",
            updatable = true,
            columnDefinition = "TEXT"
    )
    private String titleLink;
    @Column(
            name="titlePart",
            updatable = true,
            columnDefinition = "TEXT"
    )
    private String titlePart;

    @Column(
            name = "titlePartLink",
            updatable = true,
            columnDefinition = "Text"
    )
    private String titlePartLink;
//    @ManyToOne(
//            cascade = {CascadeType.ALL},
//            targetEntity = Authors.class
//    )
//    @JoinColumn(name = "author_id")
//    private String author;


    @ManyToOne(
            cascade = {CascadeType.ALL},
            targetEntity = Authors.class
    )
    @JoinColumn(name = "author_id")
    private Long author_id;


    public Books()
    {}

    public Books(String title, String titleLink) {
        this.title = title;
        this.titleLink = titleLink;
    }

//    public Books(String title, String titleLink, String author) {
//        this.title = title;
//        this.titleLink = titleLink;
//        this.author = author;
//    }


    public Books(String title, String titleLink, Long author_id) {
        this.title = title;
        this.titleLink = titleLink;
        this.author_id = author_id;
    }

//    public String getAuthor() {
//        return author;
//    }

//    public void setAuthor(Authors author) {
//    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle(){return title;}

    public void setAuthor(String author) {}

    public String getTitleLink() {
        return titleLink;
    }

    public void setTitleLink(String titleLink) {
        this.titleLink = titleLink;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitlePart() {
        return titlePart;
    }

    public void setTitlePart(String titlePart) {
        this.titlePart = titlePart;
    }

    public String getTitlePartLink() {
        return titlePartLink;
    }

    public void setTitlePartLink(String titlePartLink) {
        this.titlePartLink = titlePartLink;
    }

    public Long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Long author_id) {
        this.author_id = author_id;
    }

    @Override
    public String toString() {
        return "Books{" +
                "author='" + author_id + '\'' +
                ", titleLink=" + titleLink +
                '}';
    }
}

package com.classicalBooks.scraperService.models;

import javax.persistence.*;

@Entity(name = "Text")
public class Text {

    @Id
    @SequenceGenerator(
            name = "text_sequence",
            sequenceName = "text_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "text_sequence"
    )
    @Column(
            name= "id",
            updatable = false
    )
    private Long id;

    private String author;
    private String title;
    private String translator;
    private String dateWritten;
    private String translationProject;

    @ManyToOne(
            cascade = CascadeType.ALL,
            targetEntity = Books.class
    )
    private Books books;

    public Text() {

    }

    public Text(String author,String title)
    {
        super();
        this.author = author;
        this.title = title;
    }


    public Text(String author, String title, String translator, String dateWritten)
    {
        super();
        this.author = author;
        this.title = title;
        this.translator = translator;
        this.dateWritten = dateWritten;

    }

    public Text(String author, String title, String translator, String dateWritten, String translationProject)
    {
        super();
        this.author = author;
        this.title = title;
        this.translator = translator;
        this.dateWritten = dateWritten;
        this.translationProject = translationProject;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getDateWritten() {
        return dateWritten;
    }

    public void setDateWritten(String dateWritten) {
        this.dateWritten = dateWritten;
    }

    public String getTranslationProject() {
        return translationProject;
    }

    public void setTranslationProject(String translationProject) {
        this.translationProject = translationProject;
    }

    @Override
    public String toString() {
        return "Text{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", translator='" + translator + '\'' +
                ", dateWritten='" + dateWritten + '\'' +
                ", translationProject='" + translationProject + '\'' +
                '}';
    }
}

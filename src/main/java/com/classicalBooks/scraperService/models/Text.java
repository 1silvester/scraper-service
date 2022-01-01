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

    @Column(
            name="author",
            updatable = true,
            columnDefinition = "Text",
            unique = false
    )
    private String author;

    @Column(
            name="title",
            updatable = true,
            columnDefinition = "Text",
            unique = false
    )
    private String title;

    @Column(
            name="part",
            updatable = true,
            columnDefinition = "Text",
            unique = true
    )
    private String part;

    @Column(
            name = "translator",
            updatable = true,
            columnDefinition = "Text",
            unique = false
    )
    private String translator;

    @Column(
            name = "dateWritten",
            updatable = true,
            columnDefinition = "Text",
            unique = false
    )
    private String dateWritten;

    @Column(
            name = "translationProject",
            updatable = true,
            columnDefinition = "Text",
            unique = false
    )
    private String translationProject;

    @Column(
            name="textFileLink",
            updatable = true,
            columnDefinition = "Text",
            unique = true
    )
    private String textFileLink;

    @ManyToOne(
            cascade = CascadeType.ALL,
            targetEntity = Books.class
    )
    private Books books;

    public Text() {

    }

    public Text(String author, String title, String part, String translator, String textFileLink)
    {
        super();
        this.author = author;
        this.title = title;
        this.part = part;
        this.translator = translator;
        this.textFileLink = textFileLink;
    }

    public Text(String author, String title,String translator, String textFileLink)
    {
        super();
        this.author = author;
        this.title = title;
        this.translator = translator;
        this.textFileLink = textFileLink;
    }


    public Text(String author, String title,String part ,String translator, String dateWritten, String textFileLink)
    {
        super();
        this.author = author;
        this.title = title;
        this.part = part;
        this.translator = translator;
        this.dateWritten = dateWritten;
        this.textFileLink = textFileLink;

    }

    public Text(String author, String title,String part ,String translator, String dateWritten, String translationProject, String textFileLink)
    {
        super();
        this.author = author;
        this.title = title;
        this.part = part;
        this.translator = translator;
        this.dateWritten = dateWritten;
        this.translationProject = translationProject;
        this.textFileLink = textFileLink;
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

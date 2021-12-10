package com.classicalBooks.scraperService.models;

public class Text {

    private String author;
    private String title;
    private String translator;
    private String dateWritten;
    private String translationProject;


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
}

package com.classicalBooks.scraperService.models;


import javax.persistence.*;
import java.util.List;

@Entity(name = "Authors")
public class Authors {

    @Id
    @SequenceGenerator(
            name = "authors_sequence",
            sequenceName = "authors_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE,
            generator = "authors_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "name",
            updatable = false,
            columnDefinition = "TEXT"

    )
    private String name;

    @Column(
            name = "link",
            updatable = true,
            columnDefinition = "TEXT"
    )
    private String link;

    @OneToMany()
    private List<Books> books;

    public Authors() {
    }

    public Authors(String name, String link) {
        this.name = name;
        this.link = link;

    }

    public Authors(String name, List<Books> books)
    {
        this.name = name;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<Books> getBookListList() {
        return books;
    }

    public void setBookListList(List<Books> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Authors{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}

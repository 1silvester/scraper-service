package com.classicalBooks.scraperService.service;


import com.classicalBooks.scraperService.database.AuthorRepository;
import com.classicalBooks.scraperService.database.BooksPartsRepository;
import com.classicalBooks.scraperService.database.BooksRepository;
import com.classicalBooks.scraperService.models.Authors;
import com.classicalBooks.scraperService.models.Books;
import com.classicalBooks.scraperService.models.Text;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WebScraperService implements ScraperServices{

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BooksRepository booksRepository;

    @Autowired
    BooksPartsRepository booksPartsRepository;


    @Value("${classical.mit.base}")
    private String mitBase;

    @Value("${classical.mit.url}")
    private String mitUrl;

    @Value("${classical.mit.browse}")
    private String mitBrowse;



    private final Map<String, String> authors = new HashMap<>();

    private final Map<String, String> booksAndLinks = new HashMap<>();

    private Map<String, String> bookLinksMap = new HashMap<>();

    private List<Text> authorTextObjects;
    private Text textObject = new Text();

    @Override
    public Map<String,String> listAuthors() {
        System.out.println(mitUrl);
        Books books = new Books();
        try {
            Document document = Jsoup.connect(mitUrl).get();

            Elements body = document.select("a");

            for (Element l : body)
            {
                if (l.attr("target").equals("browse"))
                {
                    authors.put(l.text(), l.attr("href"));
                    authorRepository.save(new Authors(l.text(), l.attr("href")));
//                    textLink = new TextLink(l.text(), l.attr("href"));
//                    database.save(textLink);
                }
            }
        }
        catch (Exception exception){
            exception.printStackTrace();
        }

//        database.findAll().forEach(textLink1 -> {
//            System.out.println(textLink1.getAuthor());
//        });


//      authors.forEach((k,v) -> System.out.println("key: "+ k + " value: "+ v));


        /*
         * url http://classics.mit.edu//Browse/authors.html
         * returns key: author value: link to scrape
         * returns key: Sa'di value: browse-Sadi.html
         * */
        return authors;
    }

    /*
    * @param authorName The author name you want to get accessed from the database.
    *
    * Author name is used as a key to find the final part of the URL. Forming a URL such as
    * the following URL http://classics.mit.edu/Browse/browse-Homer.html.
    * This can then be used to scrape the books from that author.
    *
    * @return schema key: Book Title , value: link
    * @return key: Homeric Hymns , value: /Homer/hh.1.html
    * */
    @Override
    public Map<String, String> searchBooksByAuthors(String authorName) {
//        Map<String, String> authorMap = listAuthors();
        Authors authorUrl = authorRepository.findByName(authorName);
        System.out.println(authorUrl.getName() +" " +authorUrl.getLink());
//        String authorUrl = authors.get(authorName);
        String concatUrl = mitBrowse + authorUrl.getLink();

        Books books = new Books();
        List<Books> booksList = new ArrayList<>();
        Map<String, String> list = new HashMap<>();
        try
        {
            final Document document = Jsoup.connect(concatUrl).get();
            Elements aTag = document.select("a");
            for(Element b: aTag)
            {
                if(b.attr("target").equals("_parent"))
                {
                    booksAndLinks.put(b.text(), b.attr("href"));
                    list.put(b.text(), b.attr("href"));
                    booksList.add(new Books(b.text(), b.attr("href")));
                    booksRepository.save(new Books(b.text(), b.attr("href")));
                }
//                list.add(b.select("u").text());
//                list.forEach((k,v) -> System.out.println("key "+ k + " " + v ));
            }

//            booksRepository.save(new Books("","", authorName));
            //this is to make a database in the near future
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return booksAndLinks;
    }

    /**
     * @param title of the book
     *          The general schema for the return
     * @returns "Book/book part " "html Link /txt link"
     *
     *          Two different kinds of returns are possible from this method.
     *          They are placed in two different objects. The html requires further processing.
     *
     * @returns "Book VII": "iliad.7.vii.html",
     * @returns "The Great Learning": "learning.1b.txt"
     */
    @Override
    public Map<String, String> searchBooks(String title) {
        Books books = booksRepository.findByTitle(title);
        String concatUrl = mitBase + books.getTitleLink();
//        String urlTitle = booksAndLinks.get(title);
//        String concatUrl = mitBase + urlTitle;


        bookLinksMap = getTextHtmlLink(concatUrl);

        if(bookLinksMap.isEmpty())
        {
            Map<String, String> books1 = getTextFileLink(title, concatUrl);

            books1.forEach((k, v) -> new Books(k,v));

         return books1;
        }
        else
            return bookLinksMap;
    }

    @Override
    public Map<String, String> searchBooksByPart(String author, String book, String part)
    {

        String bookPartName = "";
        System.out.println(book);
        Set<String> keySet = bookLinksMap.keySet();

            for(String key : keySet)
            {
                if(key.equals(book))
                    bookPartName = key;
                System.out.println("bookPartName " + bookPartName);
            }

        String bookPart = bookLinksMap.get(part);
        System.out.println("author " + author);
        String url = mitBase + author +"/"+ bookPart;
        System.out.println(url);
       return getTextFileLink(book, url);
    }

    private Map<String,String> getTextHtmlLink(String urlLink)
    {

        Map<String,String> htmlLinks = new HashMap<>();
        try{
            final Document document = Jsoup.connect(urlLink).get();
            Elements body = document.select("blockquote");
            Elements tables = body.select("table");
            Elements table = tables.select("a");
            for (Element td : table){
                htmlLinks.put(td.text(), td.attr("href"));
        }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return htmlLinks;
    }

    private Map<String,String> getTextFileLink(String bookNameOrPart, String link)
    {

        System.out.println(link);
        Map<String, String> textLink = new HashMap<>();
        try {
            final Document document = Jsoup.connect(link).get();
            Elements body = document.select("blockquote");
            Elements aTag = body.select("a");
            for (Element a : aTag)
            {
                if(a.hasAttr("href"))
                    textLink.put(bookNameOrPart, a.attr("href"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return textLink;
    }


}

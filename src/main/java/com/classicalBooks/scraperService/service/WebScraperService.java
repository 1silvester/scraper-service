package com.classicalBooks.scraperService.service;


import com.classicalBooks.scraperService.database.AuthorRepository;
import com.classicalBooks.scraperService.database.BooksPartsRepository;
import com.classicalBooks.scraperService.database.BooksRepository;
import com.classicalBooks.scraperService.database.TextRepository;
import com.classicalBooks.scraperService.models.Authors;
import com.classicalBooks.scraperService.models.Books;
import com.classicalBooks.scraperService.models.BooksParts;
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

    @Autowired
    TextRepository textRepository;


    /*
    *
    * Variables containing the URLs used to scrape
    *
    * */
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

    /*
    *
    * This method scrapes all the authors that you can read content from.
    *
    * return list of author and links to content
    * */
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
        Authors authors = new Authors();

        final Long id = authorUrl.getId();
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
                    booksRepository.save(new Books(b.text(), b.attr("href"), authorUrl.getName()));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return booksAndLinks;
    }

    /**
     * @param title of the book
     *
     * @param author
     *
     * The general schema for the return
     * @returns "Book/book part " "html Link /txt link"
     *
     *          Two different kinds of returns are possible from this method.
     *          They are placed in two different objects. The html requires further processing.
     *
     * @returns "Book VII": "iliad.7.vii.html",
     * @returns "The Great Learning": "learning.1b.txt"
     */@Override
    public Map<String, String> searchBooks(String author, String title) {

        Books books = booksRepository.findByTitle(title);
        String concatUrl = mitBase + books.getTitleLink();
        System.out.println("searchBooks concat " + concatUrl);
        Map<String, String> stringStringMap = new HashMap<>();


            bookLinksMap = getTextHtmlLink(concatUrl);

            if (checkPerseusProject(concatUrl))
            {
                stringStringMap.put(title, "This works is currently not available on this platform. It can be found via the Perseus Project at Tufts University.");
                return stringStringMap;
            }
            else if (bookLinksMap.isEmpty()) {
                System.out.println("Checking different parts ");
                Map<String, String> books1 = getTextFileLinkParts(title, concatUrl);
                System.out.println(books1.keySet() + " " + books1.get(title));

                StringBuilder authorDetails = getDetailsOfBook(title, concatUrl);
                System.out.println(authorDetails);
                String authorDetailsString = authorDetails.toString();
                int byIntString = authorDetailsString.indexOf("by");
                String subString = authorDetailsString.substring(byIntString + 2);
                textRepository.save(new Text(author, title, subString, books1.get(title)));


                return books1;
            } else {
                Map<String, String> map = bookLinksMap;
                map.forEach((k, v) -> booksPartsRepository.save(new BooksParts(author, title, k, v)));
                bookLinksMap.clear();
                return map;
            }

    }

    /**
     *
     * @param author of the book
     *
     * @param book which book
     *
     * @param part specific part of the book
     * 
     * This method scrape's the different parts of book if available, some books may come in different
     * parts. 
     * @return a text file (.txt)
     */
    @Override
    public Map<String, String> searchBooksByPart(String author, String book, String part)
    {


        BooksParts booksParts = booksPartsRepository.getByAuthorAndBookAndPart(author, book, part);
        System.out.println("searchBooksByPart " + booksParts.getPartLink());
        String url = mitBase + author + "/" + booksParts.getPartLink();
        System.out.println("url "+ url);
        Map<String,String> result = getTextFileLinkParts(part, url);
        result.forEach((k,v) -> System.out.println("Key " + k + " " + "Value "+ v));
        System.out.println(book);
//        List<String> authorDetails = getDetailsOfBook(author,url);
        StringBuilder authorDetails = getDetailsOfBook(author, url);
        System.out.println(authorDetails);

        String s = authorDetails.toString();
        System.out.println(s);

        if (s.contains("Written"))
        {
            System.out.println("written constructor");
            int byIntString = s.indexOf("by");
            int translatorIntString = s.indexOf("Translated");
            int writtenIntString = s.indexOf("Written");
            String byString = s.substring(byIntString+2);
            String writtenString = s.substring(writtenIntString, translatorIntString-1);
            textRepository.save(new Text(author, book, part, byString, writtenString, result.get(part)));


        }
        else
        {
            System.out.println("basic constructor");
        }

//        if(!result.isEmpty())
//        {
//            q = authorDetails.get(1);
//        }
//        String finalQ = q;
//        System.out.println("finalQ "  + finalQ);
//        result.forEach((k, v) -> textRepository.save(author, book, part, finalQ, v));

//        String bookPart = bookLinksMap.get(part);
//        System.out.println("author " + author);
//        String url = mitBase + author +"/"+ bookPart;
//        System.out.println(url);
//       Map<String,String> result = getTextFileLink(book, url);
//        System.out.println(result.get(book));

       return result;
    }


    /**
     * @param urlLink
     * 
     * This is a utility method it is used when a book comes in different parts. This allows you to
     * scrape the link to the text file.
     * returns Hash Map of a link to where to scrape the text, and name of the author. 
     * */
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

    /**
     * @param urlLink - html link to get the text
     * @param part - different parts of the same book
     *
     * This is a utility method it is used when a book comes in different parts. This allows you to
     * scrape the link to the text file.
     * returns Hash Map of a link to where to scrape the text, and name of the author.
     * */
    private Map<String,String> getTextHtmlLink(String part, String urlLink)
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


    /**
     *
     * @param bookNameOrPart - name of the book or different parts of the book
     * @param link - link to scrape
     * 
     * This method scrapes the link to the text file.
     * returns Hash Map containing the link to the text file and the name of the author
     * returns
     * */
    private Map<String,String> getTextFileLink(String bookNameOrPart, String link)
    {

        System.out.println(link);
        Map<String, String> textLink = new HashMap<>();
        try {
            final Document document = Jsoup.connect(link).get();
            Elements body = document.select("blockquote");
            Elements div = body.select("div");
            Elements divElements = div.attr("align", "CENTER");
            Elements aTag = divElements.select("a");
            for (Element a : aTag)
            {
                Elements aTxt = a.getElementsByAttributeValueEnding("href","txt");
                if (aTxt.hasAttr("href"))
                    textLink.put(bookNameOrPart, a.attr("href"));
                System.out.println("getTextFileLink " + a.attr("href"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return textLink;
    }

    private Map<String,String> getTextFileLinkParts(String bookNameOrPart, String link)
    {

        System.out.println(link);
        Map<String, String> textLink = new HashMap<>();
        try {
            final Document document = Jsoup.connect(link).get();
            Elements body = document.select("blockquote");
            Elements aTag = body.select("a");

            for (Element a : aTag)
            {
                Elements aText = a.getElementsByAttributeValueEnding("href", "txt");
                if (aText.hasAttr("href"))
                {
                    textLink.put(bookNameOrPart, a.attr("href"));
                    System.out.println("getTextFileLink " + a.attr("href"));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return textLink;
    }

    /*
    * @param bookPartName,
    * @param link
    *
    * return [Aesop's Fables] [By Aesop] [Translated by George Fyler Townsend]
    * */
    private StringBuilder getDetailsOfBook(String bookPartName, String link)
    {
//        List<String> result = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        try {
            final Document document = Jsoup.connect(link).get();
            Elements body = document.select("blockquote");
            Elements divTag = body.select("div");
            for (Element div : divTag)
            {
                if (div.hasAttr("align"))
                {
//                    result.add(div.text());
                    result.append(div.text());
                    System.out.println(div.text());
                }
                break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @param concatUrl
     *
     * Checks if the work is part of the perseus project.
     *
     * @returns boolean;
     * */
    private boolean checkPerseusProject(String concatUrl) {

        try{
            final Document document = Jsoup.connect(concatUrl).get();
            Elements blockQuote = document.select("blockquote");
            Elements aTag = blockQuote.select("a");
            for (Element a : aTag)
            {
                if (a.text().equals("Perseus Project"))
                {
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}

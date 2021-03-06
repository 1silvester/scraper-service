package com.classicalBooks.scraperService.service;

//import com.classicalBooks.scraperService.database.Database;
import com.classicalBooks.scraperService.models.Text;
import com.classicalBooks.scraperService.models.BookList;
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

    @Value("${classical.mit.base}")
    private String mitBase;

    @Value("${classical.mit.url}")
    private String mitUrl;

    @Value("${classical.mit.browse}")
    private String mitBrowse;



    private final Map<String, String> authors = new HashMap<>();

    private final Map<String, String> booksAndLinks = new HashMap<>();

    private Map<String, String> books = new HashMap<>();

    private Map<String, String> bookLinks = new HashMap<>();

    private List<Text> authorTextObjects;
    private Text textObject = new Text();

    @Override
    public Map<String,String> listAuthors() {
        System.out.println(mitUrl);

        try {
            Document document = Jsoup.connect(mitUrl).get();

            Elements body = document.select("a");

            for (Element l : body)
            {
                if (l.attr("target").equals("browse"))
                {
                    authors.put(l.text(), l.attr("href"));
                }
            }
        }
        catch (Exception exception){
            exception.printStackTrace();
        }


        /*
         * url http://classics.mit.edu//Browse/authors.html
         * returns key: author value: link to scrape
         * returns key: Sa'di value: browse-Sadi.html
         * */
        return authors;
    }


    @Override
    public Map<String, String> searchBooksByAuthors(String authorName) {
        String authorUrl = authors.get(authorName);
        String concatUrl = mitBrowse + authorUrl;

        List<String> list = new ArrayList<>();
        try
        {
            final Document document = Jsoup.connect(concatUrl).get();
            Elements aTag = document.select("a");
            for(Element b: aTag)
            {
                if(b.attr("target").equals("_parent"))
                {
                    booksAndLinks.put(b.text(), b.attr("href"));
                }
                list.add(b.select("u").text());
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return booksAndLinks;
    }

    @Override
    public Map<String, String> searchBooks(String book) {
        String urlTitle = booksAndLinks.get(book);
        String concatUrl = mitBase + urlTitle;

        bookLinks = getTextHtmlLink(concatUrl);

        if(bookLinks.isEmpty())
        {
            books = getTextFileLink(book, concatUrl);
         return books;
        }
        else
            return bookLinks;
    }

    @Override
    public Map<String, String> searchBooksByPart(String author, String book, String part)
    {

        String bookPartName = "";
        System.out.println(book);
        Set<String> keySet = bookLinks.keySet();

            for(String key : keySet)
            {
                if(key.equals(book))
                    bookPartName = key;
                System.out.println("bookPartName " + bookPartName);
            }

        String bookPart = bookLinks.get(part);
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

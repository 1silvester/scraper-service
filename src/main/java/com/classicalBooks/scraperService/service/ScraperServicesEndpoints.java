package com.classicalBooks.scraperService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/scraper")
public class ScraperServicesEndpoints {


    @Autowired(required = false)
    WebScraperService webScraperService;



    @RequestMapping(value = "/authors/{authorName}", method = RequestMethod.GET, produces = "application/json")
    public Map<String, String> searchBookByAuthorName(@PathVariable("authorName") String authorName)
    {

//        return scraperService.ScraperServ().searchBookByAuthor(authorName);
        if (webScraperService.searchBooksByAuthors(authorName) != null)
            return webScraperService.searchBooksByAuthors(authorName);
        else
            return searchBookByAuthorName(authorName);
    }


    @RequestMapping(value = "/authors", method = RequestMethod.GET, produces = "application/json")
    public Map<String, String> listAuthors()
    {
        if (webScraperService.listAuthors() != null)
        {
            return webScraperService.listAuthors();
        }
        else
            return listAuthors();
    }

    @RequestMapping(value = "/authors/{author}/{book}", method = RequestMethod.GET, produces = "application/json")
    public Map<String, String> searchBooks(@PathVariable("author") String author ,@PathVariable("book") String book)
    {
        if (webScraperService.searchBooks(author ,book) != null)
        {
            return webScraperService.searchBooks(author ,book);
        }
        else
            return searchBooks(author,book);
    }

    @RequestMapping(value = "/authors/{author}/{book}/{part}", method = RequestMethod.GET, produces = "application/json")
    public Map<String, String> searchBooksByPart(@PathVariable("author") String author, @PathVariable("book") String book, @PathVariable("part") String part)
    {
        if (webScraperService.searchBooksByPart(author, book, part) != null)
        {
            return webScraperService.searchBooksByPart(author, book, part);
        }
        else
            return searchBooksByPart(author,book,part);
    }
}

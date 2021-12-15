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


    //search book by author name
    @RequestMapping(value = "/by-author/{authorName}", method = RequestMethod.GET, produces = "application/json")
    public Map<String, String> searchBookByAuthorName(@PathVariable("authorName") String authorName)
    {

//        return scraperService.ScraperServ().searchBookByAuthor(authorName);
        return webScraperService.searchBooksByAuthors(authorName);
    }

//    @RequestMapping(value = "/authors", method = RequestMethod.GET, produces = "application/json")
//    public List<String> listAuthors()
//    {
////        return scraperService.ScraperServ().listAuthors();
//        List<String> stringList = new ArrayList<>();
//
//        webScraperService.listAuthors().forEach((k,v) -> stringList.add(k));
//
//        return stringList;
//    }
    @RequestMapping(value = "/authors", method = RequestMethod.GET, produces = "application/json")
    public Map<String, String> listAuthors()
    {
//        return scraperService.ScraperServ().listAuthors();
//        List<String> stringList = new ArrayList<>();
//
//        webScraperService.listAuthors().forEach((k,v) -> stringList.add(k));

        return webScraperService.listAuthors();
    }

    @RequestMapping(value = "by-book/{book}", method = RequestMethod.GET, produces = "application/json")
    public Map<String, String> searchBooks( @PathVariable("book") String book)
    {
        return webScraperService.searchBooks(book);
    }

    @RequestMapping(value = "{author}/{book}/{part}", method = RequestMethod.GET, produces = "application/json")
    public Map<String, String> searchBooksByPart(@PathVariable("author") String author, @PathVariable("book") String book, @PathVariable("part") String part)
    {
        return webScraperService.searchBooksByPart(author, book, part);
    }
}

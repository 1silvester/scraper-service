package com.classicalBooks.scraperService.configuration;

import com.classicalBooks.scraperService.service.WebScraperService;
import org.springframework.context.annotation.Bean;

public class ScraperConfig {
    @Bean
    public WebScraperService webScraperService()
    {
        return new WebScraperService();
    }
}

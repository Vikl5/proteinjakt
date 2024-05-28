package com.app.proteinjakt.scheduler;

import com.app.proteinjakt.service.ScrapingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledScraping {

    private final ScrapingService scrapingService;

    @Autowired
    public ScheduledScraping(ScrapingService scrapingService) {
        this.scrapingService = scrapingService;
    }

    @Scheduled(fixedRate = 3600000)
    public void startScraping(){
        scrapingService.scrapeGym();
    }
}

package com.app.proteinjakt.scheduler;

import com.app.proteinjakt.repository.ProductPriceRepository;
import com.app.proteinjakt.service.ScrapingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledScraping {

    private final ScrapingService scrapingService;
    private final ProductPriceRepository productPriceRepository;

    @Autowired
    public ScheduledScraping(ScrapingService scrapingService, ProductPriceRepository productPriceRepository) {
        this.scrapingService = scrapingService;
        this.productPriceRepository = productPriceRepository;
    }

    @Transactional
    @Scheduled(fixedRate = 3600000)
    public void startScraping(){
        productPriceRepository.deleteAll();
        scrapingService.scrapeGym();
    }
}

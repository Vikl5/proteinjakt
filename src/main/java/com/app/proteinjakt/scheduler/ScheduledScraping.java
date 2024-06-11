package com.app.proteinjakt.scheduler;

import com.app.proteinjakt.repository.ProductPriceRepository;
import com.app.proteinjakt.service.BodylabScraper;
import com.app.proteinjakt.service.GymgrossistenScraper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledScraping {

    private final GymgrossistenScraper gymgrossistenScraper;
    private final ProductPriceRepository productPriceRepository;
    private final BodylabScraper bodylabScraper;

    @Autowired
    public ScheduledScraping(GymgrossistenScraper gymgrossistenScraper, ProductPriceRepository productPriceRepository, BodylabScraper bodylabScraper) {
        this.gymgrossistenScraper = gymgrossistenScraper;
        this.productPriceRepository = productPriceRepository;
        this.bodylabScraper = bodylabScraper;
    }

    @Transactional
    @Scheduled(fixedRate = 3600000)
    public void startScraping(){
        productPriceRepository.deleteAll();
        gymgrossistenScraper.scrapeGym();
        bodylabScraper.scrapeBodylab();
    }
}

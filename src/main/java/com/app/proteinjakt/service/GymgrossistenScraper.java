package com.app.proteinjakt.service;

import com.app.proteinjakt.repository.ProductPriceRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.app.proteinjakt.config.Urls.GYM_GROSSISTEN;

@Service
public class GymgrossistenScraper extends AbstractBaseScraper{

    protected GymgrossistenScraper(ProductPriceRepository productPriceRepository) {
        super(productPriceRepository);
    }
    public void scrapeGym(){
        String url = GYM_GROSSISTEN.getUrl();
        try {
            Document document = Jsoup.connect(url).get();
            Elements products = document.getElementsByClass(GYM_GROSSISTEN.getHtmlTag());
            scrape(url, products);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected String getProductPriceSelectors() {
        return "div.price-sales";
    }
    @Override
    protected String getProductNameSelectors() {
         return "p.product-tile-name";

    }
    @Override
    protected String getProductOnSaleSelectors() {
        return "div.price-adjusted";
    }
    @Override
    protected String getProductUrlSelectors() {
        return "a";
    }

}

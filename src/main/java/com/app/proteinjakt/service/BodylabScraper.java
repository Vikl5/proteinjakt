package com.app.proteinjakt.service;

import com.app.proteinjakt.config.Urls;
import com.app.proteinjakt.dto.ProteinProduct;
import com.app.proteinjakt.repository.ProductPriceRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.app.proteinjakt.config.Urls.BODY_LAB;

@Service
public class BodylabScraper extends AbstractBaseScraper{
    protected BodylabScraper(ProductPriceRepository productPriceRepository) {
        super(productPriceRepository);
    }

    public void scrapeBodylab() {
        String url = BODY_LAB.getUrl();
        try {
            Document document = Jsoup.connect(url).get();
            Elements products = document.getElementsByClass(BODY_LAB.getHtmlTag());
            scrape(url, products);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String getProductPriceSelectors() {
        return "span[itemprop=price]";
    }

    @Override
    protected String getProductNameSelectors() {
        return "div.name";
    }

    @Override
    protected String getProductOnSaleSelectors() {
        return "span[class=isoffer]";
    }

    @Override
    protected String getProductUrlSelectors() {
        return "div.name > a";
    }
}

package com.app.proteinjakt.service;

import com.app.proteinjakt.dto.ProteinProduct;
import com.app.proteinjakt.repository.ProductPriceRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBaseScraper implements IScraper{
    private final ProductPriceRepository productPriceRepository;

    @Autowired
    protected abstract String getProductPriceSelectors();
    protected abstract String getProductNameSelectors();
    protected abstract String getProductOnSaleSelectors();
    protected abstract String getProductUrlSelectors();

    protected AbstractBaseScraper(ProductPriceRepository productPriceRepository) {
        this.productPriceRepository = productPriceRepository;
    }
    @Override
    public void scrape(String url, Elements products) {
        List<ProteinProduct> prices = new ArrayList<>();
        for (Element prod : products) {
            String productNameSelector = prod.getElementsByClass(getProductNameSelectors()).text();
            String productPriceSelector = prod.getElementsByClass(getProductPriceSelectors()).text();
            String productOnSaleSelector = prod.getElementsByClass(getProductOnSaleSelectors()).text();
            String productUrlSelector = url.concat(prod.select(getProductUrlSelectors()).attr("href"));

            ProteinProduct proteinProduct = new ProteinProduct();
            proteinProduct.setProductName(productNameSelector);
            proteinProduct.setWebsite(productUrlSelector);
            if (productPriceSelector.isEmpty()) {
                proteinProduct.setPrice(productOnSaleSelector);
            } else {
                proteinProduct.setPrice(productPriceSelector);
            }
            proteinProduct.setScrapedAt(LocalDateTime.now());
            prices.add(proteinProduct);
        }
        productPriceRepository.saveAll(prices);
    }
}

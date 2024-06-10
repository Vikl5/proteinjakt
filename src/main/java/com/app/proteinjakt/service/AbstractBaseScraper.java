package com.app.proteinjakt.service;

import com.app.proteinjakt.dto.ProteinProduct;
import com.app.proteinjakt.repository.ProductPriceRepository;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBaseScraper implements IScraper{
    private final ProductPriceRepository productPriceRepository;


    protected abstract String getProductPriceSelectors();
    protected abstract String getProductNameSelectors();
    protected abstract String getProductOnSaleSelectors();
    protected abstract String getProductUrlSelectors();

    protected AbstractBaseScraper(ProductPriceRepository productPriceRepository) {
        this.productPriceRepository = productPriceRepository;
    }
    @Override
    public void scrape(String url, Elements products) {
        List<ProteinProduct> productInfo = new ArrayList<>();
        for (Element prod : products) {
            ProteinProduct proteinProduct = extractDataFromElement(url, prod);
            productInfo.add(proteinProduct);
        }
        saveProductsToDatabase(productInfo);
    }

    private void saveProductsToDatabase(List<ProteinProduct> productInfo) {
        productPriceRepository.saveAll(productInfo);
    }

    private ProteinProduct extractDataFromElement(String url, Element prod) {
            String productNameSelector = prod.select(getProductNameSelectors()).text();
            String productPriceSelector = prod.select(getProductPriceSelectors()).text();
            String productOnSaleSelector = prod.select(getProductOnSaleSelectors()).text();
            String productUrlSelector = url.concat(prod.select(getProductUrlSelectors()).attr("href"));
            return setProteinProduct(productNameSelector, productPriceSelector, productUrlSelector, productOnSaleSelector);
    }

    private ProteinProduct setProteinProduct(String name, String price, String website, String sale) {
        ProteinProduct proteinProduct = new ProteinProduct();
        proteinProduct.setProductName(name);
        proteinProduct.setWebsite(website);
        if (price.isEmpty()) {
            proteinProduct.setPrice(sale);
        } else {
            proteinProduct.setPrice(price);
        }
        proteinProduct.setScrapedAt(LocalDateTime.now());
        return proteinProduct;
    }

}

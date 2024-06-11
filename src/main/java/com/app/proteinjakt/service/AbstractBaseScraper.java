package com.app.proteinjakt.service;

import com.app.proteinjakt.dto.ProteinProduct;
import com.app.proteinjakt.repository.ProductPriceRepository;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
            productInfo.addAll(extractDataFromElement(url, prod));
        }
        saveProductsToDatabase(productInfo);
    }

    private void saveProductsToDatabase(List<ProteinProduct> productInfo) {
        productPriceRepository.saveAll(productInfo);
    }


    private List<ProteinProduct> extractDataFromElement(String url, Element prod) {
        List<String> productNameSelectors = prod.select(getProductNameSelectors()).eachText();
        List<String> productPriceSelectors = prod.select(getProductPriceSelectors()).eachText();
        List<String> productOnSaleSelectors = prod.select(getProductOnSaleSelectors()).eachText();
        List<String> productUrlSelectors = prod.select(getProductUrlSelectors()).eachAttr("href");

        List<ProteinProduct> proteinProducts = new ArrayList<>();
        //We use the .size() method on productname since every
        // productname must have a corresponding price and url
        for (int i = 0; i < productNameSelectors.size(); i++) {
            String productName = productNameSelectors.get(i);
            String productPrice = (i < productPriceSelectors.size()) ? productPriceSelectors.get(i) : "";
            String productOnSale = (i < productOnSaleSelectors.size()) ? productOnSaleSelectors.get(i) : "";
            String productUrl = (i < productUrlSelectors.size()) ? url.concat(productUrlSelectors.get(i)) : "";

            proteinProducts.add(setProteinProduct(productName, productPrice, productUrl, productOnSale));
        }
        return proteinProducts;
    }

     private ProteinProduct setProteinProduct(String name, String price, String website, String sale) {
        ProteinProduct proteinProduct = new ProteinProduct();
        proteinProduct.setProductName(name);
        proteinProduct.setWebsite(website);
        proteinProduct.setPrice(price.isEmpty() ? sale : price);
        proteinProduct.setScrapedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return proteinProduct;
    }
}

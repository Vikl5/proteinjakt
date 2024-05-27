package com.app.proteinjakt.service;

import com.app.proteinjakt.dto.ProductPrice;
import com.app.proteinjakt.repository.ProductPriceRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScrapingService {

    private final ProductPriceRepository productPriceRepository;

    @Autowired
    public ScrapingService(ProductPriceRepository productPriceRepository) {
        this.productPriceRepository = productPriceRepository;
    }

    public void scrapeAndSavePrices(){
        List<ProductPrice> prices = new ArrayList<>();
        String baseUrl = "https://www.gymgrossisten.no/kosttilskudd/proteinpulver";

        try {
            Document document = Jsoup.connect(baseUrl).get();
            Elements products = document.getElementsByClass("product-tile");

            for (Element product : products) {
                String productName = product.getElementsByClass("product-tile-name").text();
                String stringProductPrice = product.getElementsByClass("price-sales").text();
                String stringProductPriceOnSale = product.getElementsByClass("price-adjusted").text();
                String productUrl = baseUrl.concat(product.select("a").attr("href"));
//                if (stringProductPrice.contains("kr")){
//                    String noKrInPrice = stringProductPrice.replace("kr", "");
//                }
//                String price =  BigDecimal.valueOf(Long.parseLong(noKrInPrice));

                ProductPrice productPrice = new ProductPrice();
                productPrice.setProductName(productName);
                productPrice.setWebsite(productUrl);
                if (stringProductPrice.isEmpty()){
                    productPrice.setPrice(stringProductPriceOnSale);
                } else {
                    productPrice.setPrice(stringProductPrice);
                }
                productPrice.setScrapedAt(LocalDateTime.now());
                prices.add(productPrice);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productPriceRepository.saveAll(prices);
    }
}

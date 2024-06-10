package com.app.proteinjakt.controller;

import com.app.proteinjakt.dto.ProteinProduct;
import com.app.proteinjakt.repository.ProductPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {
    private final ProductPriceRepository productPriceRepository;

    @Autowired
    public ProductController(ProductPriceRepository productPriceRepository) {
        this.productPriceRepository = productPriceRepository;
    }


    @GetMapping("/priser")
    public List<String> getAllPrices(){
        List<String> stringProductName = new ArrayList<>();
        for (ProteinProduct items : productPriceRepository.findAll()) {
            stringProductName.add(items.getProductName());
        }
        return stringProductName;
    }

}

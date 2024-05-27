package com.app.proteinjakt.controller;

import com.app.proteinjakt.dto.ProductPrice;
import com.app.proteinjakt.repository.ProductPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class ProductController {

    private final ProductPriceRepository productPriceRepository;

    @Autowired
    public ProductController(ProductPriceRepository productPriceRepository) {
        this.productPriceRepository = productPriceRepository;
    }

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/priser")
    public List<ProductPrice> getAllPrices(){
        return productPriceRepository.findAll();
    }
}

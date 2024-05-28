package com.app.proteinjakt.service;

import org.jsoup.select.Elements;

public interface IScraper {

    void scrape(String url, Elements products);
}

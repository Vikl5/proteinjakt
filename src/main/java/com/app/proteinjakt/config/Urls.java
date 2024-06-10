package com.app.proteinjakt.config;

import lombok.Getter;

import java.util.HashMap;


@Getter
public enum Urls {
    GYM_GROSSISTEN("https://www.gymgrossisten.no/kosttilskudd/proteinpulver", "product-tile"),
    BODY_LAB("https://www.bodylab.no/shop/proteinpulver-84s1.html", "container small");

    private final String url;
    private final String htmlTag;
    private static final HashMap<Urls, String> productMap = new HashMap<>();

    Urls(String url, String htmlTag) {
        this.url = url;
        this.htmlTag = htmlTag;
    }

    static {
        for (Urls urlEnum : Urls.values()) {
            productMap.put(urlEnum, urlEnum.htmlTag);
        }
    }



}

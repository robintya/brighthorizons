package com.edu.config;

public enum Urls {

    BASE_URL("https://www.brighthorizons.com/");
  
    private final String url;

    Urls(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}

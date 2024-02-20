package me.alextodea.webcrawler.model;

import lombok.Data;

import java.net.URL;

@Data
public class ProductMatch {
    private String name;
    private URL emagUrl;
    private URL imageUrl;

    public ProductMatch(Product product) {
        this.name = product.getName();
        this.emagUrl = product.getEmagUrl();
        this.imageUrl = product.getImageUrl();
    }
}

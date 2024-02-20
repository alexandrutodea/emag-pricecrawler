package me.alextodea.webcrawler.model;

import lombok.Data;
import me.alextodea.webcrawler.utils.Utils;
import org.jsoup.nodes.Document;

import javax.persistence.*;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Data
public class Product {
    @Id
    @SequenceGenerator(name = "product_gen", sequenceName = "product_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_gen")
    private Long id;
    @Column(unique = true)
    private String name;
    @Column(length = 510)
    private URL emagUrl;
    @Column(length = 510)
    private URL imageUrl;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PriceRecord> priceHistory;

    public Product() {

    }

    public Product(String name, URL emagUrl, URL imageUrl) {
        this.name = name;
        this.emagUrl = emagUrl;
        this.imageUrl = imageUrl;
        this.priceHistory = new ArrayList<>();
    }

    public static Product buildFromHtml(String url, Document document) throws MalformedURLException {
        double price = Utils.extractPrice(document);
        Product product;
        URL emagURL = new URL(url);
        URL imageURL = Utils.extractImageUrl(document);
        String productName = Utils.extractProductName(document);
        product = new Product(productName, emagURL, imageURL);
        product.addPriceRecord(new PriceRecord(price));
        return product;
    }

    public void addPriceRecord(PriceRecord priceRecord) {
        priceRecord.setProduct(this);
        this.priceHistory.add(priceRecord);
    }
}

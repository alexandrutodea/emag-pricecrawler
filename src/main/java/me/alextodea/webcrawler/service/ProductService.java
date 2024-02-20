package me.alextodea.webcrawler.service;

import me.alextodea.webcrawler.dto.PricePredictionDto;
import me.alextodea.webcrawler.dto.ProductDto;
import me.alextodea.webcrawler.exception.FailedToRetrieveSearchResultsException;
import me.alextodea.webcrawler.exception.InvalidProductUrlException;
import me.alextodea.webcrawler.exception.ProductNotFoundException;
import me.alextodea.webcrawler.linear.regression.LinearRegression;
import me.alextodea.webcrawler.model.PricePrediction;
import me.alextodea.webcrawler.model.PriceRecord;
import me.alextodea.webcrawler.model.Product;
import me.alextodea.webcrawler.model.ProductMatch;
import me.alextodea.webcrawler.repository.ProductRepository;
import me.alextodea.webcrawler.utils.Utils;
import org.aspectj.weaver.ast.Not;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(ProductDto productDto) {
        try {

            Document document = Jsoup.connect(productDto.getEmagUrl()).get();
            String productName = Utils.extractProductName(document);
            Optional<Product> res = productRepository.findByName(productName);
            Product product;
            if (res.isPresent()) {
                product = res.get();
                product.addPriceRecord(new PriceRecord(Utils.extractPrice(document)));
            } else {
                product = Product.buildFromHtml(productDto.getEmagUrl(), document);
            }
            productRepository.save(product);
            return product;

        } catch (IOException e) {
            throw new InvalidProductUrlException();
        }

    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> searchProduct(URL url) {
        return productRepository.findByEmagUrl(url);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<ProductMatch> getPotentialMatches(String productName) {

        List<ProductMatch> result = new ArrayList<>();
        var searchTerm = URLEncoder.encode(productName, StandardCharsets.UTF_8);
        Elements elementsByClass;

        try {
            Document document = Jsoup.connect(String.format("https://www.emag.ro/search/%s?ref=effective_search", searchTerm)).get();
            elementsByClass = document.getElementsByClass("card-item card-standard js-product-data");
        } catch (IOException e) {
            throw new InvalidProductUrlException();
        }

        int count = 0;
        for (Element element : elementsByClass) {
            if (count == 5) {
                break;
            }
            appendSearchResults(element, result);
            count++;
        }

        return result;

    }

    public void appendSearchResults(Element element, List<ProductMatch> result) {

        Elements elementsByTag = element.getElementsByTag("a");
        for (Element anchorElement : elementsByTag) {
            String url = anchorElement.attr("href");
            try {
                Document urlDocument = Jsoup.connect(url).get();
                result.add(new ProductMatch(Product.buildFromHtml(url, urlDocument)));
                break;
            } catch (Exception e) {
                throw new FailedToRetrieveSearchResultsException();
            }
        }

    }

    public PricePrediction predictPrice(Long id, PricePredictionDto pricePredictionDto) {

        Optional<Product> retrievedProduct = productRepository.findById(id);

        if (retrievedProduct.isEmpty()) {
            throw new ProductNotFoundException();
        }

        if (retrievedProduct.get().getPriceHistory().size() < 2) {
            return new PricePrediction(retrievedProduct.get().getPriceHistory().get(0).getPrice());
        }

        List<PriceRecord> priceRecords = retrievedProduct.get().getPriceHistory();
        List<Double> dateNumericalRepresentations = new ArrayList<>();
        List<Double> prices = new ArrayList<>();

        for (PriceRecord priceRecord : priceRecords) {
            dateNumericalRepresentations.add(Utils.getDateNumericalRepresentation(priceRecord.getDateTime()));
            prices.add(priceRecord.getPrice());
        }

        LinearRegression linearRegression = new LinearRegression(
                dateNumericalRepresentations,
                prices);

        double dateTimeNumerical = Utils.getDateNumericalRepresentation(pricePredictionDto.getDateTime());
        double predictedPrice = linearRegression.predict(dateTimeNumerical);

        return new PricePrediction(Utils.getTwoDecimalPlacesDouble(predictedPrice));

    }

    @PostConstruct
    public void postConstruct() throws MalformedURLException {
        Product product = new Product("Telefon mobil Samsung Galaxy S22, Dual SIM, 128GB, 8GB RAM, 5G, Phantom Black ",
                new URL("https://www.emag.ro/telefon-mobil-samsung-galaxy-s22-dual-sim-128gb-8gb-ram-5g-phantom-black-sm-s901bzkdeue/pd/DTZ01FMBM/?X-Search-Id=abc59d0cf1775b6a362f&X-Product-Id=9265653&X-Search-Page=1&X-Search-Position=0&X-Section=search&X-MB=0&X-Search-Action=view"),
                new URL("https://s13emagst.akamaized.net/products/43178/43177458/images/res_707ec7b89553b7311534089b62e447e9.jpg"));

        product.addPriceRecord(new PriceRecord(3456, LocalDateTime.of(2022, 12, 3, 15, 15)));
        product.addPriceRecord(new PriceRecord(3566, LocalDateTime.of(2022, 11, 2, 16, 23)));
        product.addPriceRecord(new PriceRecord(3545, LocalDateTime.of(2022, 10, 1, 16, 20)));
        product.addPriceRecord(new PriceRecord(3454, LocalDateTime.of(2022, 9, 2, 17, 12)));

        this.productRepository.save(product);
    }
}

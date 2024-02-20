package me.alextodea.webcrawler.controller;

import me.alextodea.webcrawler.dto.PotentialMatchesDto;
import me.alextodea.webcrawler.dto.PricePredictionDto;
import me.alextodea.webcrawler.dto.ProductDto;
import me.alextodea.webcrawler.dto.ProductSearchDto;
import me.alextodea.webcrawler.exception.ProductNotFoundException;
import me.alextodea.webcrawler.model.PricePrediction;
import me.alextodea.webcrawler.model.Product;
import me.alextodea.webcrawler.model.ProductMatch;
import me.alextodea.webcrawler.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@RestController
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public Product saveProduct(@RequestBody @Valid ProductDto productDto) {
        return productService.saveProduct(productDto);
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @PostMapping("/products/search")
    public Product getProduct(@RequestBody @Valid  ProductSearchDto productSearchDto) throws MalformedURLException {
        return productService.searchProduct(new URL(productSearchDto.getEmagUrl())).orElseThrow(ProductNotFoundException::new);
    }

    @PostMapping("/products/matches")
    public List<ProductMatch> getPotentialMatches(@RequestBody @Valid PotentialMatchesDto potentialMatchesDto) {
        return productService.getPotentialMatches(potentialMatchesDto.getProductName());
    }

    @GetMapping("/products/{id}")
    public Product getProducts(@PathVariable("id") Long id) {
        return productService.getProductById(id).orElseThrow(ProductNotFoundException::new);
    }

    @PostMapping("/products/predict_price/{id}")
    public PricePrediction predictPrice(@PathVariable("id") Long id, @RequestBody PricePredictionDto pricePredictionDto) {
        return productService.predictPrice(id, pricePredictionDto);
    }

}

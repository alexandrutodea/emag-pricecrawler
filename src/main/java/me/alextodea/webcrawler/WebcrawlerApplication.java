package me.alextodea.webcrawler;

import me.alextodea.webcrawler.model.PriceRecord;
import me.alextodea.webcrawler.model.Product;
import me.alextodea.webcrawler.model.ProductMatch;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.MalformedURLException;
import java.net.URL;

@SpringBootApplication
public class WebcrawlerApplication {

	public static void main(String[] args) throws MalformedURLException {
		SpringApplication.run(WebcrawlerApplication.class, args);
	}

}

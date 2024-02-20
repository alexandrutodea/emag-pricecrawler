package me.alextodea.webcrawler.repository;

import me.alextodea.webcrawler.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.net.URL;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public Optional<Product> findByName(String name);
    public Optional<Product> findByEmagUrl(URL emagUrl);
}

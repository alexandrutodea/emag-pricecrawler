package me.alextodea.webcrawler.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@Entity
public class PriceRecord {
    @Id
    @SequenceGenerator(name = "price_record_gen", sequenceName = "price_record_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "price_record_gen")
    private Long id;
    private double price;
    private LocalDateTime dateTime;
    @ManyToOne
    @JsonIgnore
    private Product product;

    public PriceRecord(double price) {
        this.price = price;
        this.dateTime = LocalDateTime.now();
    }

    public PriceRecord(double price, LocalDateTime dateTime) {
        this.price = price;
        this.dateTime = dateTime;
    }

    public PriceRecord() {
    }
}


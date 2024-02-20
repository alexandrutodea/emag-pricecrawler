package me.alextodea.webcrawler.model;

import lombok.Data;

@Data
public class PricePrediction {
    private double predictedPrice;

    public PricePrediction(double predictedPrice) {
        this.predictedPrice = predictedPrice;
    }
}

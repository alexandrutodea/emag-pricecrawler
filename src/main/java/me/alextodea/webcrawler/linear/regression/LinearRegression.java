package me.alextodea.webcrawler.linear.regression;

import java.util.List;

public class LinearRegression {

    private double intercept, slope;

    public LinearRegression(List<Double> x, List<Double> y) {

        if (x.size() != y.size()) {
            throw new IllegalArgumentException("Array lengths are not equal");
        }

        int n = x.size();

        double sumx = 0.0, sumy = 0.0, sumxy =0.0, sumx2 = 0.0;

        for (int i = 0; i < n; i++) {
            sumx += x.get(i);
            sumy += y.get(i);
            sumxy += x.get(i) * y.get(i);
            sumx2 += x.get(i) * x.get(i);
        }

        this.slope = (n * sumxy - sumx * sumy)/(n * sumx2 - sumx * sumx);
        this.intercept = (sumy - this.slope * sumx) / n;

    }

    public double predict(double x) {
        return slope * x + intercept;
    }

}

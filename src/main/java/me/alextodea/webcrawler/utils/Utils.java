package me.alextodea.webcrawler.utils;

import me.alextodea.webcrawler.exception.FailedToFetchPriceException;
import me.alextodea.webcrawler.exception.FailedToParseImageException;
import me.alextodea.webcrawler.exception.InvalidTitleException;
import me.alextodea.webcrawler.model.PriceRecord;
import org.hibernate.type.LocalDateTimeType;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Optional;

public class Utils {
    public static String extractProductName(Document document) {
        String title = document.title();
        String[] parts = title.split("-");
        if (parts.length < 2) {
            throw new InvalidTitleException();
        }
        return parts[0];
    }

    public static double extractPrice(Document document) {
        Elements result = document.getElementsByClass("product-new-price");
        for (Element element : result) {
            try {
                String priceString = element.text().split(" ")[0];
                String firstResult = priceString.replace(".", "");
                String secondResult = firstResult.replace(",", ".");
                double doubleResult = Double.parseDouble(secondResult);
                return doubleResult;
            } catch (Exception e) {
                throw new FailedToFetchPriceException();
            }
        }
        throw new FailedToFetchPriceException();
    }

    public static URL extractImageUrl(Document document) {
        Elements imgElements = document.getElementsByTag("img");

        for (Element element : imgElements) {
            try {
                if (element.attr("alt").equals("Product image")) {
                    String src = element.attr("src");
                    String imageUrl = src.split("\\?")[0];
                    return new URL(imageUrl);
                }
            } catch (Exception e) {
                throw new FailedToParseImageException();
            }
        }

        throw new FailedToParseImageException();
    }

    public static double getDateNumericalRepresentation(LocalDateTime dateTime) {
        int day = dateTime.getDayOfMonth();
        int month = dateTime.getMonthValue();
        int seconds = dateTime.getSecond();
        int minutes = dateTime.getMinute();
        int hours = dateTime.getHour();
        return month * 10000 + day * 1000 + hours * 100 + minutes * 10 + seconds;
    }

    public static double getTwoDecimalPlacesDouble(double num) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.FLOOR);
        return Double.parseDouble(df.format(num));
    }

}

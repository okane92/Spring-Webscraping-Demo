package com.demo.webscrapingdemo.service;

import com.demo.webscrapingdemo.model.GroceryItem;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TescoService {

    @SneakyThrows
    public List<GroceryItem> getSpiritOffers(String alcoholType) {
        Document doc = Jsoup.connect(String.format("https://www.tesco.com/groceries/en-GB/shop/drinks/spirits/%s?promotion=offers&viewAll=promotion", alcoholType)).get();
        return getGroceryItems(doc);
    }

    @SneakyThrows
    public List<GroceryItem> getMatchedResults(String item) {
        Document doc = Jsoup.connect(String.format("https://www.tesco.com/groceries/en-GB/search?query=%s", item)).get();
        return getGroceryItems(doc);
    }

    @SneakyThrows
    public List<GroceryItem> getMatchedResults(String item, String category) {
        Document doc = Jsoup.connect(String.format("https://www.tesco.com/groceries/en-GB/search?query=%s&department=%s&viewAll=department", item, category)).get();
        return getGroceryItems(doc);
    }

    private List<GroceryItem> getGroceryItems(Document doc) {
        Elements groceryItems = doc.getElementsByClass("tile-content");

        return groceryItems.stream()
                .map(this::getGroceryItem)
                .collect(Collectors.toList());
    }

    private GroceryItem getGroceryItem(Element groceryItem) {
        String ginName = groceryItem.getElementsByClass("product-details--content")
                .eachText()
                .stream()
                .findFirst()
                .orElse(null);

        BigDecimal price = new BigDecimal(groceryItem.getElementsByClass("price-control-wrapper").text().substring(2));

        String offerText = groceryItem.getElementsByClass("offer-text").text();
        String offerDates = groceryItem.getElementsByClass("dates").text();

        return GroceryItem.builder()
                .name(ginName)
                .price(price)
                .offerDetails(StringUtils.isEmpty(offerText) ? "N/A" : offerText)
                .offerDates(StringUtils.isEmpty(offerDates) ? "N/A" : offerDates)
                .build();
    }
}

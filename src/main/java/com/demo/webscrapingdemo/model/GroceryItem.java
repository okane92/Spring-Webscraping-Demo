package com.demo.webscrapingdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroceryItem {
    private String name;
    private BigDecimal price;
    private String offerDetails;
    private String offerDates;
}

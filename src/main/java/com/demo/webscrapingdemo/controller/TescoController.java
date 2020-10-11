package com.demo.webscrapingdemo.controller;

import com.demo.webscrapingdemo.model.GroceryItem;
import com.demo.webscrapingdemo.service.TescoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/tesco")
public class TescoController {

    private final TescoService tescoService;

    public TescoController(TescoService tescoService) {
        this.tescoService = tescoService;
    }

    @GetMapping(path = "/offers/{type}")
    public List<GroceryItem> getOffers(@PathVariable(value = "type") String type) {
        return tescoService.getSpiritOffers(type);
    }

    @GetMapping(path = "/price/{item}")
    public List<GroceryItem> getSearchedItems(@PathVariable(value = "item") String item, @RequestParam(required = false) String category) {
        return category != null ? tescoService.getMatchedResults(item, category) : tescoService.getMatchedResults(item);
    }
}

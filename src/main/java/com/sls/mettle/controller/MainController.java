package com.sls.mettle.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.sls.mettle.model.Item;
import com.sls.mettle.model.Views;
import com.sls.mettle.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    private final ItemsService itemsService;

    @Autowired
    public MainController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @GetMapping("/items")
    public List<Item> getItems() {
        return itemsService.getAllItems();
    }

    @PostMapping("/item")
    public Item addItem(@JsonView(Views.Public.class) @RequestBody Item item) {
        return itemsService.addItem(item);
    }

    @PutMapping("/item")
    public Item editItem(@JsonView(Views.Public.class) @RequestBody Item item) {
        return itemsService.updateItem(item);
    }

    @DeleteMapping("/item/{uuid}")
    public Item deleteItem(@PathVariable String uuid) {
        return itemsService.deleteItem(uuid);
    }

    @GetMapping("/item/{uuid}")
    public Item getItem(@PathVariable String uuid) {
        return itemsService.getItem(uuid);
    }
}

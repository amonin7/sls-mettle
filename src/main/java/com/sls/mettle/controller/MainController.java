package com.sls.mettle.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.sls.mettle.model.Item;
import com.sls.mettle.model.Views;
import com.sls.mettle.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;

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
    public ResponseEntity<Object> addItem(@JsonView(Views.Public.class) @RequestBody Item item) {
        return processRequest(item, itemsService::addItem);
    }

    @PutMapping("/item")
    public ResponseEntity<Object> updateItem(@JsonView(Views.Public.class) @RequestBody Item item) {
        return processRequest(item, itemsService::updateItem);
    }

    @DeleteMapping("/item/{uuid}")
    public ResponseEntity<Object> deleteItem(@PathVariable String uuid) {
        return processRequest(uuid, itemsService::deleteItem);
    }

    @GetMapping("/item/{uuid}")
    public ResponseEntity<Object> getItem(@PathVariable String uuid) {
        return processRequest(uuid, itemsService::getItem);
    }

    private <T> ResponseEntity<Object> processRequest(T input, Function<T, Item> inputMap) {
        try {
            return ResponseEntity.ok()
                    .body(inputMap.apply(input));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}

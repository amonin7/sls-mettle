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

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.List;
import java.util.NoSuchElementException;

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
    public ResponseEntity<Item> addItem(@JsonView(Views.Public.class) @RequestBody Item item) {
        try {
            return ResponseEntity.ok()
                    .body(itemsService.addItem(item));
        } catch (KeyAlreadyExistsException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/item")
    public ResponseEntity<Item> editItem(@JsonView(Views.Public.class) @RequestBody Item item) {
        try {
            return ResponseEntity.ok()
                    .body(itemsService.updateItem(item));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/item/{uuid}")
    public ResponseEntity<Item> deleteItem(@PathVariable String uuid) {
        try {
            return ResponseEntity.ok()
                    .body(itemsService.deleteItem(uuid));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/item/{uuid}")
    public ResponseEntity<Item> getItem(@PathVariable String uuid) {
        try {
            return ResponseEntity.ok()
                    .body(itemsService.getItem(uuid));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}

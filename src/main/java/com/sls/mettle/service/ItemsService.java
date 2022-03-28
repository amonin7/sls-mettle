package com.sls.mettle.service;

import com.sls.mettle.model.Item;

import java.util.List;

public interface ItemsService {
    List<Item> getAllItems();
    List<Item> getItemsWithFiltering(String name, String description);
    Item addItem(Item item);
    Item updateItem(Item item);
    Item deleteItem(String uuid);
    Item getItem(String uuid);
    boolean isValid(Item item);
}

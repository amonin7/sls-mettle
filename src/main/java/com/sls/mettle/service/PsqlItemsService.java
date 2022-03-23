package com.sls.mettle.service;

import com.sls.mettle.model.Item;
import com.sls.mettle.repository.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class PsqlItemsService implements ItemsService {

    private final ItemsRepository itemsRepository;

    @Autowired
    public PsqlItemsService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    @Override
    public List<Item> getAllItems() {
        return itemsRepository.findAllByDeletedAtIsNull();
    }

    @Override
    public Item addItem(Item item) {
        Optional<Item> optionalItem = this.itemsRepository.findById(item.getId());
        if (optionalItem.isEmpty()) {
            throw new KeyAlreadyExistsException("The item with such ID already exists.");
        }
        item.setUpdatedAt(Timestamp.from(Instant.now()));
        item.setCreatedAt(Timestamp.from(Instant.now()));
        return itemsRepository.save(item);
    }

    @Override
    public Item updateItem(Item item) {
        Optional<Item> optionalItem = this.itemsRepository.findById(item.getId());
        if (optionalItem.isEmpty()) {
            throw new NoSuchElementException("There is no item with such ID");
        }
        Item oldItem = optionalItem.get();
        oldItem.setName(item.getName());
        oldItem.setDescription(item.getDescription());
        oldItem.setCost(item.getCost());
        oldItem.setType(item.getType().toString());
        oldItem.setUpdatedAt(Timestamp.from(Instant.now()));
        this.itemsRepository.save(oldItem);
        return oldItem;
    }

    @Override
    public Item deleteItem(String uuid) {
        Optional<Item> optionalItem = this.itemsRepository.findById(UUID.fromString(uuid));
        if (optionalItem.isEmpty()) {
            throw new NoSuchElementException("There is no item with such ID");
        }
        Item oldItem = optionalItem.get();
        // it should be added here, but then I could not understand, why do we need a deleted_at property...
//        this.itemsRepository.deleteById(oldItem.getId());
        oldItem.setDeletedAt(Timestamp.from(Instant.now()));
        this.itemsRepository.save(oldItem);
        return oldItem;
    }
}

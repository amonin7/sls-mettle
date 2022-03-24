package com.sls.mettle.service;

import com.sls.mettle.model.Item;
import com.sls.mettle.repository.ItemsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger log = LogManager.getLogger(ItemsService.class);
    private static final String ELEMENT_ALREADY_EXISTS = "The item with such ID already exists.";
    private static final String NO_ITEM_WITH_ID = "There is no item with such ID";

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
        tryFindElementById(item.getId(), false);
        item.setUpdatedAt(Timestamp.from(Instant.now()));
        item.setCreatedAt(Timestamp.from(Instant.now()));
        Item save = itemsRepository.save(item);
        log.info("Successfully saved item into database");
        return save;
    }

    @Override
    public Item updateItem(Item item) {
        Item oldItem = tryFindElementById(item.getId(), true);
        oldItem.setName(item.getName());
        oldItem.setDescription(item.getDescription());
        oldItem.setCost(item.getCost());
        oldItem.setType(item.getType().toString());
        oldItem.setUpdatedAt(Timestamp.from(Instant.now()));
        this.itemsRepository.save(oldItem);
        log.info("Successfully updated item");
        return oldItem;
    }

    @Override
    public Item deleteItem(String uuid) {
        Item oldItem = tryFindElementById(uuid, true);
        // it should be added here, but then I could not understand, why do we need a deleted_at property...
//        this.itemsRepository.deleteById(oldItem.getId());
        oldItem.setDeletedAt(Timestamp.from(Instant.now()));
        this.itemsRepository.save(oldItem);
        log.info("Successfully deleted item");
        return oldItem;
    }

    @Override
    public Item getItem(String uuid) {
        return tryFindElementById(uuid, true);
    }

    private Item tryFindElementById(String uuid, boolean isNeededToExist) {
        return tryFindElementById(UUID.fromString(uuid), isNeededToExist);
    }

    private Item tryFindElementById(UUID uuid, boolean isNeededToExist) {
        Optional<Item> optionalItem = this.itemsRepository.findById(uuid);
        if (optionalItem.isPresent() ^ isNeededToExist) {
            if (isNeededToExist) {
                log.error(NO_ITEM_WITH_ID);
                throw new NoSuchElementException(NO_ITEM_WITH_ID);
            } else {
                log.error(ELEMENT_ALREADY_EXISTS);
                throw new KeyAlreadyExistsException(ELEMENT_ALREADY_EXISTS);
            }
        }
        return optionalItem.orElse(null);
    }
}

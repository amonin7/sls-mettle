package com.sls.mettle.service;

import com.sls.mettle.exception.ElementAlreadyExistsException;
import com.sls.mettle.exception.InvalidItemException;
import com.sls.mettle.model.Item;
import com.sls.mettle.repository.ItemsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class PsqlItemsService implements ItemsService {

    private static final Logger log = LogManager.getLogger(ItemsService.class);

    private static final String COMMON_DESCR = "The item with ID=";
    private static final String ELEMENT_ALREADY_EXISTS = " already exists";
    private static final String NO_ITEM_WITH_ID = " does not exist";

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
    public List<Item> getItemsWithFiltering(String name, String description) {
        return itemsRepository.findAllByDeletedAtIsNullAndNameContainingAndDescriptionContaining(name, description);
    }

    @Override
    public Item addItem(Item item) {
        isValid(item);
        item.setUpdatedAt(Timestamp.from(Instant.now()));
        item.setCreatedAt(Timestamp.from(Instant.now()));
        Item save = itemsRepository.save(item);
        log.info("Successfully saved item into database");
        return save;
    }

    @Override
    public Item updateItem(Item item) {
        isValid(item);
        Item oldItem = tryFindElementById(item.getId(), true);
        oldItem.setName(item.getName());
        oldItem.setDescription(item.getDescription());
        oldItem.setCost(item.getCost());
        oldItem.setType(item.getType().toString());
        oldItem.setUpdatedAt(Timestamp.from(Instant.now()));
        Item save = this.itemsRepository.save(oldItem);
        log.info("Successfully updated item");
        return save;
    }

    @Override
    public Item deleteItem(String uuid) {
        Item oldItem = tryFindElementById(uuid, true);
        // it should be added here, but then I could not understand, why do we need a deleted_at property...
//        this.itemsRepository.deleteById(oldItem.getId());
        oldItem.setDeletedAt(Timestamp.from(Instant.now()));
        Item save = this.itemsRepository.save(oldItem);
        log.info("Successfully deleted item");
        return save;
    }

    @Override
    public Item getItem(String uuid) {
        return tryFindElementById(uuid, true);
    }

    @Override
    public boolean isValid(Item item) {
        boolean validationResult =
                (item.getName() == null || item.getName().length() <= 20) &&
                (item.getDescription() == null || item.getDescription().length() <= 200) &&
                (item.getCost() == null || item.getCost() >= 0.0) &&
                item.getCreatedAt() == null &&
                item.getUpdatedAt() == null &&
                item.getDeletedAt() == null;

        if (!validationResult) {
            log.error("Provided item is not valid");
            throw new InvalidItemException("Provided item is not valid");
        }
        return true;
    }

    private Item tryFindElementById(String uuid, boolean isNeededToExist) {
        try {
            return tryFindElementById(UUID.fromString(uuid), isNeededToExist);
        } catch (IllegalArgumentException ex) {
            log.error("Could not parse ID=" + uuid + " as UUID");
            throw ex;
        }
    }

    private Item tryFindElementById(UUID uuid, boolean isNeededToExist) {
        Optional<Item> optionalItem = this.itemsRepository.findById(uuid);
        if (optionalItem.isPresent() ^ isNeededToExist) {
            String message = constructExceptionDescription(uuid, isNeededToExist);
            log.error(message);
            if (isNeededToExist) {
                throw new NoSuchElementException(message);
            } else {
                throw new ElementAlreadyExistsException(message);
            }
        }
        return optionalItem.orElse(null);
    }

    private String constructExceptionDescription(UUID uuid, boolean isNeededToExist) {
        return COMMON_DESCR + uuid +
                (isNeededToExist ? NO_ITEM_WITH_ID : ELEMENT_ALREADY_EXISTS);
    }
}

package com.sls.mettle.repository;

import com.sls.mettle.model.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ItemsRepository extends CrudRepository<Item, UUID> {
    List<Item> findAllByDeletedAtIsNull();
    List<Item> findAllByDeletedAtIsNullAndNameContainingAndDescriptionContaining(String name, String description);
    void deleteById(UUID uuid);
}

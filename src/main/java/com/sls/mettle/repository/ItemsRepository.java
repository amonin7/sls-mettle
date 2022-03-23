package com.sls.mettle.repository;

import com.sls.mettle.model.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ItemsRepository extends CrudRepository<Item, UUID> {
    List<Item> findAllByDeletedAtIsNull();
    void deleteById(UUID uuid);
}

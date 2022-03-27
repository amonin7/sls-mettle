package com.sls.mettle.service;

import com.sls.mettle.exception.ElementAlreadyExistsException;
import com.sls.mettle.exception.InvalidItemException;
import com.sls.mettle.model.Item;
import com.sls.mettle.repository.ItemsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static com.sls.mettle.testutils.TestData.I1;
import static com.sls.mettle.testutils.TestData.I1_DELETED;
import static com.sls.mettle.testutils.TestData.I1_SAVED;
import static com.sls.mettle.testutils.TestData.I1_UPD;
import static com.sls.mettle.testutils.TestData.I1_UPDATED;
import static com.sls.mettle.testutils.TestData.I1_UUID;
import static com.sls.mettle.testutils.TestData.I2;
import static com.sls.mettle.testutils.TestData.I2_SAVED;
import static com.sls.mettle.testutils.TestData.I2_WITH_ID;
import static com.sls.mettle.testutils.TestData.ITEMS;
import static com.sls.mettle.testutils.TestData.I_INVALID;
import static com.sls.mettle.testutils.TestData.NOW;
import static com.sls.mettle.testutils.TestData.generateRandomLongString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.clearInvocations;

@ExtendWith(SpringExtension.class)
class PsqlItemsServiceTest {

    @MockBean
    private ItemsRepository itemsRepository;

    private PsqlItemsService itemsService;

    @BeforeEach
    public void init() {
        itemsService = new PsqlItemsService(itemsRepository);
        Mockito.when(itemsRepository.findAll()).thenReturn(ITEMS);
        Mockito.when(itemsRepository.findAllByDeletedAtIsNull()).thenReturn(ITEMS);
        Mockito.when(itemsRepository.save(I1)).thenReturn(I1_SAVED);
    }

    @Test
    public void itemFoundByCorrectId() {
        Mockito.when(itemsRepository.findById(I1_SAVED.getId())).thenReturn(Optional.of(I1_SAVED));
        Item item = itemsService.getItem(I1_UUID);
        assertThat(item.getName()).isEqualTo(I1_SAVED.getName());
    }

    @Test
    public void itemNotFoundByIncorrectId() {
        assertThrows(NoSuchElementException.class,
                ()->itemsService.getItem(UUID.randomUUID().toString()));
    }

    @Test
    public void itemNotFoundByInvalidId() {
        assertThrows(NoSuchElementException.class,
                ()->itemsService.getItem(UUID.randomUUID().toString()));
    }

    @Test
    public void getAllItemsReturnCorrectItems() {
        List<Item> items = itemsService.getAllItems();
        assertThat(items)
                .hasSameSizeAs(ITEMS)
                .hasSameElementsAs(ITEMS);
    }

    @Test
    public void tryAddInvalidItem() {
        assertThrows(InvalidItemException.class,
                ()->itemsService.addItem(I_INVALID));
    }

    @Test
    public void tryAddNormalItem() {
        Item item = itemsService.addItem(I1);
        assertThat(item).isEqualTo(I1_SAVED);
    }

    @Test
    public void tryEditNonExistentItem() {
        Mockito.when(itemsRepository.findById(I2.getId())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                ()->itemsService.updateItem(I2));
    }

    @Test
    public void tryEditNormalItem() {
        Mockito.when(itemsRepository.findById(I1_SAVED.getId())).thenReturn(Optional.of(I1_SAVED));
        Mockito.when(itemsRepository.save(Mockito.any())).thenReturn(I1_UPDATED);
        Item item = itemsService.updateItem(I1_UPD);
        assertThat(item).isEqualTo(I1_UPDATED);
    }

    @Test
    public void tryDeleteNonExistentItem() {
        Mockito.when(itemsRepository.findById(I2_SAVED.getId())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                ()->itemsService.deleteItem(I2_SAVED.getId().toString()));
    }

    @Test
    public void tryDeleteNormalItem() {
        Mockito.when(itemsRepository.findById(I1_SAVED.getId())).thenReturn(Optional.of(I1_SAVED));
        Mockito.when(itemsRepository.save(Mockito.any())).thenReturn(I1_DELETED);
        Item item = itemsService.deleteItem(I1_UUID);
        assertThat(item).isEqualTo(I1_DELETED);
    }

    @Test
    public void itemInvalidityTest() {
        // invalid name
        assertThrows(InvalidItemException.class,
                ()->itemsService.isValid(new Item(UUID.randomUUID(), generateRandomLongString(21),
                        null, null, null, null, null, null)));
        // invalid descr
        assertThrows(InvalidItemException.class,
                ()->itemsService.isValid(new Item(UUID.randomUUID(), "normalName",
                        generateRandomLongString(201),
                        null, null, null, null, null)));
        // invalid cost
        assertThrows(InvalidItemException.class,
                ()->itemsService.isValid(new Item(UUID.randomUUID(), "normalName",
                        generateRandomLongString(20),
                        null, -23.0, null, null, null)));
        // invalid createdAt
        assertThrows(InvalidItemException.class,
                ()->itemsService.isValid(new Item(UUID.randomUUID(), "normalName",
                        generateRandomLongString(20),
                        null, 22.0, NOW, null, null)));
        // invalid updatedAt
        assertThrows(InvalidItemException.class,
                ()->itemsService.isValid(new Item(UUID.randomUUID(), "normalName",
                        generateRandomLongString(20),
                        null, 22.0, null, NOW, null)));
        // invalid deletedAt
        assertThrows(InvalidItemException.class,
                ()->itemsService.isValid(new Item(UUID.randomUUID(), "normalName",
                        generateRandomLongString(20),
                        null, 22.0, null, null, NOW)));
    }

    @AfterEach
    void tearDown() {
        clearInvocations(itemsRepository);
    }
}
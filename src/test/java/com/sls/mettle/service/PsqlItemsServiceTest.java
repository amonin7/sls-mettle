package com.sls.mettle.service;

import com.sls.mettle.model.Item;
import com.sls.mettle.repository.ItemsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static com.sls.mettle.testutils.TestData.I1;
import static com.sls.mettle.testutils.TestData.I1_UUID;
import static com.sls.mettle.testutils.TestData.ITEMS;
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
        Mockito.when(itemsRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(itemsRepository.findById(I1.getId())).thenReturn(Optional.of(I1));
        Mockito.when(itemsRepository.findAll()).thenReturn(ITEMS);
    }

    @Test
    public void itemFoundByCorrectId() {
        Item item = itemsService.getItem(I1_UUID);
        assertThat(item.getName()).isEqualTo(I1.getName());
    }

    @Test
    public void itemFoundByIncorrectId() {
        assertThrows(NoSuchElementException.class,
                ()->itemsService.getItem(UUID.randomUUID().toString()));

    }

    @AfterEach
    void tearDown() {
        clearInvocations(itemsRepository);
    }
}
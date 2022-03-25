package com.sls.mettle.controller;

import com.sls.mettle.model.Item;
import com.sls.mettle.model.ItemType;
import com.sls.mettle.repository.ItemsRepository;
import com.sls.mettle.service.ItemsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(MainController.class)
class MainControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemsService itemsService;

    @Test
    void getItems() throws Exception {
        List<Item> itemsExpected = new ArrayList<>();
        itemsExpected.add(
                new Item(UUID.randomUUID(),
                "item1",
                "descr1",
                ItemType.HOCKEY_PADS,
                12.0,
                null, null, null));
        itemsExpected.add(
                new Item(UUID.randomUUID(),
                "item2",
                "descr2",
                ItemType.HOCKEY_SKATES,
                13.0,
                null, null, null));

        Mockito.when(itemsService.getAllItems()).thenReturn(itemsExpected);
        mockMvc.perform(get("/items")).andExpect(status().isOk());
    }

    @Test
    void addItem() {
    }

    @Test
    void updateItem() {
    }

    @Test
    void deleteItem() {
    }

    @Test
    void getItem() {
    }
}
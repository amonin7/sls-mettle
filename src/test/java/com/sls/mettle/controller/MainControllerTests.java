package com.sls.mettle.controller;

import com.sls.mettle.service.ItemsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.sls.mettle.testutils.TestData.I1_CREATE_JSON;
import static com.sls.mettle.testutils.TestData.I1_DELETED;
import static com.sls.mettle.testutils.TestData.I1_SAVED;
import static com.sls.mettle.testutils.TestData.I1_UPDATED;
import static com.sls.mettle.testutils.TestData.I1_UPDATE_JSON;
import static com.sls.mettle.testutils.TestData.I1_UUID;
import static com.sls.mettle.testutils.TestData.I2_SAVED;
import static com.sls.mettle.testutils.TestData.ITEMS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MainController.class)
class MainControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemsService itemsService;

    @Test
    void getItems() throws Exception {
        Mockito.when(itemsService.getAllItems()).thenReturn(ITEMS);
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(I1_SAVED.getId().toString())))
                .andExpect(jsonPath("$[1].id", is(I2_SAVED.getId().toString())));
        verify(itemsService, VerificationModeFactory.times(1)).getAllItems();
        reset(itemsService);
    }

    @Test
    void addItem() throws Exception {
        Mockito.when(itemsService.addItem(Mockito.any())).thenReturn(I1_SAVED);
        mockMvc.perform(post("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(I1_CREATE_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(I1_SAVED.getId().toString())))
                .andExpect(jsonPath("$.name", is(I1_SAVED.getName())));
        verify(itemsService, VerificationModeFactory.times(1)).addItem(Mockito.any());
        reset(itemsService);
    }

    @Test
    void updateItem() throws Exception {
        Mockito.when(itemsService.updateItem(Mockito.any())).thenReturn(I1_UPDATED);
        mockMvc.perform(patch("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(I1_UPDATE_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(I1_UPDATED.getId().toString())))
                .andExpect(jsonPath("$.name", is(I1_UPDATED.getName())))
                .andExpect(jsonPath("$.description", is(I1_UPDATED.getDescription())))
                .andExpect(jsonPath("$.cost", is(I1_UPDATED.getCost())));
        verify(itemsService, VerificationModeFactory.times(1)).updateItem(Mockito.any());
        reset(itemsService);
    }

    @Test
    void deleteItem() throws Exception {
        Mockito.when(itemsService.deleteItem(Mockito.any())).thenReturn(I1_DELETED);
        mockMvc.perform(delete("/item/" + I1_UUID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(I1_DELETED.getId().toString())))
                .andExpect(jsonPath("$.name", is(I1_DELETED.getName())))
                .andExpect(jsonPath("$.description", is(I1_DELETED.getDescription())))
                .andExpect(jsonPath("$.cost", is(I1_DELETED.getCost())));
        verify(itemsService, VerificationModeFactory.times(1)).deleteItem(Mockito.any());
        reset(itemsService);
    }

    @Test
    void getItem() throws Exception {
        Mockito.when(itemsService.getItem(I1_UUID)).thenReturn(I1_SAVED);
        mockMvc.perform(get("/item/" + I1_UUID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(I1_SAVED.getId().toString())))
                .andExpect(jsonPath("$.name", is(I1_SAVED.getName())))
                .andExpect(jsonPath("$.description", is(I1_SAVED.getDescription())))
                .andExpect(jsonPath("$.cost", is(I1_SAVED.getCost())));
        verify(itemsService, VerificationModeFactory.times(1)).getItem(Mockito.any());
        reset(itemsService);
    }
}
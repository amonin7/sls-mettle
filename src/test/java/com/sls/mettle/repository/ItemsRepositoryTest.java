package com.sls.mettle.repository;

import com.sls.mettle.model.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.sls.mettle.testutils.TestData.FILTER_ITEM1;
import static com.sls.mettle.testutils.TestData.FILTER_ITEM2;
import static com.sls.mettle.testutils.TestData.FILTER_ITEM3;
import static com.sls.mettle.testutils.TestData.FILTER_ITEM4;
import static com.sls.mettle.testutils.TestData.I1;
import static com.sls.mettle.testutils.TestData.I1_DELETED;
import static com.sls.mettle.testutils.TestData.I2;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class ItemsRepositoryTest {

    @Autowired
    private ItemsRepository itemsRepository;

    @Test
    public void saveItemWithoutIdTest() {
        Item save = itemsRepository.save(I1);
        assertThat(save.getId()).isNotNull();
        assertThat(I1.getId()).isNotNull();
    }

    @Test
    public void findByIdTest() {
        Item save = itemsRepository.save(I1);
        Optional<Item> searchResult = itemsRepository.findById(save.getId());
        assertThat(searchResult).isPresent();
        assertThat(searchResult).get().isEqualTo(save);
    }

    @Test
    public void findAllByDeletedAtIsNullTest() {
        Item save1 = itemsRepository.save(I1_DELETED);
        Item save2 = itemsRepository.save(I2);
        List<Item> found = itemsRepository.findAllByDeletedAtIsNull();
        assertThat(found).isNotNull();
        assertThat(found).size().isEqualTo(1);
        assertThat(found).hasSameElementsAs(List.of(save2));
    }

    @Test
    public void filteringTest() {
        Item save1 = itemsRepository.save(FILTER_ITEM1);
        Item save2 = itemsRepository.save(FILTER_ITEM2);
        Item save3 = itemsRepository.save(FILTER_ITEM3);
        Item save4 = itemsRepository.save(FILTER_ITEM4);

        List<Item> found1 = itemsRepository
                .findAllByDeletedAtIsNullAndNameContainingAndDescriptionContaining("", "");
        assertThat(found1).isNotNull();
        assertThat(found1).size().isEqualTo(4);
        assertThat(found1).hasSameSizeAs(List.of(save1, save2, save3, save4));

        List<Item> found2 = itemsRepository
                .findAllByDeletedAtIsNullAndNameContainingAndDescriptionContaining("apple", "");
        assertThat(found2).isNotNull();
        assertThat(found2).size().isEqualTo(2);
        assertThat(found2).hasSameSizeAs(List.of(save1, save2));

        List<Item> found3 = itemsRepository
                .findAllByDeletedAtIsNullAndNameContainingAndDescriptionContaining("", "donna");
        assertThat(found3).isNotNull();
        assertThat(found3).size().isEqualTo(2);
        assertThat(found3).hasSameSizeAs(List.of(save2, save4));

        List<Item> found4 = itemsRepository
                .findAllByDeletedAtIsNullAndNameContainingAndDescriptionContaining("nokia", "bella");
        assertThat(found4).isNotNull();
        assertThat(found4).size().isEqualTo(1);
        assertThat(found4).hasSameSizeAs(List.of(save3));
    }
}
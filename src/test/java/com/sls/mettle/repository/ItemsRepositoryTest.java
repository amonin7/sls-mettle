package com.sls.mettle.repository;

import com.sls.mettle.model.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        assertThat(found).hasSameSizeAs(List.of(save2));
    }
}
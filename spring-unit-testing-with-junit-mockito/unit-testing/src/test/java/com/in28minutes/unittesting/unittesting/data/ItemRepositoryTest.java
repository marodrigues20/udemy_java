package com.in28minutes.unittesting.unittesting.data;

import com.in28minutes.unittesting.unittesting.model.Item;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @DataJpaTest Launch spring context. Start the Database and populate the database
 * Using the data.sql inside of resource folders.
 * If you want to use specic data just for test you can add data.sql only in test
 * folder.
 */
@DataJpaTest
public class ItemRepositoryTest {

    @Autowired
    ItemRepository repository;

    @Test
    public void testFindAll(){

        List<Item> items = repository.findAll();
        assertEquals(3, items.size());

    }

}

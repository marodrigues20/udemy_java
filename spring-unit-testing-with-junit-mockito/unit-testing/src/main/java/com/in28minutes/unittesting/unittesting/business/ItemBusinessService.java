package com.in28minutes.unittesting.unittesting.business;

import com.in28minutes.unittesting.unittesting.data.ItemRepository;
import com.in28minutes.unittesting.unittesting.model.Item;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemBusinessService {

    @Autowired
    private ItemRepository itemRepository;

    public Item retrieveHardCodedItem() {
        return new Item(1, "Ball", 10, 100);
    }


    public List<Item> retrieveAllItems(){

        List<Item>items = itemRepository.findAll();

        for (Item item : items ) {
            item.setValue(item.getPrice() * item.getQuantity());
        }

        return items;
    }


}

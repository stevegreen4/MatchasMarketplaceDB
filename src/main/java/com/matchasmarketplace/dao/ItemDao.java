package com.matchasmarketplace.dao;

import com.matchasmarketplace.model.Item;

import java.util.List;

public interface ItemDao {
    //CRUD
    Item createItem(Item item);

    List<Item> getAllItems();

    Item getItemById(int id);

    Item updateItem(Item item);

    void deleteItem(int id);
}

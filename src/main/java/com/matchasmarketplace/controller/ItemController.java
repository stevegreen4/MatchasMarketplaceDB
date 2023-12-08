package com.matchasmarketplace.controller;

import com.matchasmarketplace.dao.ItemDao;
import com.matchasmarketplace.exception.DaoException;
import com.matchasmarketplace.model.Item;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/item")
public class ItemController {

    private ItemDao itemDao;

    public ItemController(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Item> getAllItems() {
        List<Item> items;
        try {
            items = itemDao.getAllItems();
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return items;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Item getItemById(@PathVariable int id) {
        Item item = null;
        try {
            item = itemDao.getItemById(id);
            if (item == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return item;
    }
}

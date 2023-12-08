package com.matchasmarketplace.dao;

import com.matchasmarketplace.exception.DaoException;
import com.matchasmarketplace.model.Item;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcItemDao implements ItemDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcItemDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Item createItem(Item item) {
        Item newItem = null;
        String sql = "INSERT INTO item (name, description, size, price) VALUES " +
                "(?, ?, ?, ?) RETURNING item_id;";
        try {
            int newItemId = jdbcTemplate.queryForObject(sql, int.class, item.getName(),
                    item.getDescription(), item.getImgUrl(), item.getPrice(), item.getItemId());
            newItem = getItemById(newItemId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newItem;
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<>();
        String sql = "SELECT * FROM item;";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
            while (result.next()) {
                itemList.add(mapRowToItem(result));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server", e);
        }
        return itemList;
    }

    @Override
    public Item getItemById(int id) {
        Item item = null;
        String sql = "SELECT * FROM item WHERE item_id = ?;";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
            if (result.next()) {
                item = mapRowToItem(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server", e);
        }
        return item;
    }

    @Override
    public Item updateItem(Item item) {
        Item updatedItem = null;
        String sql = "UPDATE item SET name = ?, description = ?, size = ?, price = ? " +
                "WHERE item_id = ?;";
        try {
            int itemId = jdbcTemplate.update(sql, int.class, item.getName(), item.getDescription(),
                    item.getImgUrl(), item.getPrice(), item.getItemId());
            if (itemId == 0) {
                throw new DaoException("No rows affected, expected at least one");
            }
            updatedItem = getItemById(itemId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedItem;
    }

    @Override
    public void deleteItem(int id) {
        String itemCustomerSql = "DELETE item_customer WHERE item_id = ?;";
        String itemSql = "DELETE item WHERE item_id = ?;";
        try {
            jdbcTemplate.update(itemCustomerSql);
            jdbcTemplate.update(itemSql);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    private Item mapRowToItem(SqlRowSet result) {
        Item item = new Item();
        item.setItemId(result.getInt("item_id"));
        item.setName(result.getString("name"));
        item.setDescription(result.getString("description"));
        item.setImgUrl(result.getString("img_url"));
        item.setPrice(result.getBigDecimal("price"));
        return item;
    }
}

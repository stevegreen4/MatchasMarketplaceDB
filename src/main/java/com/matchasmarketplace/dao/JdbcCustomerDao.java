package com.matchasmarketplace.dao;

import com.matchasmarketplace.exception.DaoException;
import com.matchasmarketplace.model.Customer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCustomerDao implements CustomerDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT customer_id, first_name, last_name, address, city, state, phone, email " +
                "FROM customer;";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
            while (result.next()) {
                customers.add(mapRowToCustomer(result));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server",e);
        }
        return customers;
    }

    @Override
    public Customer getCustomerById(int customerId) {
        Customer customer = null;
        String sql = "SELECT customer_id, first_name, last_name, address, city, state, phone, email " +
                "FROM customer WHERE customer_id = ?;";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, customerId);
            if (result.next()) {
                customer = mapRowToCustomer(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server", e);
        }
        return customer;
    }

    @Override
    public List<Customer> getCustomerByFirstName(String search, boolean useWildCard) {
        List<Customer> firstNames = null;
        if (useWildCard) {
            search = "%" + search + "%";
        }
        String sql = "SELECT customer_id, first_name, last_name, address, city, state, phone, email " +
                "FROM customer WHERE first_name ILIKE ?;";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, search);
            while (result.next()) {
                firstNames.add(mapRowToCustomer(result));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server", e);
        }
        return firstNames;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer createdCustomer = null;
        String sql = "INSERT INTO customer (first_name, last_name, address, city, state, phone, email) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING customer_id;";
        try {
            int newCustomerId = jdbcTemplate.queryForObject(sql, int.class, customer.getFirstName(),
                    customer.getLastName(), customer.getAddress(), customer.getCity(), customer.getState(),
                    customer.getPhone(), customer.getEmail());
            createdCustomer = getCustomerById(newCustomerId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return createdCustomer;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer updatedCustomer = null;
        String sql = "UPDATE customer SET first_name = ?, last_name = ?, address = ?, " +
                "city = ?, state = ?, phone = ?, email = ? " +
                "WHERE customer_id = ?;";
        try {
            int numberOfRows = jdbcTemplate.update(sql, customer.getFirstName(),
                    customer.getLastName(), customer.getAddress(), customer.getCity(),
                    customer.getState(), customer.getPhone(), customer.getEmail(), customer.getCustomerId());
            if (numberOfRows == 0) {
                throw new DaoException("No rows affected, expected at least one");
            } else {
                updatedCustomer = getCustomerById(customer.getCustomerId());
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedCustomer;
    }

    @Override
    public int deleteCustomer(int customerId) {
        int numberOfRowsDeleted = 0;
        String itemCustomerSql = "DELETE FROM item_customer WHERE customer_id = ?;";
        String sql = "DELETE FROM customer WHERE customer_id = ?;";
        try {
            jdbcTemplate.update(itemCustomerSql, customerId);
            numberOfRowsDeleted = jdbcTemplate.update(sql, customerId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRowsDeleted;
    }

    private Customer mapRowToCustomer(SqlRowSet rowSet) {
        Customer customer = new Customer();
        customer.setCustomerId(rowSet.getInt("customer_id"));
        customer.setFirstName(rowSet.getString("first_name"));
        customer.setLastName(rowSet.getString("last_name"));
        customer.setAddress(rowSet.getString("address"));
        customer.setCity(rowSet.getString("city"));
        customer.setState(rowSet.getString("state"));
        customer.setPhone(rowSet.getString("phone"));
        customer.setEmail(rowSet.getString("email"));
        return customer;
    }


}

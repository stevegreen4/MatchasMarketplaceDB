package com.matchasmarketplace.dao;

import com.matchasmarketplace.model.Customer;

import java.util.List;

public interface CustomerDao {

    List<Customer> getAllCustomers();

    Customer getCustomerById(int customerId);

    List<Customer> getCustomerByFirstName(String search, boolean useWildCard);
    // add search features for last name, address, city, state, phone, and email

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    int deleteCustomer(int customerId);

}

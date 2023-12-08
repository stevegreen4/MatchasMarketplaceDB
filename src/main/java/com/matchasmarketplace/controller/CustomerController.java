package com.matchasmarketplace.controller;

import com.matchasmarketplace.dao.CustomerDao;
import com.matchasmarketplace.exception.DaoException;
import com.matchasmarketplace.model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerDao customerDao;

    public CustomerController(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getAllCustomers() {
        List<Customer> customers;
        try {
            customers = customerDao.getAllCustomers();
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return customers;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable int id) {
        Customer customer = null;
        try {
            customer = customerDao.getCustomerById(id);
            if (customer == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return customer;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Customer createCustomer(@RequestBody Customer customer) {
        Customer newCustomer = null;
        try {
            newCustomer = customerDao.createCustomer(customer);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return newCustomer;
    }

    //todo add authorization
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Customer updateCustomer(@PathVariable int id, @RequestBody Customer updatedCustomer) {
        Customer customer = null;
        try {
            customer = customerDao.updateCustomer(updatedCustomer);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return customer;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable int id) {
        try {
            customerDao.deleteCustomer(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}

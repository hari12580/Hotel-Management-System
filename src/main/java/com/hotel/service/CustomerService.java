package com.hotel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hotel.config.ResourceNotFoundException;
import com.hotel.entity.Customer;
import com.hotel.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Add customer
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Get customer by ID
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Customer not found")
                );
    }

    // Delete customer
    public void deleteCustomer(Long id) {

        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found");
        }

        customerRepository.deleteById(id);
    }
}
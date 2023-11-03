package com.ethnocopia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ethnocopia.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}

package com.nhom2.repository;

import org.springframework.data.repository.CrudRepository;

import com.nhom2.entities.Order;

public interface OrderRepo extends CrudRepository<Order, Integer>  {

}

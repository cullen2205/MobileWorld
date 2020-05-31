package com.nhom2.services;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhom2.entities.Order;
import com.nhom2.repository.OrderRepo;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private SessionFactory factory;

	@Override
	public Iterable<Order> findAll() {
		// TODO Auto-generated method stub
		return orderRepo.findAll();
	}

	@Override
	public Order findOne(int id) {
		// TODO Auto-generated method stub
		return orderRepo.findOne(id);
	}

	@Override
	public void save(Order order) {
		// TODO Auto-generated method stub
		orderRepo.save(order);
	}

	@Override
	public void update(Order order) {
		// TODO Auto-generated method stub
		orderRepo.save(order);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Order> getNewOrder() {
		// TODO Auto-generated method stub
		String hql = "FROM Order WHERE status = 0";
		return factory.getCurrentSession().createQuery(hql).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Order> getCheckedOrder() {
		// TODO Auto-generated method stub
		String hql = "FROM Order WHERE status = 1";
		return factory.getCurrentSession().createQuery(hql).list();
	}

}

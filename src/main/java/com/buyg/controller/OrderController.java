package com.buyg.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buyg.beans.Orders;
import com.buyg.service.order.OrderService;

@RequestMapping(value = "/orderController")
@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping(value = "/placeOrder")
	public Map<String, Object> placeOrder(@RequestBody Orders orders) throws Exception {
		System.out.println(orders.toString());
		return orderService.placeOrder(orders);
	}

	@GetMapping(value = "/getOrdersForConsumer/{consumerId}")
	public Map<String, Object> getConsumerByID(@PathVariable("consumerId") Integer consumerId) throws Exception {
		return orderService.getOrdersForConsumer(consumerId);
	}

}

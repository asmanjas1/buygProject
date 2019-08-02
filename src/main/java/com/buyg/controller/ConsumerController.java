package com.buyg.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.buyg.beans.CommonBean;
import com.buyg.beans.Consumer;
import com.buyg.beans.ConsumerAddress;
import com.buyg.service.consumer.ConsumerService;

@RequestMapping(value = "/consumerController")
@RestController
public class ConsumerController {

	@Autowired
	private ConsumerService consumerService;

	@PostMapping(value = "/signUp")
	public Map<String, Object> doSignUp(@RequestBody Consumer consumer) throws Exception {
		System.out.println(consumer.toString());
		return consumerService.signUp(consumer);
	}

	@PostMapping(value = "/login")
	public Map<String, Object> doLogin(@RequestBody Consumer consumer) throws Exception {
		return consumerService.doLogin(consumer);
	}

	@PostMapping(value = "/saveaddress")
	public Map<String, Object> saveAddress(@RequestBody ConsumerAddress consumerAddress) throws Exception {
		return consumerService.saveAddress(consumerAddress);
	}

	@GetMapping(value = "/getAddress/{consumerId}")
	public Map<String, Object> getAddressesForConsumer(@PathVariable("consumerId") Integer consumerId)
			throws Exception {
		return consumerService.getAllAddressForConsumerId(consumerId);
	}

	@GetMapping(value = "/getMappingExampleWithParams")
	public List<ConsumerAddress> getParamExample(@RequestParam("consumerId") Integer consumerId) throws Exception {
		System.out.println(consumerId);
		return null;
	}

	@PutMapping("/consumerUpdate/{id}")
	public Map<String, Object> updateStudent(@RequestBody Consumer consumer, @PathVariable Integer id) {
		return consumerService.updateConsumer(consumer, id);
	}

	@PutMapping("/consumerPasswordUpdate/{id}")
	public Map<String, Object> updatePassword(@RequestBody CommonBean bean, @PathVariable Integer id) {
		return consumerService.updatePassword(bean, id);
	}
}

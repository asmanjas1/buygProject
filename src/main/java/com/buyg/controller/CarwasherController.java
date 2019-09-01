package com.buyg.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buyg.beans.Carwasher;
import com.buyg.beans.CarwasherAddress;
import com.buyg.beans.CarwasherFirebasenBean;
import com.buyg.beans.CommonBean;
import com.buyg.service.carwasher.CarwasherService;

@RequestMapping(value = "/carwasherController")
@RestController
public class CarwasherController {

	@Autowired
	private CarwasherService carwasherService;

	@PostMapping(value = "/signUp")
	public Map<String, Object> doSignUp(@RequestBody Carwasher shop) throws Exception {
		System.out.println(shop.toString());
		return carwasherService.signUp(shop);
	}

	@PostMapping(value = "/login")
	public Map<String, Object> doLogin(@RequestBody Carwasher shop) throws Exception {
		return carwasherService.doLogin(shop);
	}

	@PostMapping(value = "/saveaddress")
	public Map<String, Object> saveAddress(@RequestBody CarwasherAddress shopAddress) throws Exception {
		return carwasherService.saveAddress(shopAddress);
	}

	@PostMapping(value = "/saveFirebaseToken")
	public Map<String, Object> saveFirebaseToken(@RequestBody CarwasherFirebasenBean carwasherFirebasenBean)
			throws Exception {
		return carwasherService.saveFirebaseToken(carwasherFirebasenBean);
	}

	@GetMapping(value = "/getAddress/{shopId}")
	public Map<String, Object> getAddressesForshop(@PathVariable("shopId") Integer shopId) throws Exception {
		// return shopService.getAddressForshopId(shopId);
		return null;
	}

	@PutMapping("/carwasherUpdate/{id}")
	public Map<String, Object> updateStudent(@RequestBody Carwasher shop, @PathVariable Integer id) {
		// return shopService.updateShop(shop, id);
		return null;
	}

	@PutMapping("/carwasherPasswordUpdate/{id}")
	public Map<String, Object> updatePassword(@RequestBody CommonBean bean, @PathVariable Integer id) {
		// return shopService.updatePassword(bean,id);
		return null;
	}

}

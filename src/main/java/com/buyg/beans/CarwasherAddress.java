package com.buyg.beans;

import lombok.Data;

@Data
public class CarwasherAddress {
	private Integer addressId;
	private String addressLine;
	private String locality;
	private String city;
	private String state;
	private Integer pincode;
	private Carwasher carwasher;
}

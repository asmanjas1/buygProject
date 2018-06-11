package com.buyg.beans;

import lombok.Data;

@Data
public class ConsumerAddress {
	private Integer addressId;
	private String addressLine;
	private String locality;
	private String city;
	private String state;
	private Integer pincode;
	private Consumer consumer;
}

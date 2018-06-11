package com.buyg.beans;

import lombok.Data;

@Data
public class ShopAddress {
	private Integer addressId;
	private String shopName;
	private String addressLine;
	private String locality;
	private String city;
	private String state;
	private Integer pincode;
	private Double longitude;
	private Double latitude;
	private Shop shop;
}

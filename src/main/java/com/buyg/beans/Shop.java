package com.buyg.beans;

import java.util.Date;

import lombok.Data;

@Data
public class Shop {
	private Integer shopId;
	private String ownerName;
	private String email;
	private Long phoneNumber;
	private String password;
	private ShopAddress address;
	private Date registrationDate;
	private Date lastUpdateDate;
}

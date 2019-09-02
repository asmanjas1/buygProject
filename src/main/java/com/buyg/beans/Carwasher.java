package com.buyg.beans;

import lombok.Data;

@Data
public class Carwasher {
	private Integer carwasherId;
	private String name;
	private String email;
	private String phoneNumber;
	private String password;
	private String registrationDate;
	private String lastUpdateDate;
	private CarwasherAddress carwasherAddress;
}

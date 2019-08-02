package com.buyg.beans;

import java.util.Date;

import lombok.Data;

@Data
public class Carwasher {
	private Integer carwasherId;
	private String name;
	private String email;
	private Long phoneNumber;
	private String password;
	private Date registrationDate;
	private Date lastUpdateDate;
	private CarwasherAddress carwasherAddress;
}

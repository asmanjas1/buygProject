package com.buyg.beans;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Consumer {
	private Integer consumerId;
	private String name;
	private String email;
	private Long phoneNumber;
	private String password;
	private Date registrationDate;
	private Date lastUpdateDate;
	private Vehicle vehicle;
	private List<ConsumerAddress> listOfAddress;
}

package com.buyg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "carwasherfirebase")
public class CarwasherFirebaseEntity {
	@Id
	@Column(name = "carwasherfirebaseid")
	private Integer consumerFirebaseId;
	@Column(name = "firebasetoken")
	private String firebaseToken;
}

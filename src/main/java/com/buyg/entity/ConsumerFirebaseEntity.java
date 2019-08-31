package com.buyg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "consumerfirebase")
public class ConsumerFirebaseEntity {
	@Id
	@Column(name = "consumerfirebaseid")
	private Integer consumerFirebaseId;
	@Column(name = "firebasetoken")
	private String firebaseToken;

}

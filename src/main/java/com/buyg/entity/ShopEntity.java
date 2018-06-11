package com.buyg.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name = "shop")
@Data
public class ShopEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "shopid")
	private Integer shopId;
	@Column(name = "ownername")
	private String ownerName;
	@Column(name = "email")
	private String email;
	@Column(name = "phonenumber")
	private Long phoneNumber;
	@JsonIgnore
	@Column(name = "password")
	private String password;
	@CreationTimestamp
	@Column(name = "registrationdate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date registrationDate;
	@UpdateTimestamp
	@Column(name = "lastupdatedate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateDate;
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "shopEntity", fetch = FetchType.LAZY)
	@JsonManagedReference
	private ShopAddressEntity shopAddressEnitiy;
}

package com.buyg.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class OrdersEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "orderid")
	private Integer orderId;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "orderdate")
	private Date orderDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ordercompleteddate")
	private Date orderCompletedDate;
	@Column(name = "orderamount")
	private Double orderAmount;
	@Column(name = "orderstatus")
	private String orderStatus;
	@ManyToOne
	@JoinColumn(name = "consumerid")
	private ConsumerEntity consumerEntity;
	@ManyToOne
	@JoinColumn(name = "carwasherid")
	private CarwasherEntity carwasherEntity;

}

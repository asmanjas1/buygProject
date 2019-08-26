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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class OrdersEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "orderid")
	private Integer orderId;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "orderdate")
	private Date orderDate;
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ordercompleteddate")
	private Date orderCompletedDate;
	@Column(name = "orderamount")
	private Double orderAmount;
	@Column(name = "orderstatus")
	private String orderStatus;
	@Column(name = "orderpaymentstatus")
	private String orderPaymentStatus;
	@ManyToOne
	@JoinColumn(name = "consumerid")
	private ConsumerEntity consumerEntity;

	@Column(name = "ordercarwasherid")
	private Integer orderCarwasherId;
	@Column(name = "ordervehicleid")
	private Integer orderVehicleId;
	@Column(name = "orderaddressid")
	private Integer orderAddressId;

}

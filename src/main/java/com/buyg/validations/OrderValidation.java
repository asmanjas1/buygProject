package com.buyg.validations;

import static com.buyg.utils.CommonUtils.isNullOrEmpty;
import static java.util.Objects.nonNull;

import org.springframework.stereotype.Service;

import com.buyg.beans.Orders;

@Service
public class OrderValidation {

	public boolean validatePlaceOrder(Orders orders) {
		if (nonNull(orders) && nonNull(orders.getOrderAmount()) && !isNullOrEmpty(orders.getOrderPaymentStatus())
				&& !isNullOrEmpty(orders.getOrderStatus())) {
			return true;
		}
		return false;
	}

}

package com.buyg.service.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyg.beans.Orders;
import com.buyg.entity.CarwasherEntity;
import com.buyg.entity.CarwasherFirebaseEntity;
import com.buyg.entity.ConsumerEntity;
import com.buyg.entity.OrdersEntity;
import com.buyg.notification.SendNotifications;
import com.buyg.repository.carwasher.CarwasherFirebaseRepository;
import com.buyg.repository.orders.OrdersRepository;
import com.buyg.service.carwasher.CarwasherService;
import com.buyg.utils.BuyGConstants;
import com.buyg.validations.OrderValidation;
import com.google.gson.Gson;

@Service
public class OrderService {

	Gson gson = new Gson();

	@Autowired
	private OrdersRepository ordersRepository;

	@Autowired
	private OrderValidation orderValidation;

	@Autowired
	private SendNotifications sendNotifications;

	@Autowired
	private CarwasherService carwasherService;

	@Autowired
	private CarwasherFirebaseRepository carwasherFirebaseRepository;

	public Map<String, Object> placeOrder(Orders orders) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 500;
		String resMsg = "Error Occured";
		OrdersEntity ordersEnt = null;
		// Orders savedOrder = new Orders();
		if (orderValidation.validatePlaceOrder(orders)) {
			OrdersEntity ordersEntity = new OrdersEntity();
			ordersEntity.setOrderAmount(orders.getOrderAmount());
			ordersEntity.setOrderPaymentStatus(orders.getOrderPaymentStatus());
			ordersEntity.setOrderStatus(orders.getOrderStatus());
			ConsumerEntity con = new ConsumerEntity();
			con.setConsumerId(orders.getConsumer().getConsumerId());
			ordersEntity.setConsumerEntity(con);

			ordersEntity.setOrderVehicleId(orders.getOrderVehicleId());
			ordersEntity.setOrderAddressId(orders.getOrderAddressId());
			ordersEntity.setOrderAddressCity(orders.getOrderAddressCity().toLowerCase());
			ordersEntity.setOrderAddressState(orders.getOrderAddressState().toLowerCase());

			ordersEnt = ordersRepository.saveAndFlush(ordersEntity);
			// savedOrder.setCarwasher(ordersEnt.ge);
			responseCode = 200;
			resMsg = "Successfully placed order";
			sendOrderNotifications(orders.getOrderAddressCity(), orders.getOrderAddressState());
		} else {
			responseCode = 400;
			resMsg = "order validation failed";
		}

		responseMap.put(BuyGConstants.DATA_STRING, gson.toJson(ordersEnt));
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		responseMap.put(BuyGConstants.RESPONSE_MSG, resMsg);
		return responseMap;
	}

	public void sendOrderNotifications(String city, String state) {
		List<CarwasherEntity> list = carwasherService.getCarwahserByAddressMatching(city, state);
		if (list.size() > 0) {
			List<Integer> idList = new ArrayList<>();
			for (CarwasherEntity ce : list) {
				idList.add(ce.getCarwasherId());
			}
			List<CarwasherFirebaseEntity> cfeList = carwasherFirebaseRepository.findByconsumerFirebaseIdIn(idList);

			if (cfeList.size() > 0) {
				List<String> tokenList = new ArrayList<>();
				for (CarwasherFirebaseEntity cfe : cfeList) {
					tokenList.add(cfe.getFirebaseToken());
				}
				String[] item = tokenList.toArray(new String[tokenList.size()]);

				sendNotifications.sendNotifications("New Order", "You got a new order.. click here to accept it.",
						item);
			}
		}
	}

	public Map<String, Object> getOrdersForConsumer(Integer consumerId) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 500;
		String resMsg = "Error Occured";
		List<OrdersEntity> ordersEntity = new ArrayList<>();
		List<String> orderList = new ArrayList<>();
		try {
			ConsumerEntity consumerEntity = new ConsumerEntity();
			consumerEntity.setConsumerId(consumerId);
			ordersEntity = ordersRepository.findByConsumerEntity(consumerEntity);
			Orders order = new Orders();
			for (OrdersEntity oe : ordersEntity) {
				order.setOrderId(oe.getOrderId());
				// order.setOrderAmount(oe.getOrderAmount());
				order.setOrderDate(oe.getOrderDate());
				order.setOrderStatus(oe.getOrderStatus());
				orderList.add(gson.toJson(order));
			}
			responseCode = 200;
			resMsg = "Success";
		} catch (Exception e) {
		}
		responseMap.put(BuyGConstants.DATA_STRING, orderList);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		responseMap.put(BuyGConstants.RESPONSE_MSG, resMsg);
		return responseMap;
	}

	public Map<String, Object> getCompletedOrdersForCarwasher(Integer carwasherId) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 500;
		String resMsg = "Error Occured";
		List<OrdersEntity> ordersEntity = new ArrayList<>();
		List<String> orderList = new ArrayList<>();
		try {
			ordersEntity = ordersRepository.fetchOrdersForCwarwasherByStatus(carwasherId, "Completed");
			Orders order = new Orders();
			for (OrdersEntity oe : ordersEntity) {
				order.setOrderId(oe.getOrderId());
				order.setOrderDate(oe.getOrderDate());
				orderList.add(gson.toJson(order));
			}
			responseCode = 200;
			resMsg = "Success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		responseMap.put(BuyGConstants.DATA_STRING, orderList);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		responseMap.put(BuyGConstants.RESPONSE_MSG, resMsg);
		return responseMap;
	}

	public Map<String, Object> getInProgressOrdersForCarwasher(Integer carwasherId) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 500;
		String resMsg = "Error Occured";
		List<OrdersEntity> ordersEntity = new ArrayList<>();
		List<String> orderList = new ArrayList<>();
		try {
			ordersEntity = ordersRepository.fetchOrdersForCwarwasherByStatus(carwasherId, "In Progress");
			Orders order = new Orders();
			for (OrdersEntity oe : ordersEntity) {
				order.setOrderId(oe.getOrderId());
				order.setOrderDate(oe.getOrderDate());
				orderList.add(gson.toJson(order));
			}
			responseCode = 200;
			resMsg = "Success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		responseMap.put(BuyGConstants.DATA_STRING, orderList);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		responseMap.put(BuyGConstants.RESPONSE_MSG, resMsg);
		return responseMap;
	}

	public Map<String, Object> getAllNewOrdersForCarwasher(Integer carwasherId, String city, String state) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 500;
		String resMsg = "Error Occured";
		List<OrdersEntity> ordersEntity = new ArrayList<>();
		List<String> orderList = new ArrayList<>();
		try {
			ordersEntity = ordersRepository.fetchAllNewOrderForCarwasher(city.toLowerCase(), state.toLowerCase(),
					"New");
			Orders order = new Orders();
			for (OrdersEntity oe : ordersEntity) {
				order.setOrderId(oe.getOrderId());
				order.setOrderDate(oe.getOrderDate());
				orderList.add(gson.toJson(order));
			}
			responseCode = 200;
			resMsg = "Success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		responseMap.put(BuyGConstants.DATA_STRING, orderList);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		responseMap.put(BuyGConstants.RESPONSE_MSG, resMsg);
		return responseMap;
	}

}

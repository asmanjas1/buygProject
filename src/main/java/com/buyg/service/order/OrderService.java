package com.buyg.service.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyg.beans.Carwasher;
import com.buyg.beans.Consumer;
import com.buyg.beans.ConsumerAddress;
import com.buyg.beans.Orders;
import com.buyg.beans.Vehicle;
import com.buyg.entity.CarwasherEntity;
import com.buyg.entity.CarwasherFirebaseEntity;
import com.buyg.entity.ConsumerAddressEntity;
import com.buyg.entity.ConsumerEntity;
import com.buyg.entity.ConsumerFirebaseEntity;
import com.buyg.entity.OrdersEntity;
import com.buyg.entity.VehicleEntity;
import com.buyg.notification.SendNotifications;
import com.buyg.repository.carwasher.CarwasherFirebaseRepository;
import com.buyg.repository.carwasher.CarwasherRepository;
import com.buyg.repository.consumer.ConsumerAddressRepository;
import com.buyg.repository.consumer.ConsumerFirebaseRepository;
import com.buyg.repository.consumer.ConsumerVehicleRepository;
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

	@Autowired
	private ConsumerFirebaseRepository consumerFirebaseRepository;

	@Autowired
	private ConsumerVehicleRepository consumerVehicleRepository;

	@Autowired
	private ConsumerAddressRepository consumerAddressRepository;

	@Autowired
	private CarwasherRepository carwasherRepository;

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
			List<CarwasherFirebaseEntity> cfeList = carwasherFirebaseRepository.findBycarwasherFirebaseIdIn(idList);

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
				order.setOrderDate(oe.getOrderDate().toString());
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
				order.setOrderDate(oe.getOrderDate().toString());
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
				order.setOrderDate(oe.getOrderDate().toString());
				Consumer consumer = new Consumer();
				consumer.setConsumerId(oe.getConsumerEntity().getConsumerId());
				order.setConsumer(consumer);
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
				order.setOrderDate(oe.getOrderDate().toString());
				Consumer consumer = new Consumer();
				consumer.setConsumerId(oe.getConsumerEntity().getConsumerId());
				order.setConsumer(consumer);
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

	public Map<String, Object> confirmOrderByCarwasher(Integer orderId, Integer carwasherId, Integer consumerId) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 500;
		String resMsg = "Error Occured";
		boolean flag = false;
		try {
			Integer updatedRows = ordersRepository.confirmOrderByCarwasher(orderId, carwasherId, "In Progress", "New");
			if (updatedRows > 0) {
				sendOrderConfirmNotificatoin(consumerId);
				flag = true;
			}
			responseCode = 200;
			resMsg = "Success";
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
		responseMap.put(BuyGConstants.DATA_STRING, flag);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		responseMap.put(BuyGConstants.RESPONSE_MSG, resMsg);
		return responseMap;
	}

	public Map<String, Object> completeOrderByOrderId(Integer orderId, Integer consumerId) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 500;
		String resMsg = "Error Occured";
		boolean flag = false;
		try {
			Integer updatedRows = ordersRepository.completeOrderByOrderId(orderId, "Completed", "Completed");
			if (updatedRows > 0) {
				sendOrderCompletedNotificatoin(consumerId, orderId);
				flag = true;
			}
			responseCode = 200;
			resMsg = "Success";
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
		responseMap.put(BuyGConstants.DATA_STRING, flag);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		responseMap.put(BuyGConstants.RESPONSE_MSG, resMsg);
		return responseMap;
	}

	public void sendOrderConfirmNotificatoin(Integer consumerId) {
		if (consumerId != null) {
			ConsumerFirebaseEntity cfe = consumerFirebaseRepository.findByconsumerFirebaseId(consumerId);
			if (cfe != null) {
				List<String> tokenList = new ArrayList<>();
				tokenList.add(cfe.getFirebaseToken());
				String[] item = tokenList.toArray(new String[tokenList.size()]);
				sendNotifications.sendNotifications("Order Confirmed",
						"Your order has been confirmed.. click to check details.", item);
			}
		}

	}

	public void sendOrderCompletedNotificatoin(Integer consumerId, Integer orderId) {
		if (consumerId != null) {
			ConsumerFirebaseEntity cfe = consumerFirebaseRepository.findByconsumerFirebaseId(consumerId);
			if (cfe != null) {
				List<String> tokenList = new ArrayList<>();
				tokenList.add(cfe.getFirebaseToken());
				String[] item = tokenList.toArray(new String[tokenList.size()]);
				sendNotifications.sendNotifications("Order Confirmed",
						"Your order with OrderId: " + orderId + " is completed. Thanks for choosing us!", item);
			}
		}

	}

	public Map<String, Object> getAllDetailsForOrderId(Integer orderId) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 500;
		String resMsg = "Error Occured";
		Orders orders = new Orders();
		try {
			OrdersEntity ordersEntity = ordersRepository.findByOrderId(orderId);
			orders.setOrderId(ordersEntity.getOrderId());
			orders.setOrderDate(ordersEntity.getOrderDate().toString());
			orders.setOrderCompletedDate(ordersEntity.getOrderCompletedDate().toString());
			orders.setOrderAmount(ordersEntity.getOrderAmount());
			orders.setOrderStatus(ordersEntity.getOrderStatus());
			orders.setOrderPaymentStatus(ordersEntity.getOrderPaymentStatus());

			ConsumerEntity consumerEntity = ordersEntity.getConsumerEntity();
			Consumer consumer = new Consumer();
			consumer.setEmail(consumerEntity.getEmail());
			consumer.setName(consumerEntity.getName());
			consumer.setPhoneNumber(consumerEntity.getPhoneNumber());
			orders.setConsumer(consumer);

			Integer vehicleId = ordersEntity.getOrderVehicleId();
			orders.setOrderVehicle(getVehicleById(vehicleId));

			Integer addressId = ordersEntity.getOrderAddressId();
			orders.setOrderAddress(getConsumerAddressById(addressId));

			Integer carwasherId = ordersEntity.getOrderCarwasherId();
			orders.setCarwasher(getCarwasherById(carwasherId));

			responseCode = 200;
			resMsg = "Success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		responseMap.put(BuyGConstants.DATA_STRING, gson.toJson(orders));
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		responseMap.put(BuyGConstants.RESPONSE_MSG, resMsg);
		return responseMap;
	}

	public Carwasher getCarwasherById(Integer id) {
		Carwasher carwasher = null;
		try {
			carwasher = new Carwasher();
			CarwasherEntity carwasherEntity = carwasherRepository.findBycarwasherId(id);
			carwasher.setCarwasherId(carwasherEntity.getCarwasherId());
			carwasher.setEmail(carwasherEntity.getEmail());
			carwasher.setName(carwasherEntity.getName());
			carwasher.setPhoneNumber(carwasherEntity.getPhoneNumber());
		} catch (Exception e) {
		}
		return carwasher;
	}

	public ConsumerAddress getConsumerAddressById(Integer id) {
		ConsumerAddress consumerAddress = null;
		try {
			consumerAddress = new ConsumerAddress();
			ConsumerAddressEntity consumerAddressEntity = consumerAddressRepository.findByAddressId(id);
			consumerAddress.setAddressId(consumerAddressEntity.getAddressId());
			consumerAddress.setAddressLine(consumerAddressEntity.getAddressLine());
			consumerAddress.setLocality(consumerAddressEntity.getLocality());
			consumerAddress.setCity(consumerAddressEntity.getCity());
			consumerAddress.setState(consumerAddressEntity.getState());
			consumerAddress.setPincode(consumerAddressEntity.getPincode());
		} catch (Exception e) {
		}
		return consumerAddress;
	}

	public Vehicle getVehicleById(Integer id) {
		Vehicle vehicle = null;
		try {
			vehicle = new Vehicle();
			VehicleEntity vehicleEntity = consumerVehicleRepository.findByVehicleId(id);
			vehicle.setVehicleId(vehicleEntity.getVehicleId());
			vehicle.setVehicleName(vehicleEntity.getVehicleName());
			vehicle.setVehicleNumber(vehicleEntity.getVehicleNumber());
			vehicle.setVehicleType(vehicleEntity.getVehicleType());
		} catch (Exception e) {
		}

		return vehicle;
	}
}

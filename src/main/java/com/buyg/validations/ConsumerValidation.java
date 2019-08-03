
package com.buyg.validations;

import static com.buyg.utils.CommonUtils.isNullOrEmpty;
import static java.util.Objects.nonNull;

import org.springframework.stereotype.Service;

import com.buyg.beans.Consumer;
import com.buyg.beans.ConsumerAddress;
import com.buyg.beans.Vehicle;

@Service
public class ConsumerValidation {

	public boolean validateConsumerForSignUp(Consumer consumer) {
		if (nonNull(consumer) && !isNullOrEmpty(consumer.getEmail()) && !isNullOrEmpty(consumer.getName())
				&& !isNullOrEmpty(consumer.getPassword())) {
			return true;
		}
		return false;
	}

	public boolean validateConsumerAdddress(ConsumerAddress consumerAddress) {
		if (nonNull(consumerAddress) && !isNullOrEmpty(consumerAddress.getAddressLine())
				&& !isNullOrEmpty(consumerAddress.getLocality()) && !isNullOrEmpty(consumerAddress.getCity())
				&& !isNullOrEmpty(consumerAddress.getState()) && nonNull(consumerAddress.getPincode())) {
			return true;
		}
		return false;
	}

	public boolean validateConsumerVehicle(Vehicle vehicle) {
		if (nonNull(vehicle) && nonNull(vehicle.getConsumer().getConsumerId())
				&& !isNullOrEmpty(vehicle.getVehicleName()) && !isNullOrEmpty(vehicle.getVehicleNumber())
				&& !isNullOrEmpty(vehicle.getVehicleType())) {
			return true;
		}
		return false;
	}
}

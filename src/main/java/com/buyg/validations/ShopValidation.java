package com.buyg.validations;

import static com.buyg.utils.CommonUtils.isNullOrEmpty;
import static java.util.Objects.nonNull;

import org.springframework.stereotype.Service;

import com.buyg.beans.Shop;
import com.buyg.beans.ShopAddress;

@Service
public class ShopValidation {

	public boolean validateShopForSignUp(Shop shop) {
		if (nonNull(shop) && !isNullOrEmpty(shop.getEmail()) && !isNullOrEmpty(shop.getOwnerName())
				&& !isNullOrEmpty(shop.getPassword()) && !isNullOrEmpty(String.valueOf(shop.getPhoneNumber()))) {
			return true;
		}
		return false;
	}

	public boolean validateShopAdddress(ShopAddress shopAddress) {
		if (nonNull(shopAddress) && !isNullOrEmpty(shopAddress.getAddressLine())
				&& !isNullOrEmpty(shopAddress.getLocality()) && !isNullOrEmpty(shopAddress.getCity())
				&& !isNullOrEmpty(shopAddress.getState()) && nonNull(shopAddress.getPincode())) {
			return true;
		}
		return false;
	}
}

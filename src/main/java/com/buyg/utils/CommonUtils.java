package com.buyg.utils;

import static java.util.Objects.isNull;

public class CommonUtils {

	public static boolean isNullOrEmpty(String value) {
		return isNull(value) || value.isEmpty();
	}

}

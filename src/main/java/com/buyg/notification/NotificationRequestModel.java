package com.buyg.notification;

import com.google.gson.annotations.SerializedName;

public class NotificationRequestModel {
	@SerializedName("data")
	private NotificationData mData;
	@SerializedName("registration_ids")
	private String mTo[];

	@SerializedName("priority")
	private String mPriority;

	public String getmPriority() {
		return mPriority;
	}

	public void setmPriority(String mPriority) {
		this.mPriority = mPriority;
	}

	public NotificationData getData() {
		return mData;
	}

	public void setData(NotificationData data) {
		mData = data;
	}

	public String[] getTo() {
		return mTo;
	}

	public void setTo(String[] to) {
		mTo = to;
	}
}

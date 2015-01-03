package com.chro.beerapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class Entry {
	private int category;
	private String productName;
	private float price;
	private int quantity;
	private String contactDetails;
	public float latitude;
	public float longtitude;
	public TimeZone tz;
	public Calendar beginTime;
	public Calendar endTime;

	public Entry(int category, String productName, float price, int quantity,
			String contactDetails, float latitude, float longtitude,
			Calendar beginTime, Calendar endTime) {
		this.category = category;
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
		this.contactDetails = contactDetails;
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}

	// constructor with Times as String for SQLiteDBHandler
	public Entry(int category, String productName, float price, int quantity,
			String contactDetails, float latitude, float longtitude,
			String beginTimeString, String endTimeString) {
		this.category = category;
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
		this.contactDetails = contactDetails;
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.setBeginTimeFromString(beginTimeString);
		this.setEndTimeFromString(endTimeString);
	}

	public Entry(String productName) {
		this.productName = productName;
	}

	public Calendar getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Calendar beginTime) {
		this.beginTime = beginTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public String getBeginTimeAsString() {
		return this.getTimeAsString(this.beginTime);
	}

	public String getEndTimeAsString() {
		return this.getTimeAsString(this.endTime);
	}

	public void setBeginTimeFromString(String timeString) {
		this.beginTime = this.getTimeFromString(timeString);
	}

	public void setEndTimeFromString(String timeString) {
		this.endTime = this.getTimeFromString(timeString);
	}

	private String getTimeAsString(Calendar calendar) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.US);
		return sdf.format(calendar.getTime());
	}

	private Calendar getTimeFromString(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.US);
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(dateString));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cal;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	/*
	 * @Note you are an idiot don't put that here it has to go to the
	 * CreateActivty public void SetUpTimeEssentials() { TimeZone tz =
	 * TimeZone.getDefault(); // et Timezone Calendar cal = new
	 * GregorianCalendar(tz); // initialize Calendar Date d = new Date();
	 * cal.setTime(d); // Set time to a new time @Note is it set to now? }
	 */
	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(float longtitude) {
		this.longtitude = longtitude;
	}

	public int getCategory(){
		return category;
	}
	public String getCategoryAsString() {  
        String s = String.valueOf(category); 
		return (s);
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}
}

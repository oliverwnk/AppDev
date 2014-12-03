package com.chro.beerapp;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class Entry {
	private int categorie;
	private String productName;
	private float price;
	private int quantity;
	private String contactDetails;
	public float latitude;
	public float longtitude;
	public TimeZone tz;
	public Calendar beginTime;
	public Calendar endTime;
	public Entry(int categorie,String productName,float price,int quantity,String contactDetails,float latitude, float longtitude,Calendar beginTime, Calendar endTime) {
		this.categorie=categorie;
		this.productName=productName;
		this.price=price;
		this.quantity=quantity;
		this.contactDetails=contactDetails;
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}
	
	public Entry(String productName){
		this.productName=productName;
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
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}
	/* @Note you are an idiot don't put that here it has to go to the CreateActivty 
	public void SetUpTimeEssentials()
	{
		TimeZone tz = TimeZone.getDefault(); // et Timezone
		Calendar cal = new GregorianCalendar(tz); // initialize Calendar
		Date d = new Date();
		cal.setTime(d); // Set time to a new time @Note is it set to now?
	}*/
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
	public int getCategorie() {
		return categorie;
	}
	public void setCategorie(int categorie) {
		this.categorie = categorie;
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

package com.chro.beerapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;




public class Entries {
	public ArrayList<Entry> Entries = new ArrayList<Entry>();
	public ArrayList<Entry> LastQuery = new ArrayList<Entry>(); //Zuletzt angeforderten Daten
	public ArrayList<Entry> MyEntries = new ArrayList<Entry>();
	
	TimeZone tz ; // et Timezone
	Calendar calBeg; // initialize Beginning Calendar
	Calendar calEnd;
	Date d = new Date();
	protected static Entries instants = null;
	protected Entries()
	{
		SetUpTimeEssentials();
		/*@TODO add the calendars here aswell */

		int i=2;
		Entry e = new Entry("Beer","Becks"+i,0.67f,4,"details",51.039926f, 13.731029f,"2014-04-23 16:29","2014-04-23 18:29",true);
		Entry e1 = new Entry("Beer","Chips"+i,i*0.67f,5,"details",51.044055f, 13.733217f,"2014-04-23 16:29","2014-04-23 18:29",true);
		Entry e2 = new Entry("Beer","Fanta"+i,i*0.67f,3,"details",51.044459f, 13.738882f,"2014-04-23 16:29","2014-04-23 18:29",true);
		Entries.add(e);
		Entries.add(e1);
		Entries.add(e2);
		LastQuery = Entries;
	}
	protected void Del()
	{
		Entries.clear();
	}
	public void SetUpTimeEssentials()
	{
		TimeZone tz = TimeZone.getDefault(); // et Timezone
		Calendar calBeg = new GregorianCalendar(tz); // initialize Beginning Calendar
		Calendar calEnd = new GregorianCalendar(tz);
		Date d = new Date();
		calBeg.setTime(d); // Set time to cur Time
		calEnd.setTime(d);
		calBeg.set(Calendar.HOUR_OF_DAY,23);
		calEnd.set(Calendar.HOUR_OF_DAY,24);
		calBeg.set(Calendar.DAY_OF_WEEK, 0);		
		
	}
	public void addEntrie(String category, String productName, float price, int quantity,
			String contactDetails, float latitude, float longtitude,
			String beginTimeString, String endTimeString,boolean active)
	{
		Entry e = new Entry(category,productName,price,quantity,contactDetails,longtitude, latitude,beginTimeString,endTimeString,active);
		Entries.add(e);
	}
	
	public void addMyEntry(String category, String productName, float price, int quantity,
			String contactDetails, float latitude, float longtitude,
			String beginTimeString, String endTimeString,boolean active)
	{
		Entry e = new Entry(category,productName,price,quantity,contactDetails,longtitude, latitude,beginTimeString,endTimeString,active);
		MyEntries.add(e);
	}
	public static Entries getInstance()
	{
		if(instants == null)
		{
			instants = new Entries();
		}
		return instants;
	}

	public ArrayList<Entry> getEntriesByName(String Name)
	{
		LastQuery = Entries;
		return Entries;
	}
	public ArrayList<Entry> getEntriesByLocation(int Categorie,float latiude,float longitude,int Radius)
	{
		LastQuery = Entries;
		return Entries;
		
	}
	public List<Entry> getEntriesByID(int UserID)
	{
		LastQuery = Entries;
		List<Entry> L = new ArrayList<Entry>();
		for(int i = 0; i < Entries.size();i++)
		{
			Entry e = Entries.get(i);
			if(e.getUser_id() == UserID)
			{
				L.add(e);
			}
		}
		return L;
		
	}
	public ArrayList<Entry> getLastRequest()
	{
		return LastQuery;
	}
	
	
}

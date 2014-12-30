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
		Entry e = new Entry(0,"Becks"+i,0.67f,4,"details",51.039926f, 13.731029f,"2014-04-23 16:29","2014-04-23 18:29");
		Entry e1 = new Entry(1,"Chips"+i,i*0.67f,5,"details",51.044055f, 13.733217f,"2014-04-23 16:29","2014-04-23 18:29");
		Entry e2 = new Entry(2,"Fanta"+i,i*0.67f,3,"details",51.044459f, 13.738882f,"2014-04-23 16:29","2014-04-23 18:29");
		Entries.add(e);
		Entries.add(e1);
		Entries.add(e2);
		LastQuery = Entries;
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
	public ArrayList<Entry> getEntriesByID(int UserID)
	{
		LastQuery = Entries;
		return Entries;
		
	}
	public ArrayList<Entry> getLastRequest()
	{
		return LastQuery;
	}
	
	
}

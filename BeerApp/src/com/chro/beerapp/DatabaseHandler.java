package com.chro.beerapp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	public static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "entriesDB";
	private static final String ENTRIES_TABLE = "entries";
	
	
	
	
	private static final String KEY_ID = "id";
	private static final String ID = "entryId";
	private static final String CATEGORY = "category";
	private static final String PRODUCT_NAME = "productName";
	private static final String PRICE = "price";
	private static final String QUANTITY = "quantity";
	private static final String CONTACT_DETAILS = "contactDetails";
	private static final String LATITUDE = "latitude";
	private static final String LONGTITUDE = "longtitude";
	private static final String BEGIN_TIME = "beginTime";
	private static final String END_TIME = "endTime";
	private static final String USER_ID = "userId";
	private static final String DATE = "date";
	
	
	
	public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
	@Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ENTRIES_TABLE = "CREATE TABLE " + ENTRIES_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ID + " INT, "
        		+ CATEGORY + " TEXT, "
                + PRODUCT_NAME + " TEXT, " 
        		+ PRICE + " REAL, " 
                + QUANTITY + " INT, " 
        		+ CONTACT_DETAILS + " TEXT, "
                + LATITUDE + " TEXT, "
                + LONGTITUDE + " TEXT, "
                + BEGIN_TIME + " TEXT, " 
                + END_TIME + " TEXT, " 
                + USER_ID + " INT, "
                + DATE + " TEXT)";
        
        db.execSQL(CREATE_ENTRIES_TABLE);
    }
	
	@Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(DatabaseHandler.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + ENTRIES_TABLE);
	    onCreate(db);
	}
	
	// Adding new entry
    void addEntry(Entry entry,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        if(!exists(entry)){
        ContentValues values = new ContentValues();
        values.put(ID, entry.getID());
        values.put(CATEGORY,entry.getCategory());
        values.put(PRODUCT_NAME, entry.getProductName());
        values.put(PRICE, entry.getPrice());
        values.put(QUANTITY, entry.getQuantity());
        values.put(CONTACT_DETAILS, entry.getContactDetails());
        values.put(LATITUDE, entry.getLatitude());
        values.put(LONGTITUDE, entry.getLongtitude());
        values.put(BEGIN_TIME, entry.getBeginTimeAsString());
        values.put(END_TIME, entry.getEndTimeAsString());
        values.put(USER_ID, entry.getUser_id());
        values.put(DATE, date);
 
        // Inserting Row
        db.insert(ENTRIES_TABLE, null, values);
        }
        db.close(); // Closing database connection
    }
    
    public Entry getEntry(int id){
    	 
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
     
        // 2. build query
        Cursor cursor = 
                db.query(ENTRIES_TABLE, // a. table
                		new String[] { QUANTITY, PRODUCT_NAME, PRICE,QUANTITY,CONTACT_DETAILS,
                			LATITUDE,LONGTITUDE,BEGIN_TIME,END_TIME,USER_ID }, // b. column names
                KEY_ID + "=?", // c. selections 
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
     
        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
     
        // 4. build book object
        //new Entry(id,category, productName, price, quantity, contactDetails, latitude, longtitude, beginTime, endTime)
        Entry entry = new Entry(
        		cursor.getInt(1),
        		cursor.getString(2),
        		cursor.getString(3),
        		cursor.getFloat(4),
        		cursor.getInt(5),
        		cursor.getString(6),
        		cursor.getFloat(7),
        		cursor.getFloat(8),
        		cursor.getString(9),
        		cursor.getString(10),
        		cursor.getInt(11),
        		true);
     
        db.close();
        cursor.close();
        // 5. return book
        return entry;
    }
    
    public boolean deleteEntry(String entryId){
    	SQLiteDatabase db = this.getWritableDatabase();
    	boolean success = db.delete(ENTRIES_TABLE, ID + "=?",
                new String[] { entryId})>0;
    	db.close();
    	return success;
    }
    
    //checks if entry exists
    public boolean exists(Entry entry) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	   Cursor cursor = db.rawQuery("SELECT  * FROM " + ENTRIES_TABLE + " where " + ID + "=" + Integer.toString(entry.getID()), 
    	        null);
    	   boolean exists = (cursor.getCount() > 0);
    	   cursor.close();
    	   return exists;
    	}
    
    public String getDate(Entry entry){
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.rawQuery("SELECT  * FROM " + ENTRIES_TABLE + " where " + ID + "=" + Integer.toString(entry.getID()), 
    	        null);
    	cursor.moveToFirst();
    	return cursor.getString(12);
    }
    
    public ArrayList<Entry> getAllEntries() {
        ArrayList<Entry> entryList = new ArrayList<Entry>();
  
        // 1. build the query
        String query = "SELECT  * FROM " + ENTRIES_TABLE;
  
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
  
        // 3. go over each row, build book and add it to list
        Entry entry = null;
        if (cursor.moveToFirst()) {
            do {
            	 	entry = new Entry(
            	 		cursor.getInt(1),
                 		cursor.getString(2),
                 		cursor.getString(3),
                 		cursor.getFloat(4),
                 		cursor.getInt(5),
                 		cursor.getString(6),
                 		cursor.getFloat(7),
                 		cursor.getFloat(8),
                 		cursor.getString(9),
                 		cursor.getString(10),
                 		cursor.getInt(11),
                 		true);
  
                // Add book to books
                entryList.add(entry);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return entryList;
    }
	

}

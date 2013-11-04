package com.src.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBConfig {

	public final static String DBName = "Lemon.db";
	public final static int  DBVersion = 936;
	
	
	public static boolean tableIsExist(SQLiteDatabase db,   String tabName){
		
		 boolean result = false;
         if(tabName == null){
                 return false;
         }
         Cursor cursor = null;
         try {
               
                 String sql = "select count(*) as tbcount from sqlite_master where type ='table' and name ='"+tabName.trim()+"' ";
                 cursor = db.rawQuery(sql, null);
                 if(cursor.moveToNext()){
                         int count = cursor.getInt(0);
                         if(count>0){
                                 result = true;
                         }
                 }
                 
         } catch (Exception e) {
                 // TODO: handle exception
         }               
         return result;
        
	}
	
	
}

package gust.dblist;

/**
 * Created by mac on 18‏/12‏/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBAdapter {

    String dbName="myDB";
    String table="contact";

    String nameCol="name";
    String phoneCol="phone";
    int version =1;
    String creation="create table if not exists contact (name text, phone integer);";
    String drop= "drop table if exists contact;";
    MyDBHelper myhelper;
    SQLiteDatabase db;

    public DBAdapter(Context c){
        myhelper=new MyDBHelper(c);
    }

    public void open(){
        db=  myhelper.getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    public ArrayList<Contact> retreiveAll(){
        ArrayList<Contact> mycontacts=new ArrayList<Contact>();
        //i will fill this array from database




        Cursor c=db.query(table,//table name
                new String [] {nameCol,phoneCol},//the columns to return
                null,//The columns for the WHERE clause
                null,// The values for the WHERE clause
                null,// don't group the rows
                null,// don't filter by row groups
                null);// The sort order

        if( c.moveToFirst()==true){
            //i want you to get all names and numbers and fill mycontacts
            do {
                Contact con = new Contact(c.getString(0), c.getInt(1));
                mycontacts.add(con);
            }while(c.moveToNext());
        }

        return mycontacts;
    }

    public void add(Contact p){

        ContentValues record=new ContentValues();
        record.put(nameCol,p.name);
        record.put(phoneCol,p.number);


        db.insert(table,null,record);

        /*insert take 3 param
        table name,
        null// if the user enters empty value
        values

         */
    }






    public class MyDBHelper extends SQLiteOpenHelper {

        public MyDBHelper(Context context) {
            super(context, dbName, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(creation);
            db.execSQL("INSERT INTO contact VALUES('Ahmad', 33334444);");
            db.execSQL("INSERT INTO contact VALUES('Ali', 55556666);");
            db.execSQL("INSERT INTO contact VALUES('Suad', 11112222);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(drop);
            onCreate(db);
        }
    }

}

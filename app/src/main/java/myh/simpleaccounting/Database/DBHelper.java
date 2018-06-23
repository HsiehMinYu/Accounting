package myh.simpleaccounting.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import myh.simpleaccounting.Model.Record;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper{

    // If you change the database schema, you must increment the database version.
    private String TAG = "DBHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "account_db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Record.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS " + Record.TABLE_NAME);

        onCreate(db);
    }

    public boolean insertRecord(int money, String category, String note, String timestamp){

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Record.COLUMN_MONEY, money);
        values.put(Record.COLUMN_CATEGORY, category);
        values.put(Record.COLUMN_NOTE, note);
        values.put(Record.COLUMN_TIMESTAMP, timestamp);

        // Insert the new row, returning the primary key value of the new row
        long result = db.insert(Record.TABLE_NAME, null, values);

        db.close();
        //check the insert successful or not
        if(result == -1){
            return false ;
        }
        else{
            return true ;
        }
    }

    public ArrayList<Record> getbetweenRecord(String starttime, String endtime){
        ArrayList<Record> records = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ Record.TABLE_NAME +
                " where " + "date(" + Record.COLUMN_TIMESTAMP + ")" +
                " between ?  and ? order by date(" + Record.COLUMN_TIMESTAMP + ") desc", new String[]{starttime, endtime});

        if (cursor.moveToFirst()){
            do{
                Record record = new Record();

                record.setId(cursor.getInt(cursor.getColumnIndex(Record.COLUMN_ID)));
                record.setMoney(cursor.getInt(cursor.getColumnIndex(Record.COLUMN_MONEY)));
                record.setCategory(cursor.getString(cursor.getColumnIndex(Record.COLUMN_CATEGORY)));
                record.setNote(cursor.getString(cursor.getColumnIndex(Record.COLUMN_NOTE)));
                record.setTimestamp(cursor.getString(cursor.getColumnIndex(Record.COLUMN_TIMESTAMP)));

                records.add(record);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return records;
    }

    public ArrayList<Record> getRecord(String time){

        ArrayList<Record> records = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from accounting where timestamp = ? order by timestamp ", new String[]{time});

        if (cursor.moveToFirst()){
            do{
                Record record = new Record();

                record.setId(cursor.getInt(cursor.getColumnIndex(Record.COLUMN_ID)));
                record.setMoney(cursor.getInt(cursor.getColumnIndex(Record.COLUMN_MONEY)));
                record.setCategory(cursor.getString(cursor.getColumnIndex(Record.COLUMN_CATEGORY)));
                record.setNote(cursor.getString(cursor.getColumnIndex(Record.COLUMN_NOTE)));
                record.setTimestamp(cursor.getString(cursor.getColumnIndex(Record.COLUMN_TIMESTAMP)));

                records.add(record);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return records;
    }

    public Record getRecordbyid(String id){


        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from accounting where "+ Record.COLUMN_ID + " =? ", new String[]{id});

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Record record = new Record();

        record.setId(cursor.getInt(cursor.getColumnIndex(Record.COLUMN_ID)));
        record.setMoney(cursor.getInt(cursor.getColumnIndex(Record.COLUMN_MONEY)));
        record.setCategory(cursor.getString(cursor.getColumnIndex(Record.COLUMN_CATEGORY)));
        record.setNote(cursor.getString(cursor.getColumnIndex(Record.COLUMN_NOTE)));
        record.setTimestamp(cursor.getString(cursor.getColumnIndex(Record.COLUMN_TIMESTAMP)));


        cursor.close();
        return record;
    }


    public ArrayList<Record> getAllRecord(){

        ArrayList<Record> records = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from accounting order by date(timestamp)" , null);

        if (cursor.moveToFirst()){
            do{
                Record record = new Record();

                record.setId(cursor.getInt(cursor.getColumnIndex(Record.COLUMN_ID)));
                record.setMoney(cursor.getInt(cursor.getColumnIndex(Record.COLUMN_MONEY)));
                record.setCategory(cursor.getString(cursor.getColumnIndex(Record.COLUMN_CATEGORY)));
                record.setNote(cursor.getString(cursor.getColumnIndex(Record.COLUMN_NOTE)));
                record.setTimestamp(cursor.getString(cursor.getColumnIndex(Record.COLUMN_TIMESTAMP)));

                records.add(record);

            } while (cursor.moveToNext());
        }
        db.close();
        return records;
    }

    public int getRecordsCount(){

        String countQuery = "select  * from " + Record.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updateRecode(Record record){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Record.COLUMN_MONEY, record.getMoney());
        values.put(Record.COLUMN_CATEGORY, record.getCategory());
        values.put(Record.COLUMN_NOTE, record.getNote());

        return db.update(Record.TABLE_NAME, values,
                Record.COLUMN_ID + "=?",
                new String[]{String.valueOf(record.getId())});
    }

    public void deleteRecode(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Record.TABLE_NAME, Record.COLUMN_ID + "=?",
                new String[]{id});

        db.close();
    }
}

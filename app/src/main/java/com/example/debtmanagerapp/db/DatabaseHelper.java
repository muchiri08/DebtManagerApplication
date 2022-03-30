package com.example.debtmanagerapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.debtmanagerapp.model.DebtorModel;
import com.example.debtmanagerapp.model.UserModel;

import java.time.LocalDate;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DEBTORS = "Debtors";
    public static final String DEBT_ID = "id";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String AMOUNT = "amount";
    public static final String DATE_GIVEN = "dateGiven";
    public static final String DEADLINE_DATE = "deadlineDate";

    private static final String USER = "User";
    private static final String USERNAME = "username";
    private static final String MOBILE = "mobile";
    private static final String PASSWORD = "password";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DEBTORS + ".db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String tableDebtors = "CREATE TABLE " + DEBTORS + " (" + DEBT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FIRST_NAME + " TEXT, " + LAST_NAME + " TEXT, " + PHONE_NUMBER + " INT, " + AMOUNT + " INT, " + DATE_GIVEN + " DATE, " + DEADLINE_DATE + " DATE)";
        db.execSQL(tableDebtors);

        String tableUser = "CREATE TABLE " + USER + " (" + USERNAME + " TEXT PRIMARY KEY, " + MOBILE + " INT, " + PASSWORD + " TEXT)";
        db.execSQL(tableUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DEBTORS);
        db.execSQL("DROP TABLE IF EXISTS " + USER);
    }

    public boolean insertData(DebtorModel debtors){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIRST_NAME, debtors.getFirstName());
        contentValues.put(LAST_NAME, debtors.getLastName());
        contentValues.put(PHONE_NUMBER, debtors.getPhoneNumber());
        contentValues.put(AMOUNT, debtors.getAmount());
        contentValues.put(DATE_GIVEN, String.valueOf(debtors.getDateGiven()));
        contentValues.put(DEADLINE_DATE, String.valueOf(debtors.getDeadlineDate()));

        long debtorDetails = db.insert(DEBTORS, null,contentValues);

        if (debtorDetails == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public ArrayList<DebtorModel> getAllDebtors(){
        ArrayList<DebtorModel> debtors = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String result = "SELECT * FROM "+DEBTORS;
        Cursor cursor = db.rawQuery(result, null);

        int idc = cursor.getColumnIndex(DEBT_ID);
        int lnc = cursor.getColumnIndex(LAST_NAME);
        int fnc = cursor.getColumnIndex(FIRST_NAME);
        int ac = cursor.getColumnIndex(AMOUNT);
        int phc = cursor.getColumnIndex(PHONE_NUMBER);
        int dgc = cursor.getColumnIndex(DATE_GIVEN);
        int ddc = cursor.getColumnIndex(DEADLINE_DATE);


        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(idc);
                String lastName = cursor.getString(lnc);
                String firstName = cursor.getString(fnc);
                int amount = cursor.getInt(ac);
                int phone = cursor.getInt(phc);
                String dateGiven = cursor.getString(dgc);
                String deadlineDate = cursor.getString(ddc);

                DebtorModel debtorModel = new DebtorModel(id, firstName, lastName, phone, amount, LocalDate.parse(dateGiven), LocalDate.parse(deadlineDate));
                debtors.add(debtorModel);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return debtors;
    }

    public DebtorModel getDebtorById(int id){
        Log.d(TAG, "getDebtorById: Data retrieved");
       SQLiteDatabase db = getReadableDatabase();
       Cursor cursor = db.query(DEBTORS,
               new String[]{DEBT_ID, FIRST_NAME, LAST_NAME, PHONE_NUMBER, AMOUNT, DATE_GIVEN, DEADLINE_DATE},
               DEBT_ID + " = ?",
               new String[]{String.valueOf(id)}, null, null, null, null);

       if (cursor != null){
           cursor.moveToFirst();
       }


        int idd = cursor.getInt(cursor.getColumnIndex(DEBT_ID));
        String lastName = cursor.getString(cursor.getColumnIndex(LAST_NAME));
        String firstName = cursor.getString(cursor.getColumnIndex(FIRST_NAME));
        int amount = cursor.getInt(cursor.getColumnIndex(AMOUNT));
        int phone = cursor.getInt(cursor.getColumnIndex(PHONE_NUMBER));
        String dateGiven = cursor.getString(cursor.getColumnIndex(DATE_GIVEN));
        String deadlineDate = cursor.getString(cursor.getColumnIndex(DEADLINE_DATE));

        DebtorModel debtorModel = new DebtorModel(idd, firstName, lastName, phone, amount, LocalDate.parse(dateGiven), LocalDate.parse(deadlineDate));

        return debtorModel;
    }

    public int getSum(){
        int sum = 0;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT SUM("+AMOUNT+") AS "+AMOUNT+" FROM "+DEBTORS;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            sum = cursor.getInt(cursor.getColumnIndex(AMOUNT));
        }
        cursor.close();
        db.close();
        return sum;
    }




    //User logic part
    public boolean insertDataUser(UserModel user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USERNAME, user.getUsername());
        contentValues.put(MOBILE, user.getPhoneNumber());
        contentValues.put(PASSWORD, user.getPassword());

        long userDetails = db.insert(USER, null, contentValues);

        if (userDetails == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean checkUser(String username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+USER+ " WHERE "+USERNAME+" = ?", new String[]{username});

        if (cursor.getCount()>0){
            cursor.close();
            return true;
        }
        else {
            cursor.close();
            return false;
        }
    }

    public boolean checkUsernameAndPassword(String username, String password){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+USER+" WHERE "+USERNAME+" = ? AND "+PASSWORD+" = ?", new String[]{username, password});

        if (cursor.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }

}

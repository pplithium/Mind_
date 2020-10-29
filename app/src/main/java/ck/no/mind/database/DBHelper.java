package ck.no.mind.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static ck.no.mind.activities.AssesmentActivity.ASSESMENT1_TABLE;
import static ck.no.mind.activities.AssesmentActivity.NUMBER_OF_HOURS;

public class DBHelper extends SQLiteOpenHelper {

    private String DATABASE_NAME;
    // database coloumns
    public static final String[] ASSESMENT1_HOUR_COLOUMS = {
            "HOUR_0",
            "HOUR_1",
            "HOUR_2",
            "HOUR_3",
            "HOUR_4",
            "HOUR_5",
            "HOUR_6",
            "HOUR_7",
            "HOUR_8",
            "HOUR_9",
            "HOUR_10",
            "HOUR_11",
            "HOUR_12",
            "HOUR_13",
            "HOUR_14",
            "HOUR_15",
            "HOUR_16",
            "HOUR_17",
            "HOUR_18",
            "HOUR_19",
            "HOUR_20",
            "HOUR_21",
            "HOUR_22",
            "HOUR_23",
    };

    public DBHelper(Context context, String DATABASE_NAME) {
        super(context, DATABASE_NAME, null, 1);
        this.DATABASE_NAME = DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        if (DATABASE_NAME.equals(ASSESMENT1_TABLE)) {
            String databaseCommand = "create table " + DATABASE_NAME +  " " + "(id integer primary key, date text";

            for (String coloumn : ASSESMENT1_HOUR_COLOUMS) {
                databaseCommand += ", " + coloumn + " text";
            }

            databaseCommand += ")";
            sqLiteDatabase.execSQL(databaseCommand);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }


    public void insertAssesment1Data(String date, String[] data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_NAME, "date='" + date + "'", null);

        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);

        for (int i = 0 ; i < ASSESMENT1_HOUR_COLOUMS.length ; i++) {
            contentValues.put(ASSESMENT1_HOUR_COLOUMS[i], data[i]);
        }


        db.insert(DATABASE_NAME, null, contentValues);
        db.close();
    }

    public String[] getAssesment1Data(String date) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "select * from " + DATABASE_NAME + " where date='" + date + "'", null);

        if (res != null && res.moveToFirst()) {
            String[] data = new String[NUMBER_OF_HOURS];

            // first two coloumns contains the first elements
            for (int i = 0 ; i < ASSESMENT1_HOUR_COLOUMS.length; i++) {
                int index = res.getColumnIndex(ASSESMENT1_HOUR_COLOUMS[i]);

                if (index < 0) {
                    data[i] = "";
                } else {
                    data[i] = res.getString(index);
                }
            }

            res.close();
            db.close();
            return data;
        }

        res.close();
        db.close();
        return null;
    }
}

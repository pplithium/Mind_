package ck.no.mind.database;

import static ck.no.mind.activities.AssessmentActivity.ASSESMENT1_TABLE;
import static ck.no.mind.activities.AssessmentActivity.NUMBER_OF_HOURS;
import static ck.no.mind.activities.SecondAssessmentActivity.ASSESMENT2_TABLE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.HashMap;
import java.util.Map;

/**
 * Database Helper class for tracking daily routine, feelings, and cause of the feelings
 *
 */
public class DBHelper extends SQLiteOpenHelper {
    private String DATABASE_NAME;

    String date = "date";
    String happiness = "happiness";
    String ex = "ex";
    String sad = "sad";
    String anx = "anx";
    String ang = "ang";

    // database coloumns
    public static final String[] ASSESMENT1_HOUR_COLOUMS = {
            "HOUR_0",  "HOUR_1",  "HOUR_2",  "HOUR_3",  "HOUR_4",  "HOUR_5",  "HOUR_6",  "HOUR_7",
            "HOUR_8",  "HOUR_9",  "HOUR_10", "HOUR_11", "HOUR_12", "HOUR_13", "HOUR_14", "HOUR_15",
            "HOUR_16", "HOUR_17", "HOUR_18", "HOUR_19", "HOUR_20", "HOUR_21", "HOUR_22", "HOUR_23",
    };

    public DBHelper(Context context, String DATABASE_NAME) {
        super(context, DATABASE_NAME, null, 1);
        this.DATABASE_NAME = DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Assessment activity
        if (DATABASE_NAME.equals(ASSESMENT1_TABLE)) {
            String databaseCommand = "create table " + DATABASE_NAME + " "
                    + "(id integer primary key, date text";

            for (String coloumn : ASSESMENT1_HOUR_COLOUMS) {
                databaseCommand += ", " + coloumn + " text";
            }

            databaseCommand += ")";
            sqLiteDatabase.execSQL(databaseCommand);
        }

        // SecondAssessmentActivity
        if (DATABASE_NAME.equals(ASSESMENT2_TABLE)) {
            String databaseCommand = "create table " + DATABASE_NAME + " "
                    + "(id integer primary key, date text, happiness text, ex text, "
                    + "sad text, anx text, ang text)";

            sqLiteDatabase.execSQL(databaseCommand);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insertAssesment2Data(
            String date, String happiness, String ex, String sad, String anx, String ang) {
        SQLiteDatabase db = this.getWritableDatabase();

        // delete initial value for this row, it will be renewed
        db.delete(DATABASE_NAME, "date='" + date + "'", null);

        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("happiness", happiness);
        contentValues.put("ex", ex);
        contentValues.put("sad", sad);
        contentValues.put("anx", anx);
        contentValues.put("ang", ang);
        db.insert(DATABASE_NAME, null, contentValues);
        db.close();
    }

    public Map<String, Map<String, String>> getAllAssesment2Data() {
        Map<String, Map<String, String>> data = new HashMap<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from " + DATABASE_NAME, null);

        if (res != null && res.moveToFirst()) {
            String dateString = "";

            while (!res.isAfterLast()) {
                Map<String, String> row = new HashMap<>();

                int index = res.getColumnIndex(date);

                if (index >= 0) {
                    dateString = res.getString(index);
                }

                index = res.getColumnIndex(happiness);

                if (index < 0) {
                    row.put(happiness, "");
                } else {
                    row.put(happiness, res.getString(index));
                }

                index = res.getColumnIndex(ex);

                if (index < 0) {
                    row.put(ex, "");
                } else {
                    row.put(ex, res.getString(index));
                }

                index = res.getColumnIndex(sad);

                if (index < 0) {
                    row.put(sad, "");
                } else {
                    row.put(sad, res.getString(index));
                }

                index = res.getColumnIndex(anx);

                if (index < 0) {
                    row.put(anx, "");
                } else {
                    row.put(anx, res.getString(index));
                }

                index = res.getColumnIndex(ang);

                if (index < 0) {
                    row.put(ang, "");
                } else {
                    row.put(ang, res.getString(index));
                }

                data.put(dateString, row);

                res.moveToNext();
            }
        }

        res.close();
        db.close();

        return data;
    }

    public Map<String, String> getAssesment2Data(String date) {
        Map<String, String> data = new HashMap<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =
                db.rawQuery("select * from " + DATABASE_NAME + " where date='" + date + "'", null);

        if (res != null && res.moveToFirst()) {
            int index = res.getColumnIndex(happiness);

            if (index < 0) {
                data.put(happiness, "");
            } else {
                data.put(happiness, res.getString(index));
            }

            index = res.getColumnIndex(ex);

            if (index < 0) {
                data.put(ex, "");
            } else {
                data.put(ex, res.getString(index));
            }

            index = res.getColumnIndex(sad);

            if (index < 0) {
                data.put(sad, "");
            } else {
                data.put(sad, res.getString(index));
            }

            index = res.getColumnIndex(anx);

            if (index < 0) {
                data.put(anx, "");
            } else {
                data.put(anx, res.getString(index));
            }

            index = res.getColumnIndex(ang);

            if (index < 0) {
                data.put(ang, "");
            } else {
                data.put(ang, res.getString(index));
            }
        }

        return data;
    }

    public void insertAssesment1Data(String date, String[] data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_NAME, "date='" + date + "'", null);

        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);

        for (int i = 0; i < ASSESMENT1_HOUR_COLOUMS.length; i++) {
            contentValues.put(ASSESMENT1_HOUR_COLOUMS[i], data[i]);
        }

        db.insert(DATABASE_NAME, null, contentValues);
        db.close();
    }

    public String[] getAssesment1Data(String date) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =
                db.rawQuery("select * from " + DATABASE_NAME + " where date='" + date + "'", null);

        if (res != null && res.moveToFirst()) {
            String[] data = new String[NUMBER_OF_HOURS];

            // first two coloumns contains the first elements
            for (int i = 0; i < ASSESMENT1_HOUR_COLOUMS.length; i++) {
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
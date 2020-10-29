package ck.no.mind.activities;

import static ck.no.mind.database.DBHelper.ASSESMENT1_HOUR_COLOUMS;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import ck.no.mind.App;
import ck.no.mind.R;
import ck.no.mind.database.DBHelper;
import org.w3c.dom.Text;

public class AssesmentActivity extends AppCompatActivity {
    public static final String ASSESMENT1_TABLE = "ASSESMENT1_TABLE";
    public static final int NUMBER_OF_HOURS = 24;
    private static String dateAsString = null;
    DBHelper dbHelper;
    int year = -1;
    int month = -1;
    int dayOfMonth = -1;

    CalendarView calendarView;

    // editTexts by hour
    EditText editText8;
    EditText editText9;
    EditText editText10;
    EditText editText11;
    EditText editText12;
    EditText editText13;
    EditText editText14;
    EditText editText15;
    EditText editText16;
    EditText editText17;
    EditText editText18;
    EditText editText19;
    EditText editText20;
    EditText editText21;
    EditText editText22;
    EditText editText23;
    EditText editText0;
    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    EditText editText6;
    EditText editText7;

    EditText[] editTexts;

    boolean readOnly = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this, ASSESMENT1_TABLE);
        setContentView(R.layout.activity_assestment);
        initializeIntentData();
        initializeViews();
        initializeAllHistoricalData();
    }

    private void initializeIntentData() {
        Intent previousIntent = getIntent();
        year = previousIntent.getIntExtra("year", -1);
        month = previousIntent.getIntExtra("month", -1);
        dayOfMonth = previousIntent.getIntExtra("day", -1);
        readOnly = previousIntent.getBooleanExtra("read_only", false);
    }

    private void initializeViews() {
        dateAsString = "q" + dayOfMonth + month + year;

        if (year != -1) {
            TextView selectedDateText = findViewById(R.id.selected_date);
            String date = dayOfMonth + " - " + month + " - " + year;
            selectedDateText.setText(date);
        }

        editText0 = findViewById(R.id.edittext_hour0);
        editText1 = findViewById(R.id.edittext_hour1);
        editText2 = findViewById(R.id.edittext_hour2);
        editText3 = findViewById(R.id.edittext_hour3);
        editText4 = findViewById(R.id.edittext_hour4);
        editText5 = findViewById(R.id.edittext_hour5);
        editText6 = findViewById(R.id.edittext_hour6);
        editText7 = findViewById(R.id.edittext_hour7);
        editText8 = findViewById(R.id.edittext_hour8);
        editText9 = findViewById(R.id.edittext_hour9);
        editText10 = findViewById(R.id.edittext_hour10);
        editText11 = findViewById(R.id.edittext_hour11);
        editText12 = findViewById(R.id.edittext_hour12);
        editText13 = findViewById(R.id.edittext_hour13);
        editText14 = findViewById(R.id.edittext_hour14);
        editText15 = findViewById(R.id.edittext_hour15);
        editText16 = findViewById(R.id.edittext_hour16);
        editText17 = findViewById(R.id.edittext_hour17);
        editText18 = findViewById(R.id.edittext_hour18);
        editText19 = findViewById(R.id.edittext_hour19);
        editText20 = findViewById(R.id.edittext_hour20);
        editText21 = findViewById(R.id.edittext_hour21);
        editText22 = findViewById(R.id.edittext_hour22);
        editText23 = findViewById(R.id.edittext_hour23);
        editTexts = new EditText[NUMBER_OF_HOURS];

        editTexts[0] = editText0;
        editTexts[1] = editText1;
        editTexts[2] = editText2;
        editTexts[3] = editText3;
        editTexts[4] = editText4;
        editTexts[5] = editText5;
        editTexts[6] = editText6;
        editTexts[7] = editText7;
        editTexts[8] = editText8;
        editTexts[9] = editText9;
        editTexts[10] = editText10;
        editTexts[11] = editText11;
        editTexts[12] = editText12;
        editTexts[13] = editText13;
        editTexts[14] = editText14;
        editTexts[15] = editText15;
        editTexts[16] = editText16;
        editTexts[17] = editText17;
        editTexts[18] = editText18;
        editTexts[19] = editText19;
        editTexts[20] = editText20;
        editTexts[21] = editText21;
        editTexts[22] = editText22;
        editTexts[23] = editText23;

        if (readOnly) {
            for (EditText editText : editTexts) {
                editText.setFocusable(false);
                editText.setEnabled(false);
                editText.setCursorVisible(false);
                editText.setKeyListener(null);
                editText.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    private void initializeAllHistoricalData() {
        if (dateAsString == null) {
            App.showToast("Problem fetching date. Your data cannot be saved without the date.");
            return;
        }

        String[] data = dbHelper.getAssesment1Data(dateAsString);

        if (data == null) {
            return;
        }

        for (int i = 0; i < data.length; i++) {
            editTexts[i].setText(data[i]);
        }
    }

    private void saveAllData() {
        if (dateAsString == null) {
            App.showToast("Problem fetching date. Your data cannot be saved without the date.");
            return;
        }

        String[] data = new String[NUMBER_OF_HOURS];

        for (int i = 0; i < editTexts.length; i++) {
            data[i] = editTexts[i].getText().toString();
        }

        dbHelper.insertAssesment1Data(dateAsString, data);
    }

    private void launchSecondAssestment() {
        Intent intent = new Intent(this, SecondAssesmentActivity.class);
        intent.putExtra("date", dateAsString);
        intent.putExtra("read_only", readOnly);
        startActivity(intent);
        finish();
    }

    public void launchSecondAssestmentActivity(View v) {
        if (!readOnly) {
            saveAllData();
        }

        launchSecondAssestment();
    }
}
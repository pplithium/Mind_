package ck.no.mind.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RatingBar;

import java.util.Map;

import ck.no.mind.R;
import ck.no.mind.database.DBHelper;

public class SecondAssesmentActivity extends AppCompatActivity {

    public static final String ASSESMENT2_TABLE = "ASSESMENT2_TABLE";

    private String dateAsString;
    private boolean readOnly = false;
    DBHelper dbHelper;
    RatingBar ratingBarHappiness;
    RatingBar ratingBarEx;
    RatingBar ratingBarSad;
    RatingBar ratingBarAnx;
    RatingBar ratingBarAng;
    public String happiness = "happiness";
    public String ex = "ex";
    public String sad = "sad";
    public String anx = "anx";
    public String ang = "ang";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_assesment);
        dbHelper = new DBHelper(this, ASSESMENT2_TABLE);
        initializeViews();
        initializeData();
    }

    private void initializeViews() {
        ratingBarHappiness = findViewById(R.id.rating_happiness);
        ratingBarEx = findViewById(R.id.rating_excitement);
        ratingBarSad = findViewById(R.id.rating_sad);
        ratingBarAnx = findViewById(R.id.rating_anxious);
        ratingBarAng = findViewById(R.id.rating_angry);

        readOnly = getIntent().getBooleanExtra("read_only", false);
        if (readOnly) {
            ratingBarHappiness.setIsIndicator(true);
            ratingBarEx.setIsIndicator(true);
            ratingBarSad.setIsIndicator(true);
            ratingBarAnx.setIsIndicator(true);
            ratingBarAng.setIsIndicator(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!readOnly) {
            saveAllData(ratingBarHappiness.getRating(), ratingBarEx.getRating(),
                    ratingBarSad.getRating(), ratingBarAnx.getRating(), ratingBarAng.getRating());
        }
    }

    private void initializeData() {
        dateAsString = getIntent().getStringExtra("date");
        Map<String, String> data = dbHelper.getAssesment2Data(dateAsString);

        try {
            ratingBarHappiness.setRating(Float.parseFloat(data.get(happiness)));
            ratingBarEx.setRating(Float.parseFloat(data.get(ex)));
            ratingBarSad.setRating(Float.parseFloat(data.get(sad)));
            ratingBarAnx.setRating(Float.parseFloat(data.get(anx)));
            ratingBarAng.setRating(Float.parseFloat(data.get(ang)));
        } catch (Exception e) {
            // ignore
        }
    }

    private void saveAllData(float happiness, float exc, float sad, float anx, float ang) {
        dbHelper.insertAssesment2Data(dateAsString, String.valueOf(happiness),
                String.valueOf(exc), String.valueOf(sad), String.valueOf(anx), String.valueOf(ang));
    }

    public void finishAssesment(View v) {
        if (!readOnly) {
            saveAllData(ratingBarHappiness.getRating(), ratingBarEx.getRating(),
                    ratingBarSad.getRating(), ratingBarAnx.getRating(), ratingBarAng.getRating());
        }

        finish();
    }
}
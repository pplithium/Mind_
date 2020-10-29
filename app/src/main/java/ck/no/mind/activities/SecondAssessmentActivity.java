package ck.no.mind.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import ck.no.mind.R;
import ck.no.mind.database.DBHelper;
import ck.no.mind.helpers.window.MindWindowCallback;
import java.util.HashMap;
import java.util.Map;

/**
 * Activity to asses how happy the user felt during the day,
 * get more input regarding what made them feel that.
 */
public class SecondAssessmentActivity extends AppCompatActivity {
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
    Context context;

    Map<String, String> detailsData = new HashMap<>();
    Map<String, String> ratingData = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_assesment);
        context = this;
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

        Button happinessDetails = findViewById(R.id.happiness_button_details);
        setClickToRedirectDialog(happinessDetails, "happiness");

        Button exDetails = findViewById(R.id.ex_button_details);
        setClickToRedirectDialog(exDetails, "ex");

        Button sadDetails = findViewById(R.id.sad_button_details);
        setClickToRedirectDialog(sadDetails, "sad");

        Button anxDetails = findViewById(R.id.anx_button_details);
        setClickToRedirectDialog(anxDetails, "anx");

        Button angDetails = findViewById(R.id.ang_button_details);
        setClickToRedirectDialog(angDetails, "ang");

        getWindow().setCallback(new MindWindowCallback(this, getWindow().getCallback()));
    }

    // set click listener to details buttons for opening related dialog
    private void setClickToRedirectDialog(Button button, String type) {
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                // save data not to miss entered value after details button clicked
                // window callback might refresh this data after if it hasn't been saved
                saveAllData(
                        dateAsString, ratingBarHappiness.getRating(), ratingBarEx.getRating(),
                        ratingBarSad.getRating(), ratingBarAnx.getRating(),
                        ratingBarAng.getRating());

                final AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.details_dialog_layout, null);
                dialogBuilder.setView(dialogView);
                EditText editText = dialogView.findViewById(R.id.details_edittext);

                // set edittext to textview style if it is readOnly mode
                if (readOnly) {
                    editText.setFocusable(false);
                    editText.setEnabled(false);
                    editText.setCursorVisible(false);
                    editText.setKeyListener(null);
                    editText.setBackgroundColor(Color.TRANSPARENT);
                }

                Button okButton = dialogView.findViewById(R.id.all_ok_button);

                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                });
                dialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        saveDetailsData(editText.getText().toString(), type);
                    }
                });
                dialogBuilder.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        editText.setText(detailsData.get(type));
                    }
                });
                dialogBuilder.show();
            }
        });
    }

    // put the data into the global map, and save it to database
    public void saveDetailsData(String data, String type) {
        detailsData.put(type, data);
        saveDetailsData("details" + dateAsString);
    }

    // trigger refresh o the data from database,
    // this is done for dialog need to refresh itself for the new update after it's closed
    public void triggerDataRefresh() {
        initializeData();
    }

    // save the data even if the user did not press finish button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!readOnly) {
            saveAllData(
                    dateAsString, ratingBarHappiness.getRating(), ratingBarEx.getRating(),
                    ratingBarSad.getRating(), ratingBarAnx.getRating(), ratingBarAng.getRating());
        }
    }

    // initialize the data from database
    private void initializeData() {
        dateAsString = getIntent().getStringExtra("date");
        detailsData = dbHelper.getAssesment2Data("details" + dateAsString);
        ratingData = dbHelper.getAssesment2Data(dateAsString);
        try {
            ratingBarHappiness.setRating(Float.parseFloat(ratingData.get(happiness)));
            ratingBarEx.setRating(Float.parseFloat(ratingData.get(ex)));
            ratingBarSad.setRating(Float.parseFloat(ratingData.get(sad)));
            ratingBarAnx.setRating(Float.parseFloat(ratingData.get(anx)));
            ratingBarAng.setRating(Float.parseFloat(ratingData.get(ang)));
        } catch (Exception e) {
            // ignore
        }
    }

    private void saveAllData(
            String dateAsString, float happiness, float exc, float sad, float anx, float ang) {
        dbHelper.insertAssesment2Data(
                dateAsString, String.valueOf(happiness), String.valueOf(exc), String.valueOf(sad),
                String.valueOf(anx), String.valueOf(ang));
        saveDetailsData("details" + dateAsString);
    }

    private void saveDetailsData(String dateAsString) {
        String detailsHappiness = detailsData.get("happiness");
        String detailsEx = detailsData.get("ex");
        String detailsSad = detailsData.get("sad");
        String detailsAnx = detailsData.get("anx");
        String detailsAng = detailsData.get("ang");
        dbHelper.insertAssesment2Data(
                dateAsString, detailsHappiness, detailsEx, detailsSad, detailsAnx, detailsAng);
    }

    public void finishAssesment(View v) {
        if (!readOnly) {
            saveAllData(
                    dateAsString, ratingBarHappiness.getRating(), ratingBarEx.getRating(),
                    ratingBarSad.getRating(), ratingBarAnx.getRating(), ratingBarAng.getRating());
        }

        finish();
    }
}
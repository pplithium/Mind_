package ck.no.mind.activities;

import static ck.no.mind.activities.SecondAssessmentActivity.ASSESMENT2_TABLE;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import ck.no.mind.R;
import ck.no.mind.database.DBHelper;
import ck.no.mind.helpers.Triplet;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalysisActivity extends AppCompatActivity {
    GraphView emotionsGraph;
    CheckBox happinessCheckbox;
    CheckBox exCheckBox;
    CheckBox sadnessCheckbox;
    CheckBox anxCheckBox;
    CheckBox angCheckBox;
    DBHelper dbHelperAssesment2;

    TextView generalFeedbackText;

    String happiness = "happiness";
    String ex = "ex";
    String sad = "sad";
    String anx = "anx";
    String ang = "ang";
    List<String> types = Arrays.asList(happiness, ex, sad, anx, ang);

    PieChart pieChart;
    Map<String, Boolean> checkBoxesState = new HashMap<>();
    List<String> keysForLastWeek = new ArrayList<>();
    Map<String, Map<String, String>> allAssesment2Data = new HashMap<>();

    Map<String, Triplet<String, Integer, Float>> simpleAnalysisMap = new HashMap<>();

    Map<String, Integer> colors =
            Map.of(happiness, Color.GREEN, ex, Color.CYAN, sad, Color.YELLOW, anx, Color.GRAY, ang,
                    Color.RED);

    Map<String, String> legendEntries = Map.of(
            happiness, "Happiness", ex, "Excitement", sad, "Sadness", anx, "Anxiety", ang, "Anger");

    @Override
    protected void onResume() {
        super.onResume();
        updateGraphWithData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        dbHelperAssesment2 = new DBHelper(this, ASSESMENT2_TABLE);

        initializeData();
        initializeViews();
    }

    private void initializeViews() {
        parseTodaysDateAndCreateKeys();

        pieChart = findViewById(R.id.piechart);
        emotionsGraph = (GraphView) findViewById(R.id.emotions_graph);
        emotionsGraph.getViewport().setMinX(1);
        emotionsGraph.getViewport().setMaxX(7);
        emotionsGraph.getViewport().setMinY(0);
        emotionsGraph.getViewport().setMaxY(5);
        emotionsGraph.getViewport().setYAxisBoundsManual(true);
        emotionsGraph.getViewport().setXAxisBoundsManual(true);

        happinessCheckbox = findViewById(R.id.checkbox_happiness);
        exCheckBox = findViewById(R.id.checkbox_excitement);
        sadnessCheckbox = findViewById(R.id.checkbox_sadness);
        anxCheckBox = findViewById(R.id.checkbox_anx);
        angCheckBox = findViewById(R.id.checkbox_ang);

        setCheckListener(happinessCheckbox, happiness);
        setCheckListener(exCheckBox, ex);
        setCheckListener(sadnessCheckbox, sad);
        setCheckListener(anxCheckBox, anx);
        setCheckListener(angCheckBox, ang);

        updatePieChart();
    }

    // key format : "q" + dayOfMonth + month + year
    // today , and 6 more past days will be added to keys
    private void parseTodaysDateAndCreateKeys() {
        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);
        int currentMonth = c.get(Calendar.MONTH);
        int currentDayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        int month = currentMonth;
        int year = currentYear;

        if (currentDayOfMonth < 7) {
            month = currentMonth - 1;
            currentDayOfMonth += 30;
        }

        if (month == 0) {
            month = 12;
            year = currentYear - 1;
        }

        for (int i = 0; i < 7; i++) {
            int dayOfMonth = currentDayOfMonth - i;
            keysForLastWeek.add("q" + dayOfMonth + month + year);
        }
    }

    private void initializeData() {
        allAssesment2Data = dbHelperAssesment2.getAllAssesment2Data();
        simpleAnalysisOfData();
    }

    private void setCheckListener(CheckBox checkBox, String type) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                updateGraphWithData();
            }
        });
    }

    private void updateCheckBoxState() {
        checkBoxesState.put(happiness, happinessCheckbox.isChecked());
        checkBoxesState.put(ex, exCheckBox.isChecked());
        checkBoxesState.put(sad, sadnessCheckbox.isChecked());
        checkBoxesState.put(anx, anxCheckBox.isChecked());
        checkBoxesState.put(ang, angCheckBox.isChecked());
    }

    private Map<String, Float> calculateEmotionValues() {
        Map<String, Float> values = new HashMap<>();

        for (String type : types) {
            Float value = 0.0f;
            for (String key : keysForLastWeek) {
                Map<String, String> dataForADay = allAssesment2Data.get(key);
                if (dataForADay == null) {
                    continue;
                }
                String rating = dataForADay.get(type);
                if (rating == null) {
                    continue;
                }

                value = value + Float.parseFloat(rating);
            }
            values.put(type, value);
        }

        return values;
    }

    private void updatePieChart() {
        Map<String, Float> values = calculateEmotionValues();

        List<PieEntry> pieEntires = new ArrayList<>();

        for (String key : values.keySet()) {
            pieEntires.add(new PieEntry(values.get(key), legendEntries.get(key)));
        }

        PieDataSet dataSet = new PieDataSet(pieEntires, "");
        dataSet.setColors(new int[] {colors.get(happiness), colors.get(ex), colors.get(sad),
                colors.get(anx), colors.get(ang)});
        PieData data = new PieData(dataSet);
        // Get the chart
        pieChart.setData(data);

        pieChart.setEntryLabelTextSize(15);
        pieChart.setHoleRadius(75);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawSliceText(false);
        // legend attributes
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(10);
        legend.setFormSize(20);
        legend.setFormToTextSpace(2);
    }

    private void updateGraphWithData() {
        updateCheckBoxState();
        emotionsGraph.removeAllSeries();

        DataPoint[] dataPoints = new DataPoint[7];

        List<String> sortedKeys = keysForLastWeek.stream().sorted().collect(Collectors.toList());
        for (Map.Entry<String, Boolean> entry : checkBoxesState.entrySet()) {
            if (entry.getValue()) {
                String type = entry.getKey();
                int day = 0;
                int i = 0;
                for (String key : sortedKeys) {
                    Map<String, String> dataForADay = allAssesment2Data.get(key);
                    if (dataForADay == null) {
                        continue;
                    }
                    String rating = dataForADay.get(type);
                    if (rating == null) {
                        continue;
                    }

                    dataPoints[i] = new DataPoint(day, Float.parseFloat(rating));
                    i++;
                    day++;
                }

                boolean empty = true;
                for (Object ob : dataPoints) {
                    if (ob != null) {
                        empty = false;
                        break;
                    }
                }

                if (empty) {
                    return;
                }

                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);

                series.setColor(colors.get(type));
                emotionsGraph.addSeries(series);
            }
        }
    }

    private void simpleAnalysisOfData() {
        generalFeedbackText = findViewById(R.id.analysis_text);
        // clean-up and collect the data
        for (Map.Entry<String, Map<String, String>> entry : allAssesment2Data.entrySet()) {
            if (entry.getKey().startsWith("details")) {
                for (Map.Entry<String, String> innerEntry : entry.getValue().entrySet()) {
                    if (innerEntry.getValue() != null && !"".equals(innerEntry.getValue())) {
                        String activity = innerEntry.getValue();
                        String rating = allAssesment2Data.get(entry.getKey().replace("details", ""))
                                .get(innerEntry.getKey());
                        Triplet<String, Integer, Float> previousRating =
                                simpleAnalysisMap.get(activity);

                        if (previousRating != null) {
                            simpleAnalysisMap.remove(activity);
                            Triplet<String, Integer, Float> newRating =
                                    new Triplet<String, Integer, Float>(
                                            innerEntry.getKey(), previousRating.second + 1,
                                            previousRating.third + Float.valueOf(rating));
                            simpleAnalysisMap.put(activity, newRating);
                        } else {
                            Triplet<String, Integer, Float> newRating =
                                    new Triplet<String, Integer, Float>(
                                            innerEntry.getKey(), 1, Float.valueOf(rating));
                            simpleAnalysisMap.put(activity, newRating);
                        }
                    }
                }
            }
        }

        // compute and get average values
        // <activity , <emotion, rating>>
        Map<String, Pair<String, Float>> averageValues = new HashMap<>();
        for (Map.Entry<String, Triplet<String, Integer, Float>> entry :
                simpleAnalysisMap.entrySet()) {
            Float averageValue = entry.getValue().third / entry.getValue().second;
            Pair<String, Float> pair = new Pair<>(entry.getValue().first, averageValue);
            averageValues.put(entry.getKey(), pair);
        }

        // populate value to text
        for (Map.Entry<String, Pair<String, Float>> entry : averageValues.entrySet()) {
            if (entry.getValue().second > 2.5F) {
                String emotion;
                switch (entry.getValue().first) {
                    case "happiness":
                        emotion = "happy";
                        break;
                    case "ex":
                        emotion = "excited";
                        break;
                    case "sad":
                        emotion = "sad";
                        break;
                    case "anx":
                        emotion = "anxious";
                        break;
                    case "ang":
                        emotion = "angry";
                    default:
                        emotion = "happy";
                        break;
                }

                String addedText = "\n** " + entry.getKey() + " makes you " + emotion;
                generalFeedbackText.setText(generalFeedbackText.getText().toString() + addedText);
            }
        }

        generalFeedbackText.setText(
                generalFeedbackText.getText().toString()
                        + "\n\n Try to do more activities makes you happy and excited!");
    }
}
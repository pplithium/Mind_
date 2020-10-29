package ck.no.mind.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import ck.no.mind.R;
import com.airbnb.lottie.LottieAnimationView;

/**
 * This activity will use sensors to monitor user's heart rate.
 *
 * TODO: Build an overall graph of the data
 *
 */
public class HeartMonitorActivity extends AppCompatActivity implements SensorEventListener {
    final static int TOTAL_MEASUREMENT_COUNT = 1;
    static Sensor heartSensor;
    static SensorManager sensorManager;
    static SensorEventListener sensorEventListener;
    // Sensor related code
    static float HEART_RATE_TOTAL = 0;
    static float HEART_RATE_COUNT = 0;
    LottieAnimationView measurementProgress;
    TextView informationForSensor;
    ImageView informationImageForSensor;
    ImageView saveButton;
    ImageView reportButton;
    ImageView resetButton;
    TextView heartRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sensorEventListener = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_monitor);

        requestPermissionAndWait();
        setupSensor();
        setupViews();
        setupWatcherThread();
    }

    private void setupSensor() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        heartSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        sensorManager.registerListener(this, heartSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void launchMainActivity(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setupViews() {
        // supported or not
        if (heartSensor == null) {
            TextView notSupported = findViewById(R.id.no_support);
            notSupported.setVisibility(View.VISIBLE);

            Button button = findViewById(R.id.back_button);
            button.setVisibility(View.VISIBLE);

            measurementProgress = findViewById(R.id.heart_measurement_progress);
            measurementProgress.setAnimation(R.raw.heart_measurement_progress);
        } else {
            // progress bar for measurement
            measurementProgress = findViewById(R.id.heart_measurement_progress);
            measurementProgress.setAnimation(R.raw.heart_measurement_progress);
            measurementProgress.setMaxProgress(1);

            // guidance for sensor
            informationForSensor = findViewById(R.id.information_for_finger);
            informationForSensor.setVisibility(View.VISIBLE);
            informationImageForSensor = findViewById(R.id.image_information_for_finger);
            informationImageForSensor.setVisibility(View.VISIBLE);
            heartRate = findViewById(R.id.heart_rate);

            // save
            saveButton = findViewById(R.id.save_heart_rate);
            // report
            reportButton = findViewById(R.id.report);
            // reset
            resetButton = findViewById(R.id.reset);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, heartSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(
                            HeartMonitorActivity.this,
                            "You have denied the permission to measure "
                                    + "your heart rate",
                            Toast.LENGTH_SHORT)
                            .show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void requestPermissionAndWait() {
        ActivityCompat.requestPermissions(
                HeartMonitorActivity.this, new String[] {Manifest.permission.BODY_SENSORS}, 1);
    }

    // watch HEART_RATE_COUNT
    private void setupWatcherThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(350);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (HEART_RATE_COUNT >= TOTAL_MEASUREMENT_COUNT) {
                        setupLastButtonAnimationsAndStopSensor();
                        break;
                    }
                }
            }
        }).start();
    }

    private void setupLastButtonAnimationsAndStopSensor() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sensorManager.unregisterListener(sensorEventListener);

                // save heartrate
                saveButton.setVisibility(View.VISIBLE);

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveHeartrate();
                    }
                });

                // show analysis
                reportButton.setVisibility(View.VISIBLE);

                reportButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reportAllData();
                    }
                });

                // reset entire data
                resetButton.setVisibility(View.VISIBLE);

                resetButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        resetAllData();
                    }
                });
            }
        });
    }

    private void saveHeartrate() {
        // TODO
    }

    private void resetAllData() {
        // TODO
    }

    private void reportAllData() {
        // TODO
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_HEART_RATE && sensorEvent.values[0] != 0
                && HEART_RATE_COUNT <= TOTAL_MEASUREMENT_COUNT) {
            HEART_RATE_COUNT += 1;
            HEART_RATE_TOTAL += sensorEvent.values[0];
        }

        if (HEART_RATE_COUNT >= 1) {
            informationForSensor.setVisibility(View.GONE);
            informationImageForSensor.setVisibility(View.GONE);
            measurementProgress.setVisibility(View.VISIBLE);
            measurementProgress.setProgress(HEART_RATE_COUNT / TOTAL_MEASUREMENT_COUNT);
            heartRate.setVisibility(View.VISIBLE);
            heartRate.setText(String.format("%.1f", HEART_RATE_TOTAL / HEART_RATE_COUNT));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
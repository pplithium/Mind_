package ck.no.mind.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import ck.no.mind.App;
import ck.no.mind.R;


/**
 * main screen
 *
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        animateBackground();
    }

    // animation to give a calm atmosphere, background photo constantly zooming in and out slowly
    public void animateBackground() {
        final ImageView img = (ImageView) findViewById(R.id.main);
        final Animation aniSlideZoomOut =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out_background);
        final Animation aniSlideZoomIn =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in_background);
        img.startAnimation(aniSlideZoomOut);

        aniSlideZoomIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                img.startAnimation(aniSlideZoomOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        aniSlideZoomOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                img.startAnimation(aniSlideZoomIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public void launchPsycologistsActivity(View v) {
        Intent intent = new Intent(this, PsycologistsActivity.class);
        startActivity(intent);
    }

    public void launchHearthMonitor(View v) {
        Intent intent = new Intent(this, HeartMonitorActivity.class);
        startActivity(intent);
    }
    public void launchCalendarActivity(View v) {
        Intent intent = new Intent(this, HealthCalendarActivity.class);
        startActivity(intent);
    }
    public void launchAnalysisActivity(View v) {
        Intent intent = new Intent(this, AnalysisActivity.class);
        startActivity(intent);
    }
    public void launchTipsActivity(View v) {
        App.notImplementedCodeToast();
    }
    public void launchSleepActivity(View v) {
        App.notImplementedCodeToast();
    }
    public void launchTasksActivity(View v) {
        App.notImplementedCodeToast();
    }
    public void launchContactActivity(View v) {
        App.notImplementedCodeToast();
    }
    public void launchFriendsActivity(View v) {
        App.notImplementedCodeToast();
    }

    public void launchEventsActivity(View v) {
        Intent intent = new Intent(this, EventActivity.class);
        startActivity(intent);
    }
}
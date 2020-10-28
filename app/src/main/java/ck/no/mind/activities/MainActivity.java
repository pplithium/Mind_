package ck.no.mind.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import ck.no.mind.R;
import ck.no.mind.activities.HeartMonitorActivity;

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

    public void animateBackground() {
        final ImageView img = (ImageView)findViewById(R.id.main);
        final Animation aniSlideZoomOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_out_background);
        final Animation aniSlideZoomIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in_background);
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

    public void launchHearthMonitor(View v) {
        Intent intent = new Intent(this, HeartMonitorActivity.class);
        startActivity(intent);
    }

    public void launchEventsActivity(View v) {
        Intent intent = new Intent(this, EventActivity.class);
        startActivity(intent);
    }
}
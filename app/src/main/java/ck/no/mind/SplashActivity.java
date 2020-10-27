package ck.no.mind;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;

public
class SplashActivity extends AppCompatActivity {
    LottieAnimationView intro;
    private
    static float SPEED = 0.6F;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initiateAnimation();
    }

    private
    void initiateAnimation() {
        intro = findViewById(R.id.intro_anim);
        intro.setAnimation(R.raw.intro);
        intro.playAnimation();
        intro.setSpeed(SPEED);

        intro.addAnimatorListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animator) {
            }

            @Override public void onAnimationEnd(Animator animator) {
                initiateMainActivityAndFinish();
            }

            @Override public void onAnimationCancel(Animator animator) {
            }

            @Override public void onAnimationRepeat(Animator animator) {
            }
        });
    }

    private
    void initiateMainActivityAndFinish() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
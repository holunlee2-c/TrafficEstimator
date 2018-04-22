package ee5415gp9.com.trafficestimator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;


public class StartupActivity extends AppCompatActivity
{
    //TextView title;
    ImageView theme_startup;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.SplashTheme);
        setContentView(R.layout.startup);
        theme_startup = (ImageView) findViewById(R.id.theme_startup);
        animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        //title.setAnimation(animation);
        theme_startup.setAnimation(animation);
        animation.setDuration(1000);
        animation.setAnimationListener(new AnimationListener(){
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(StartupActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
    }
}
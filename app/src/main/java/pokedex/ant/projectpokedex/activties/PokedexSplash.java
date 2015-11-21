package pokedex.ant.projectpokedex.activties;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import pokedex.ant.projectpokedex.R;

/**
 * Created by Anthony on 2015/10/07.
 * splash screen
 */
public class PokedexSplash extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //Hide the action
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        performAnimation();
        CountDownTimer cdt = new CountDownTimer(5800,5800) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish()
            {
                Intent iMain = new Intent(getApplicationContext(), PokedexActivity.class);
                startActivity(iMain);
                finish();
            }
        }.start();
    }
    private void performAnimation() {
        // We will animate the imageview
        ImageView reusableImageView = (ImageView) findViewById(R.id.ImageViewForTweening);
        reusableImageView.setImageResource(R.drawable.poke_splash);
        reusableImageView.setVisibility(View.VISIBLE);

        // Load the appropriate animation
        Animation an = AnimationUtils.loadAnimation(this, R.anim.shakennotstirred);
        // Register a listener, so we can disable and re-enable buttons
        an.setAnimationListener(new MyAnimationListener());
        // Start the animation
        reusableImageView.startAnimation(an);
    }
    private class MyAnimationListener implements Animation.AnimationListener {

        public void onAnimationEnd(Animation animation) {
            // Hide our ImageView
            ImageView reusableImageView = (ImageView) findViewById(R.id.ImageViewForTweening);
            reusableImageView.setVisibility(View.INVISIBLE);

        }

        public void onAnimationRepeat(Animation animation) {
            // what to do when animation loops
        }

        public void onAnimationStart(Animation animation) {

        }
    }
}

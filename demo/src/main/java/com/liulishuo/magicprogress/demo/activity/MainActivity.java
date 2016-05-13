package com.liulishuo.magicprogress.demo.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.liulishuo.magicprogress.demo.R;
import com.liulishuo.magicprogresswidget.VerticalProgressBar;

import java.util.Random;

import cn.dreamtobe.percentsmoothhandler.ISmoothTarget;

/**
 * Created by Jacksgong on 12/10/15.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();

        anim();
    }

    private boolean isAnimActive;
    private final Random random = new Random();

    private void anim() {
        final int ceil = 26;
        final int progress = random.nextInt(ceil);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(demo1Mpb, "percent", 0, random.nextInt(ceil) / 100f)
        );
        set.setDuration(600);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimActive = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimActive = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.setInterpolator(new AccelerateInterpolator());
        set.start();
    }


    public void onReRandomPercent(final View view) {
        if (isAnimActive) {
            return;
        }
        anim();
    }

    public void onClickIncreaseSmoothly(final View view) {
        if (isAnimActive) {
            return;
        }

        // Just for demo smoothly process to the target percent in 3000ms duration.
        demo1Mpb.setSmoothPercent(getIncreasedPercent(demo1Mpb), 1000);
    }

    private float getIncreasedPercent(ISmoothTarget target) {
        float increasedPercent = target.getPercent() + 0.1f;

        return Math.min(1, increasedPercent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_github:
                openGitHub();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openGitHub() {
        Uri uri = Uri.parse(getString(R.string.app_github_url));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private VerticalProgressBar demo1Mpb;


    private void assignViews() {
        demo1Mpb = (VerticalProgressBar) findViewById(R.id.demo_1_mpb);
    }

}


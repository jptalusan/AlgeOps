package com.freelance.jptalusan.algeops.Activities;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freelance.jptalusan.algeops.LayoutWithSeekBarView;
import com.freelance.jptalusan.algeops.R;
import com.freelance.jptalusan.algeops.Utilities.Constants;
import com.freelance.jptalusan.algeops.Utilities.Equation;
import com.freelance.jptalusan.algeops.Utilities.EquationGeneration;

import me.grantland.widget.AutofitTextView;

//https://tausiq.wordpress.com/2013/01/18/android-base-activity-class-example/
//http://stackoverflow.com/questions/8821240/android-how-to-create-my-own-activity-and-extend-it
public class BaseOpsActivity extends AppCompatActivity {
    public Button startButton;
    public Button checkButton;

    public LinearLayout firstPartEq;
    public LinearLayout secondPartEq;

    protected String firstPart;
    protected String secondPart;

    public Equation eq;

    public boolean hasStarted = false;

    public ImageView operationImageView;

    private MediaPlayer mediaPlayer;

    protected LayoutWithSeekBarView xSeekbar;
    protected LayoutWithSeekBarView oneSeekbar;
    protected TextView xSeekbarImageView;
    protected TextView oneSeekbarImageView;

    protected boolean isFirstAnswerCorrect = false;
    protected boolean isSecondAnswerCorrect = false;

    protected SharedPreferences prefs;
    protected int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        prefs = getSharedPreferences(Constants.PREFS, MODE_PRIVATE);
    }

    protected void startAlgeOps() {
        hasStarted = true;
        eq = EquationGeneration.generateEquation("ADD", 0);
        firstPart = eq.getPart(1);
        secondPart = eq.getPart(2);

        //Add processing to decide what to place
//        firstPartEq.setText(firstPart);
//        secondPartEq.setText(secondPart);

        xSeekbarImageView.setTextColor(Color.BLACK);
        oneSeekbarImageView.setTextColor(Color.BLACK);

        startButton.setText("NEW");

        isFirstAnswerCorrect = false;
        isSecondAnswerCorrect = false;
    }

    protected void answerIsCorrect() {
        hasStarted = false;

        xSeekbar.setVisibility(View.VISIBLE);
        oneSeekbar.setVisibility(View.VISIBLE);

        xSeekbarImageView.setVisibility(View.VISIBLE);
        oneSeekbarImageView.setVisibility(View.VISIBLE);

        xSeekbarImageView.setTextColor(Color.BLACK);
        oneSeekbarImageView.setTextColor(Color.BLACK);
    }

    protected void answerIsWrong() {
        xSeekbar.setVisibility(View.GONE);
        oneSeekbar.setVisibility(View.GONE);

        xSeekbarImageView.setVisibility(View.GONE);
        oneSeekbarImageView.setVisibility(View.GONE);
    }

    public void playSound(int soundFile) {
        mediaPlayer = MediaPlayer.create(this, soundFile);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
    }

    protected void setEquationsLayout() {
        int[] first = eq.getIntArr(1);
        int[] second = eq.getIntArr(2);

        if (first[0] != 0) {
            AutofitTextView tv = new AutofitTextView(this);
            tv.setText(Integer.toString(Math.abs(first[0])));
            ImageView iv = new ImageView(this);
            if (first[0] > 0)
                iv.setImageResource(R.drawable.green_cube);
            else
                iv.setImageResource(R.drawable.red_cube);

            firstPartEq.addView(tv);
            firstPartEq.addView(iv);
        }

        if (first[1] != 0) {
            AutofitTextView tv = new AutofitTextView(this);
            tv.setText(Integer.toString(Math.abs(first[1])));
            ImageView iv = new ImageView(this);
            if (first[1] > 0)
                iv.setImageResource(R.drawable.green_circle);
            else
                iv.setImageResource(R.drawable.red_circle);

            firstPartEq.addView(tv);
            firstPartEq.addView(iv);
        }

        if (second[0] != 0) {
            AutofitTextView tv = new AutofitTextView(this);
            tv.setText(Integer.toString(Math.abs(second[0])));
            ImageView iv = new ImageView(this);
            if (second[0] > 0)
                iv.setImageResource(R.drawable.green_cube);
            else
                iv.setImageResource(R.drawable.red_cube);

            secondPartEq.addView(tv);
            secondPartEq.addView(iv);
        }

        if (second[1] != 0) {
            AutofitTextView tv = new AutofitTextView(this);
            tv.setText(Integer.toString(Math.abs(second[1])));
            ImageView iv = new ImageView(this);
            if (second[1] > 0)
                iv.setImageResource(R.drawable.green_circle);
            else
                iv.setImageResource(R.drawable.red_circle);

            secondPartEq.addView(tv);
            secondPartEq.addView(iv);
        }
    }
}

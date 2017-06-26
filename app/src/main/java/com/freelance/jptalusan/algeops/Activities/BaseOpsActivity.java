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
import com.freelance.jptalusan.algeops.Utilities.Constants;
import com.freelance.jptalusan.algeops.Utilities.Equation;
import com.freelance.jptalusan.algeops.Utilities.EquationGeneration;

//https://tausiq.wordpress.com/2013/01/18/android-base-activity-class-example/
//http://stackoverflow.com/questions/8821240/android-how-to-create-my-own-activity-and-extend-it
public class BaseOpsActivity extends AppCompatActivity {
    public Button startButton;
    public Button checkButton;
    public Button resetButton;

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
//        DEBUG
//        eq = new Equation(1, 0, 0, 0);
        firstPart = eq.getPart(1);
        secondPart = eq.getPart(2);

        xSeekbarImageView.setTextColor(Color.BLACK);
        oneSeekbarImageView.setTextColor(Color.BLACK);

        startButton.setText("RESET");
        checkButton.setVisibility(View.GONE);
        resetButton.setVisibility(View.GONE);

        isFirstAnswerCorrect = false;
        isSecondAnswerCorrect = false;
    }

    protected void answerIsCorrect() {
        hasStarted = false;

        checkButton.setVisibility(View.VISIBLE);
        resetButton.setVisibility(View.VISIBLE);

        xSeekbar.setVisibility(View.VISIBLE);
        oneSeekbar.setVisibility(View.VISIBLE);

        xSeekbarImageView.setVisibility(View.VISIBLE);
        oneSeekbarImageView.setVisibility(View.VISIBLE);

        xSeekbarImageView.setTextColor(Color.BLACK);
        oneSeekbarImageView.setTextColor(Color.BLACK);

        xSeekbar.getViewDimensions();
        oneSeekbar.getViewDimensions();
    }

    protected void reset() {
        hasStarted = true;
        isFirstAnswerCorrect = false;
        isSecondAnswerCorrect = false;

        xSeekbar.resetSeekBars();
        oneSeekbar.resetSeekBars();

        checkButton.setVisibility(View.GONE);
        resetButton.setVisibility(View.GONE);

        answerIsWrong();
    }

    protected void answerIsWrong() {
        checkButton.setVisibility(View.GONE);
        resetButton.setVisibility(View.GONE);

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
}

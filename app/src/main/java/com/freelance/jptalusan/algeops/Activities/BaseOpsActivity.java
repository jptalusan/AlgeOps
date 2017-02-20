package com.freelance.jptalusan.algeops.Activities;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelance.jptalusan.algeops.LayoutWithSeekBarView;
import com.freelance.jptalusan.algeops.R;
import com.freelance.jptalusan.algeops.Utilities.Equation;
import com.freelance.jptalusan.algeops.Utilities.EquationGeneration;

//https://tausiq.wordpress.com/2013/01/18/android-base-activity-class-example/
//http://stackoverflow.com/questions/8821240/android-how-to-create-my-own-activity-and-extend-it
public class BaseOpsActivity extends AppCompatActivity {
    public Button startButton;
    public Button checkButton;

    public TextView firstPartEq;
    public TextView secondPartEq;

    public String firstPart;
    public String secondPart;

    public Equation eq;

    public boolean hasStarted = false;

    public ImageView operationImageView;

    private MediaPlayer mediaPlayer;

    public LayoutWithSeekBarView xSeekbar;
    public LayoutWithSeekBarView oneSeekbar;
    public TextView xSeekbarImageView;
    public TextView oneSeekbarImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    protected void startAlgeOps() {
        hasStarted = true;
        eq = EquationGeneration.generateEquation("MAIN");
        firstPart = eq.getPart(1);
        secondPart = eq.getPart(2);

        firstPartEq.setText(firstPart);
        secondPartEq.setText(secondPart);

        xSeekbarImageView.setTextColor(Color.GREEN);
        oneSeekbarImageView.setTextColor(Color.GREEN);

        startButton.setText("NEW");
    }

    protected void answerIsCorrect() {
        //http://stackoverflow.com/questions/39831710/add-view-to-constraintlayout-with-constraints-similar-to-another-child
        ConstraintLayout.LayoutParams newQuestionLayoutParams = (ConstraintLayout.LayoutParams) startButton.getLayoutParams();
        newQuestionLayoutParams.leftToLeft = R.id.verticalFiftyPercent;
        newQuestionLayoutParams.leftMargin = 0;
        newQuestionLayoutParams.rightMargin = 0;
        startButton.setLayoutParams(newQuestionLayoutParams);

        checkButton.setVisibility(View.VISIBLE);
        hasStarted = false;

        xSeekbar.setVisibility(View.VISIBLE);
        oneSeekbar.setVisibility(View.VISIBLE);

        xSeekbarImageView.setVisibility(View.VISIBLE);
        oneSeekbarImageView.setVisibility(View.VISIBLE);
    }

    protected void answerIsWrong() {
        checkButton.setVisibility(View.GONE);
        //http://stackoverflow.com/questions/39831710/add-view-to-constraintlayout-with-constraints-similar-to-another-child
        ConstraintLayout.LayoutParams newQuestionLayoutParams = (ConstraintLayout.LayoutParams) startButton.getLayoutParams();
        newQuestionLayoutParams.leftToLeft = R.id.leftTwentyPercent;
        newQuestionLayoutParams.leftMargin = 0;
        newQuestionLayoutParams.rightMargin = 0;
        startButton.setLayoutParams(newQuestionLayoutParams);

        checkButton.setVisibility(View.GONE);
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

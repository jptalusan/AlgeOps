package com.freelance.jptalusan.algeops.Activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelance.jptalusan.algeops.AlgeOpsRelativeLayout;
import com.freelance.jptalusan.algeops.LayoutWithSeekBarView;
import com.freelance.jptalusan.algeops.R;
import com.freelance.jptalusan.algeops.Utilities.Constants;
import com.freelance.jptalusan.algeops.Utilities.LayoutUtilities;

import io.apptik.widget.MultiSlider;
import me.grantland.widget.AutofitTextView;

public class AddActivity extends BaseOpsActivity {
    private static final String TAG = "AddActivity";
    private ImageButton leftXAdd;
    private ImageButton leftXSub;
    private ImageButton rightXAdd;
    private ImageButton rightXSub;
    private ImageButton leftOneAdd;
    private ImageButton leftOneSub;
    private ImageButton rightOneAdd;
    private ImageButton rightOneSub;

    protected AlgeOpsRelativeLayout layoutRightX;
    protected AlgeOpsRelativeLayout layoutLeftX;
    protected AlgeOpsRelativeLayout layoutRightOne;
    protected AlgeOpsRelativeLayout layoutLeftOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        startButton     = (Button) findViewById(R.id.newQuestionButton);
        checkButton     = (Button) findViewById(R.id.checkButton);

        leftXAdd        = (ImageButton) findViewById(R.id.leftXAdd);
        leftXSub        = (ImageButton) findViewById(R.id.leftXSub);
        rightXAdd       = (ImageButton) findViewById(R.id.rightXAdd);
        rightXSub       = (ImageButton) findViewById(R.id.rightXSub);
        leftOneAdd      = (ImageButton) findViewById(R.id.leftOneAdd);
        leftOneSub      = (ImageButton) findViewById(R.id.leftOneSub);
        rightOneAdd     = (ImageButton) findViewById(R.id.rightOneAdd);
        rightOneSub     = (ImageButton) findViewById(R.id.rightOneSub);

        layoutLeftX     = (AlgeOpsRelativeLayout) findViewById(R.id.layoutLeftX);
        layoutRightX    = (AlgeOpsRelativeLayout) findViewById(R.id.layoutRightX);
        layoutLeftOne   = (AlgeOpsRelativeLayout) findViewById(R.id.layoutLeftOne);
        layoutRightOne  = (AlgeOpsRelativeLayout) findViewById(R.id.layoutRightOne);

        firstPartEq     = (AutofitTextView) findViewById(R.id.firstEqTextView);
        secondPartEq    = (AutofitTextView) findViewById(R.id.secondEqTextView);

        xSeekbar        = (LayoutWithSeekBarView) findViewById(R.id.xSeekBar);
        oneSeekbar      = (LayoutWithSeekBarView) findViewById(R.id.oneSeekBar);

        //1 thumb
        xSeekbar.seekBar.setOnThumbValueChangeListener(new MultiSlider.SimpleChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                Log.d(TAG, "thumb " + thumbIndex + ":" + value);
                xSeekbar.removeAllViewsInRelativeLayout();
                xSeekbar.setUserAnswer(value);
                xSeekbar.drawValuesInRelativeLayout(value, false);
                xSeekbarImageView.setText(Integer.toString(value));
                if (value > 0)
                    xSeekbarImageView.setTextColor(Color.GREEN);
                else if (value < 0)
                    xSeekbarImageView.setTextColor(Color.RED);
                else
                    xSeekbarImageView.setTextColor(Color.BLACK);
            }
        });

        oneSeekbar.seekBar.setOnThumbValueChangeListener(new MultiSlider.SimpleChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                Log.d(TAG, "thumb " + thumbIndex + ":" + value);
                oneSeekbar.removeAllViewsInRelativeLayout();
                oneSeekbar.setUserAnswer(value);
                oneSeekbar.drawValuesInRelativeLayout(value, false);
                oneSeekbarImageView.setText(Integer.toString(value));
                if (value > 0)
                    oneSeekbarImageView.setTextColor(Color.GREEN);
                else if (value < 0)
                    oneSeekbarImageView.setTextColor(Color.RED);
                else
                    oneSeekbarImageView.setTextColor(Color.BLACK);
            }
        });

        xSeekbarImageView   = (TextView) findViewById(R.id.xSeekBarImage);
        oneSeekbarImageView = (TextView) findViewById(R.id.oneSeekBarImage);

        operationImageView = (ImageView) findViewById(R.id.operationImageView);
        operationImageView.setImageResource(R.drawable.plus);

        startAlgeOps();
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAlgeOps();
            }
        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (!isFirstAnswerCorrect) {
                boolean isCorrect = eq.isAdditionAnswerCorrect(layoutLeftX.currXVal,
                        layoutLeftOne.currOneVal,
                        layoutRightX.currXVal,
                        layoutRightOne.currOneVal);

                Log.d(TAG, "Check if 1st is correct: " + isCorrect);
                if (isCorrect) {
                    isFirstAnswerCorrect = true;
                    xSeekbar.getViewDimensions();
                    oneSeekbar.getViewDimensions();
                    xSeekbar.drawNumbersinRelativeLayout();
                    oneSeekbar.drawNumbersinRelativeLayout();
                    answerIsCorrect();
                    cancelOutViews();
                } else {
                    answerIsWrong();
                }
            } else if (!isSecondAnswerCorrect){
                Log.d(TAG, "First ans correct.");
                if (isSeekBarAnswerCorrect()) {
                    isSecondAnswerCorrect = true;
                } else {
                    playSound(R.raw.wrong);
                }
            }
            }
        });

        answerIsWrong();

        layoutLeftX.getViewDimensions();
        layoutRightX.getViewDimensions();
        layoutLeftOne.getViewDimensions();
        layoutRightOne.getViewDimensions();

        leftXAdd.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_ADD_X, layoutLeftX));
        leftXSub.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB_X, layoutLeftX));

        rightXAdd.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_ADD_X, layoutRightX));
        rightXSub.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB_X, layoutRightX));

        leftOneAdd.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_ADD_ONE, layoutLeftOne));
        leftOneSub.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB_ONE, layoutLeftOne));

        rightOneAdd.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_ADD_ONE, layoutRightOne));
        rightOneSub.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB_ONE, layoutRightOne));
    }

    private void cancelOutViews() {
        int countX = LayoutUtilities.getNumberOfViewsToRemove(layoutLeftX, layoutRightX, Constants.OPS_X);
        Log.d(TAG, "Cancelling out X: " + countX);
        Handler handler1 = new Handler();
        for (int i = 0; i < countX; ++i) {
            handler1.postDelayed(new Runnable() {

                @Override
                public void run() {
                    layoutLeftX.removeImage(layoutLeftX.getObjectTypeInside());
                    layoutRightX.removeImage(layoutRightX.getObjectTypeInside());
                }
            }, 1000 * i);
        }

        int countOne = LayoutUtilities.getNumberOfViewsToRemove(layoutLeftOne, layoutRightOne, Constants.OPS_ONE);
        Log.d(TAG, "Cancelling out 1: " + countOne);
        for (int i = 0; i < countOne; ++i) {
            handler1.postDelayed(new Runnable() {

                @Override
                public void run() {
                    layoutLeftOne.removeImage(layoutLeftOne.getObjectTypeInside());
                    layoutRightOne.removeImage(layoutRightOne.getObjectTypeInside());
                }
            }, 1500 * i);
        }
    }

    private boolean isSeekBarAnswerCorrect() {
        Log.d("Seekbar", "corrX:" + (eq.getAx() + eq.getCx()) + "");
        Log.d("Seekbar", "corr1:" + (eq.getB() + eq.getD()) + "");

        int xCorrectAnswer = eq.getAx() + eq.getCx();
        int oneCorrectAnswer = eq.getB() + eq.getD();

        if (xSeekbar.getUserAnswer() == xCorrectAnswer &&
                oneSeekbar.getUserAnswer() == oneCorrectAnswer) {
            playSound(R.raw.correct);
            Log.d(TAG, "correct");
            xSeekbar.seekBar.setEnabled(false);
            oneSeekbar.seekBar.setEnabled(false);
            return true;
        } else {
            playSound(R.raw.wrong);
            if (xSeekbar.getUserAnswer() != xCorrectAnswer) {
                xSeekbar.setCorrectAnswer(xCorrectAnswer);
                xSeekbar.answerIsIncorrect();
                xSeekbarImageView.setText(Integer.toString(xCorrectAnswer));
                if (xCorrectAnswer > 0)
                    xSeekbarImageView.setTextColor(Color.GREEN);
                else
                    xSeekbarImageView.setTextColor(Color.RED);
            }

            if (oneSeekbar.getUserAnswer() != oneCorrectAnswer) {
                oneSeekbar.setCorrectAnswer(oneCorrectAnswer);
                oneSeekbar.answerIsIncorrect();
                oneSeekbarImageView.setText(Integer.toString(oneCorrectAnswer));
                if (oneCorrectAnswer > 0)
                    oneSeekbarImageView.setTextColor(Color.GREEN);
                else
                    oneSeekbarImageView.setTextColor(Color.RED);
            }

            return false;
        }
    }

    protected void startAlgeOps() {
        super.startAlgeOps();

        layoutLeftX.resetLayout();
        layoutRightX.resetLayout();
        layoutLeftOne.resetLayout();
        layoutRightOne.resetLayout();

        xSeekbar.resetSeekBars();
        oneSeekbar.resetSeekBars();

        answerIsWrong();
    }

    public class AlgeOpsButtonsOnClickListener implements View.OnClickListener {
        private static final String TAG = "Add:ClickListener";
        private Context mContext;
        private int mOperation;
        private AlgeOpsRelativeLayout mView;

        AlgeOpsButtonsOnClickListener(Context context, int operation, AlgeOpsRelativeLayout view) {
            mContext = context;
            mOperation = operation;
            mView = view;
        }

        @Override
        public void onClick(View view) {
            if (hasStarted) {
                if (mView.setImage(mContext, mOperation)) {
                    playSound(R.raw.correct);
                } else {
                    playSound(R.raw.wrong);
                }
            }
        }
    }
}

package com.freelance.jptalusan.algeops.Activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.freelance.jptalusan.algeops.AlgeOpsRelativeLayout;
import com.freelance.jptalusan.algeops.LayoutWithSeekBarView;
import com.freelance.jptalusan.algeops.R;
import com.freelance.jptalusan.algeops.Utilities.AutoResizeTextView;
import com.freelance.jptalusan.algeops.Utilities.Constants;
import com.freelance.jptalusan.algeops.Utilities.EquationGeneration;
import com.freelance.jptalusan.algeops.Utilities.LayoutUtilities;

import io.apptik.widget.MultiSlider;

public class SubtractActivity extends BaseOpsActivity {
    private static final String TAG = "SubActivity";
    private ImageView addXImageView;
    private ImageView subXImageView;
    private ImageView addOneImageView;
    private ImageView subOneImageView;
    private ImageView addsubXImageView;
    private ImageView addsubOneImageView;

    private ImageButton subPosXButton;
    private ImageButton subNegXButton;
    private ImageButton addPosNegXButton;
    private ImageButton subPosOneButton;
    private ImageButton subNegOneButton;
    private ImageButton addPosNegOneButton;

    private AlgeOpsRelativeLayout subLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtract);

        prefs.edit().putInt(Constants.SUB_LEVEL, Constants.LEVEL_4).apply();

        if (prefs.getBoolean(Constants.IS_FIRST_RUN_SUB, true)) {
            prefs.edit().putBoolean(Constants.IS_FIRST_RUN_SUB, false).apply();
            prefs.edit().putInt(Constants.SUB_LEVEL, Constants.LEVEL_1).apply();
            prefs.edit().putInt(Constants.CORRECT_SUB_ANSWERS, 0).apply();
        }
        level = prefs.getInt(Constants.SUB_LEVEL, Constants.LEVEL_1);

        startButton = (Button) findViewById(R.id.startButton);
        checkButton = (Button) findViewById(R.id.checkButton);

        firstPartEq = (LinearLayout) findViewById(R.id.firstEqTextView);
        secondPartEq = (LinearLayout) findViewById(R.id.secondEqTextView);

        addXImageView = (ImageView) findViewById(R.id.addXImageView);
        subXImageView = (ImageView) findViewById(R.id.subXImageView);
        addOneImageView = (ImageView) findViewById(R.id.addOneImageView);
        subOneImageView = (ImageView) findViewById(R.id.subOneImageView);
        addsubXImageView = (ImageView) findViewById(R.id.addsubXImageView);
        addsubOneImageView = (ImageView) findViewById(R.id.addsubOneImageView);

        xSeekbarImageView = (TextView) findViewById(R.id.xSeekbarImageView);
        oneSeekbarImageView = (TextView) findViewById(R.id.oneSeekbarImageView);

        operationImageView = (ImageView) findViewById(R.id.operationImageView);
        operationImageView.setImageResource(R.drawable.minus);

        subPosXButton = (ImageButton) findViewById(R.id.subPosXButton);
        subNegXButton = (ImageButton) findViewById(R.id.subNegXButton);
        addPosNegXButton = (ImageButton) findViewById(R.id.addPosNegXButton);

        subPosOneButton = (ImageButton) findViewById(R.id.subPosOneButton);
        subNegOneButton = (ImageButton) findViewById(R.id.subNegOneButton);
        addPosNegOneButton = (ImageButton) findViewById(R.id.addPosNegOneButton);

        subLayout = (AlgeOpsRelativeLayout) findViewById(R.id.subAlgeOpsRelLayout);

        xSeekbar = (LayoutWithSeekBarView) findViewById(R.id.subXSeekBar);
        oneSeekbar = (LayoutWithSeekBarView) findViewById(R.id.subOneSeekBar);

        xSeekbar.seekBar.setOnThumbValueChangeListener(new MultiSlider.SimpleChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
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

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAlgeOps();
            }
        });

        //TODO: Add level up mechanics
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "checkButton clicked");
                int correctAnswers = prefs.getInt(Constants.CORRECT_SUB_ANSWERS, 0);
                if (!isFirstAnswerCorrect) {
                    //answerX = initx + -1(posX - negX)
                    //answer1 = init1 + -1(pos1 - neg1)
                    int answX = subLayout.positiveX - subLayout.negativeX;
                    int answ1 = subLayout.positiveOne - subLayout.negativeOne;
                    boolean isCorrect = eq.isSubtractAnswerCorrect(answX, answ1);

                    if (isCorrect) {
                        isFirstAnswerCorrect = true;
                        xSeekbar.getViewDimensions();
                        oneSeekbar.getViewDimensions();
                        answerIsCorrect();
                        cancelOutViews();
                    } else {
                        if (correctAnswers != Constants.LEVEL_UP) {
                            correctAnswers = 0;
                            prefs.edit().putInt(Constants.CORRECT_SUB_ANSWERS, correctAnswers).apply();
                            Log.d(TAG, "Back to start: " + correctAnswers);
                        }
                        answerIsWrong();
                        playSound(R.raw.wrong);
                    }
                } else if (!isSecondAnswerCorrect) {
                    Log.d(TAG, "First ans correct.");
                    if (isSeekBarAnswerCorrect()) {
                        isSecondAnswerCorrect = true;
                        //Count if the user has 10 consecutive answers (4 levels)
                        correctAnswers++;
                        int currLevel = prefs.getInt(Constants.SUB_LEVEL, 1);
                        if (correctAnswers == Constants.LEVEL_UP && currLevel != Constants.LEVEL_4) {
                            currLevel++;
                            Toast.makeText(SubtractActivity.this, "Congratulations! You are now in Level " + currLevel, Toast.LENGTH_SHORT).show();
                            prefs.edit().putInt(Constants.SUB_LEVEL, currLevel).apply();
                            prefs.edit().putInt(Constants.CORRECT_SUB_ANSWERS, 0).apply();
                        } else {
                            prefs.edit().putInt(Constants.CORRECT_SUB_ANSWERS, correctAnswers).apply();
                        }
                        Log.d(TAG, "Correct: " + correctAnswers);
                    } else {
                        playSound(R.raw.wrong);
                        if (correctAnswers != Constants.LEVEL_UP) {
                            correctAnswers = 0;
                            prefs.edit().putInt(Constants.CORRECT_ADD_ANSWERS, correctAnswers).apply();
                            Log.d(TAG, "Back to start: " + correctAnswers);
                        }
                    }
                }
            }
        });

        answerIsWrong();

        subPosXButton.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB_POS_X, subLayout));
        subNegXButton.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB_NEG_X, subLayout));
        addPosNegXButton.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_ADD_POS_NEG_X, subLayout));

        subPosOneButton.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB_POS_ONE, subLayout));
        subNegOneButton.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB_NEG_ONE, subLayout));
        addPosNegOneButton.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_ADD_POS_NEG_ONE, subLayout));

        subLayout.getViewDimensions();
        ViewTreeObserver vto = subLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                subLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                startAlgeOps();
            }
        });
    }

    //TODO: Do i also have to cancel out the initial views? or only added views?
    private void cancelOutViews() {
        Handler handler1 = new Handler();
        int countX = LayoutUtilities.getNumberOfViewsToRemove(subLayout, Constants.OPS_X);
        Log.d(TAG, "Cancelling out X: " + countX);
        for (int i = 0; i < countX; i++) {
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    subLayout.removeSubImage(Constants.POS_X);
                    subLayout.removeSubImage(Constants.NEG_X);
                }
            }, 1600 * i);
        }

        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                Handler handler3 = new Handler();
                int count1 = LayoutUtilities.getNumberOfViewsToRemove(subLayout, Constants.OPS_ONE);
                Log.d(TAG, "Cancelling out 1: " + count1);
                for (int i = 0; i < count1; i++) {
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            subLayout.removeSubImage(Constants.POS_ONE);
                            subLayout.removeSubImage(Constants.NEG_ONE);
                        }
                    }, 1600 * i);
                }
            }
        }, countX * 1600);

    }

    private boolean isSeekBarAnswerCorrect() {
        Log.d("Seekbar", "corrX:" + (eq.getAx() - eq.getCx()) + "");
        Log.d("Seekbar", "corr1:" + (eq.getB() - eq.getD()) + "");

        int xCorrectAnswer = eq.getAx() - eq.getCx();
        int oneCorrectAnswer = eq.getB() - eq.getD();

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

        firstPartEq.removeAllViews();
        secondPartEq.removeAllViews();

        int subLevel = prefs.getInt(Constants.SUB_LEVEL, 1);
        eq = EquationGeneration.generateEquation("SUB", subLevel);
        firstPart = eq.getPart(1);
        secondPart = eq.getPart(2);

        if (subLevel == Constants.LEVEL_1) {
            setEquationsLayout();
        } else if (subLevel == Constants.LEVEL_2) {
            AutoResizeTextView tv1 = new AutoResizeTextView(this);
            //tv1.setText(" from, " + firstPart);
            AutoResizeTextView tv2 = new AutoResizeTextView(this);
            tv2.setText("Remove " + secondPart + ", from " + firstPart);

            firstPartEq.addView(tv1);
            secondPartEq.addView(tv2);
        } else if (subLevel == Constants.LEVEL_3) {
            setEquationsLayout();
        } else {
            AutoResizeTextView tv1 = new AutoResizeTextView(this);
            //tv1.setText(" from, " + firstPart);
            AutoResizeTextView tv2 = new AutoResizeTextView(this);
            tv2.setText("Remove " + secondPart + ", from " + firstPart);

            firstPartEq.addView(tv1);
            secondPartEq.addView(tv2);
        }

        subLayout.resetLayout();
        xSeekbar.resetSeekBars();
        oneSeekbar.resetSeekBars();
        answerIsWrong();

//        subLayout.populateImageViewBasedOnEq(SubtractActivity.this, eq);
        subLayout.populateImageViewBasedOnLeftSideEq(SubtractActivity.this, eq);
    }

    @Override
    protected void setEquationsLayout() {
        super.setEquationsLayout();
        TextView tv = (TextView) secondPartEq.getChildAt(0);
        tv.setGravity(Gravity.START);
        tv.setText("Remove: " + tv.getText() + " from:");

        TextView tv2 = (TextView) firstPartEq.getChildAt(0);
        tv.setGravity(Gravity.START);
        tv2.setVisibility(View.GONE);
        firstPartEq.setVisibility(View.GONE);
//        tv2.setText(" , from: " + tv2.getText());
    }

    public class AlgeOpsButtonsOnClickListener implements View.OnClickListener {
        private static final String TAG = "Sub:ClickListener";
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
            Log.d(TAG, "OnClick");
            if (hasStarted) {
                if (prefs.getInt(Constants.SUB_LEVEL, 1) < Constants.LEVEL_3) {
                    if (view.getId() == R.id.addPosNegOneButton || view.getId() == R.id.addPosNegXButton)
                    {
                        playSound(R.raw.wrong);
                        return;
                    }
                    if (mView.setSubImage(mContext, mOperation, eq)) {
                        playSound(R.raw.correct);
                    } else {
                        playSound(R.raw.wrong);
                    }
                } else {
                    if (mView.setSubImage(mContext, mOperation)) {
                        playSound(R.raw.correct);
                    } else {
                        playSound(R.raw.wrong);
                    }

                }
            }
        }
    }
}

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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.freelance.jptalusan.algeops.AlgeOpsRelativeLayout;
import com.freelance.jptalusan.algeops.LayoutWithSeekBarView;
import com.freelance.jptalusan.algeops.R;
import com.freelance.jptalusan.algeops.Utilities.Constants;
import com.freelance.jptalusan.algeops.Utilities.EquationGeneration;
import com.freelance.jptalusan.algeops.Utilities.LayoutUtilities;

import java.util.ArrayList;
import java.util.List;

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

    private int countX = 0;
    private int count1 = 0;
    private int animatedCounter = 0;
    private boolean hasStartedAnimationX = false;
    private boolean hasStartedAnimation1 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtract);

        if (prefs.getBoolean(Constants.IS_FIRST_RUN_SUB, true)) {
            prefs.edit().putBoolean(Constants.IS_FIRST_RUN_SUB, false).apply();
            prefs.edit().putInt(Constants.SUB_LEVEL, Constants.LEVEL_1).apply();
            prefs.edit().putInt(Constants.CORRECT_SUB_ANSWERS, 0).apply();
        }
        level = prefs.getInt(Constants.SUB_LEVEL, Constants.LEVEL_1);

        startButton = (Button) findViewById(R.id.startButton);
        checkButton = (Button) findViewById(R.id.checkButton);
        resetButton = (Button) findViewById(R.id.resetButton);

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

        ArrayList<String> points = new ArrayList<>();
        points.add("-10");
        points.add("-9");
        points.add("-8");
        points.add("-7");
        points.add("-6");
        points.add("-5");
        points.add("-4");
        points.add("-3");
        points.add("-2");
        points.add("-1");
        points.add(" 0");
        points.add(" 1");
        points.add(" 2");
        points.add(" 3");
        points.add(" 4");
        points.add(" 5");
        points.add(" 6");
        points.add(" 7");
        points.add(" 8");
        points.add(" 9");
        points.add("10");

        xSeekbar.seekBar.setMax(20);
        xSeekbar.setValues(points);
        xSeekbar.getViewDimensions();
        xSeekbar.seekBar.setProgress(10);
        xSeekbar.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                xSeekbar.removeAllViewsInRelativeLayout();
                xSeekbar.setUserAnswer(progress - 10);
                xSeekbar.drawValuesInRelativeLayout(progress - 10, false);
                if (progress != 21) {
                    xSeekbarImageView.setText(Integer.toString(progress - 10));
                    if (progress - 10 > 0)
                        xSeekbarImageView.setTextColor(Color.GREEN);
                    else if (progress - 10 < 0)
                        xSeekbarImageView.setTextColor(Color.RED);
                    else
                        xSeekbarImageView.setTextColor(Color.BLACK);
                } else if (progress == 21) {
                    xSeekbarImageView.setText(Integer.toString(10));
                    xSeekbarImageView.setTextColor(Color.GREEN);
                }
            }
        });

        oneSeekbar.seekBar.setMax(20);
        oneSeekbar.setValues(points);
        oneSeekbar.getViewDimensions();
        oneSeekbar.seekBar.setProgress(10);
        oneSeekbar.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                oneSeekbar.removeAllViewsInRelativeLayout();
                oneSeekbar.setUserAnswer(progress - 10);
                oneSeekbar.drawValuesInRelativeLayout(progress - 10, false);
                if (progress != 21) {
                    oneSeekbarImageView.setText(Integer.toString(progress - 10));
                    if (progress - 10 > 0)
                        oneSeekbarImageView.setTextColor(Color.GREEN);
                    else if (progress - 10 < 0)
                        oneSeekbarImageView.setTextColor(Color.RED);
                    else
                        oneSeekbarImageView.setTextColor(Color.BLACK);
                } else if (progress == 21) {
                    xSeekbarImageView.setText(Integer.toString(10));
                    xSeekbarImageView.setTextColor(Color.GREEN);
                }
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAlgeOps();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasStartedAnimationX && !hasStartedAnimation1)
                    reset();
            }
        });

        //TODO: Add level up mechanics
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasStartedAnimationX && !hasStartedAnimation1) {
                    Log.d(TAG, "checkButton clicked");
                    int correctAnswers = prefs.getInt(Constants.CORRECT_SUB_ANSWERS, 0);
                    if (!isSecondAnswerCorrect) {
                        Log.d(TAG, "First ans correct.");
                        if (isSeekBarAnswerCorrect()) {
                            isSecondAnswerCorrect = true;
                            //Count if the user has 10 consecutive answers (4 levels)
                            correctAnswers++;
                            int currLevel = prefs.getInt(Constants.SUB_LEVEL, 1);
                            Toast.makeText(SubtractActivity.this, "You are correct!", Toast.LENGTH_SHORT).show();
                            if (correctAnswers == Constants.LEVEL_UP && currLevel != Constants.LEVEL_4) {
                                currLevel++;
                                Toast.makeText(SubtractActivity.this, "Congratulations! You are now in Level " + currLevel, Toast.LENGTH_SHORT).show();
                                prefs.edit().putInt(Constants.SUB_LEVEL, currLevel).apply();
                                prefs.edit().putInt(Constants.CORRECT_SUB_ANSWERS, 0).apply();
                            } else {
                                prefs.edit().putInt(Constants.CORRECT_SUB_ANSWERS, correctAnswers).apply();
                            }
                            Log.d(TAG, "Correct: " + correctAnswers);
//                            startAlgeOps();
                        } else {
                            playSound(R.raw.wrong);
                            if (correctAnswers != Constants.LEVEL_UP) {
                                correctAnswers = 0;
                                prefs.edit().putInt(Constants.CORRECT_ADD_ANSWERS, correctAnswers).apply();
                                Log.d(TAG, "Back to start: " + correctAnswers);
                            }
                        }
                        Handler h = new Handler();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startAlgeOps();
                            }
                        }, 2000);
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

        subLayout.onAnimationEndListener(new AlgeOpsRelativeLayout.AnimationEndListener() {
            @Override
            public void onAnimationEnded(int val) {
                Log.d(TAG, "val/countX/1: " + val + "/" + countX + "/" + count1);
                animatedCounter++;
                if (animatedCounter == ((count1 + countX) * 2)) {
                    hasStartedAnimationX = false;
                    hasStartedAnimation1 = false;
                }
            }
        });
    }

    //TODO: Do i also have to cancel out the initial views? or only added views?
    private void cancelOutViews() {
        Handler handler1 = new Handler();
        countX = LayoutUtilities.getNumberOfViewsToRemove(subLayout, Constants.OPS_X);
        Log.d(TAG, "Cancelling out X: " + countX);
        for (int i = 0; i < countX; i++) {
            hasStartedAnimationX = true;
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    subLayout.removeSubImage(Constants.POS_X);
                    subLayout.removeSubImage(Constants.NEG_X);
                }
            }, 3200 * i);
        }

        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                Handler handler3 = new Handler();
                count1 = LayoutUtilities.getNumberOfViewsToRemove(subLayout, Constants.OPS_ONE);
                Log.d(TAG, "Cancelling out 1: " + count1);
                for (int i = 0; i < count1; i++) {
                    hasStartedAnimation1 = true;
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            subLayout.removeSubImage(Constants.POS_ONE);
                            subLayout.removeSubImage(Constants.NEG_ONE);
                        }
                    }, 3200 * i);
                }
            }
        }, countX * 3200);

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

    @Override
    protected void reset() {
        super.reset();
        subLayout.resetLayout();
        subLayout.populateImageViewBasedOnLeftSideEq(SubtractActivity.this, eq);
    }

    protected void startAlgeOps() {
        super.startAlgeOps();

        firstPartEq.removeAllViews();
        secondPartEq.removeAllViews();

        int subLevel = prefs.getInt(Constants.SUB_LEVEL, 1);
        eq = EquationGeneration.generateEquation("SUB", subLevel);
        firstPart = eq.getPart(1);
        secondPart = eq.getPart(2);

        final int factor = getResources().getInteger(R.integer.text_box_factor);
        if (subLevel == Constants.LEVEL_1) {
            setEquationsLayout();
        } else if (subLevel == Constants.LEVEL_2) {
            firstPartEq.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    firstPartEq.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int tempH = firstPartEq.getMeasuredHeight();
                    TextView tv1 = new TextView(SubtractActivity.this);
                    //tv1.setText(" from, " + firstPart);
                    TextView tv2 = new TextView(SubtractActivity.this);
                    tv2.setText("Remove " + secondPart);// + ", from " + firstPart);
                    tv1.setTextSize(tempH / factor);
                    tv2.setTextSize(tempH / factor);
                    firstPartEq.addView(tv1);
                    secondPartEq.addView(tv2);
                }
            });

        } else if (subLevel == Constants.LEVEL_3) {
            setEquationsLayout();
        } else {
            firstPartEq.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    firstPartEq.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int tempH = firstPartEq.getMeasuredHeight();
                    TextView tv1 = new TextView(SubtractActivity.this);
                    //tv1.setText(" from, " + firstPart);
                    TextView tv2 = new TextView(SubtractActivity.this);
                    tv2.setText("Remove " + secondPart);// + ", from " + firstPart);
                    tv1.setTextSize(tempH / factor);
                    tv2.setTextSize(tempH / factor);
                    firstPartEq.addView(tv1);
                    secondPartEq.addView(tv2);
                }
            });

        }

        subLayout.resetLayout();
        xSeekbar.resetSeekBars();
        oneSeekbar.resetSeekBars();
        answerIsWrong();

        subLayout.populateImageViewBasedOnLeftSideEq(SubtractActivity.this, eq);
    }

    protected void setEquationsLayout() {
        final int[] first = eq.getIntArr(1);
        final int[] second = eq.getIntArr(2);
        final int factor = getResources().getInteger(R.integer.text_with_image);
        firstPartEq.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                firstPartEq.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int tempH = firstPartEq.getMeasuredHeight();
                if (first[0] != 0) {
                    TextView tv = new TextView(SubtractActivity.this);
                    tv.setText(Integer.toString(Math.abs(first[0])));
                    ImageView iv = new ImageView(SubtractActivity.this);
                    if (first[0] > 0)
                        iv.setImageResource(R.drawable.green_cube);
                    else
                        iv.setImageResource(R.drawable.red_cube);
                    tv.setTextSize(tempH / factor);
                    firstPartEq.addView(tv);
                    firstPartEq.addView(iv);
                }

                if (first[1] != 0) {
                    TextView tv = new TextView(SubtractActivity.this);
                    tv.setText(Integer.toString(Math.abs(first[1])));
                    ImageView iv = new ImageView(SubtractActivity.this);
                    if (first[1] > 0)
                        iv.setImageResource(R.drawable.green_circle);
                    else
                        iv.setImageResource(R.drawable.red_circle);
                    tv.setTextSize(tempH / factor);
                    firstPartEq.addView(tv);
                    firstPartEq.addView(iv);
                }

                if (second[0] != 0) {
                    TextView tv = new TextView(SubtractActivity.this);
                    tv.setText(Integer.toString(Math.abs(second[0])));
                    ImageView iv = new ImageView(SubtractActivity.this);
                    if (second[0] > 0)
                        iv.setImageResource(R.drawable.green_cube);
                    else
                        iv.setImageResource(R.drawable.red_cube);
                    tv.setTextSize(tempH / factor);
                    secondPartEq.addView(tv);
                    secondPartEq.addView(iv);
                }

                if (second[1] != 0) {
                    TextView tv = new TextView(SubtractActivity.this);
                    tv.setText(Integer.toString(Math.abs(second[1])));
                    ImageView iv = new ImageView(SubtractActivity.this);
                    if (second[1] > 0)
                        iv.setImageResource(R.drawable.green_circle);
                    else
                        iv.setImageResource(R.drawable.red_circle);
                    tv.setTextSize(tempH / factor);
                    secondPartEq.addView(tv);
                    secondPartEq.addView(iv);
                }

                TextView tv = (TextView) secondPartEq.getChildAt(0);
                tv.setGravity(Gravity.START);
                String temp = tv.getText().toString();
                tv.setText("Remove: " + temp);

                firstPartEq.removeAllViews();
//                TextView tv2 = new TextView(SubtractActivity.this);
//                tv2.setGravity(Gravity.START);
//                tv2.setText(" From: ");
//                tv2.setTextSize(tempH / factor);
//                firstPartEq.addView(tv2);
            }
        });


    }

    private void checkIfTilesAreCorrect() {
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
                playSound(R.raw.correct);
            }
        }
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
                Log.d(TAG, prefs.getInt(Constants.SUB_LEVEL, 1) + "");
                if (prefs.getInt(Constants.SUB_LEVEL, 1) < Constants.LEVEL_3) {
                    if (view.getId() == R.id.addPosNegOneButton || view.getId() == R.id.addPosNegXButton)
                    {
                        playSound(R.raw.wrong);
                        return;
                    }
                    if (mView.setSubImage(mContext, mOperation, eq)) {
                        checkIfTilesAreCorrect();
                        playSound(R.raw.correct);
                    } else {
                        playSound(R.raw.wrong);
                    }
                } else {
                    if (mView.setSubImage(mContext, mOperation)) {
                        playSound(R.raw.correct);
                        checkIfTilesAreCorrect();
                    } else {
                        playSound(R.raw.wrong);
                    }

                }
            }
        }
    }
}

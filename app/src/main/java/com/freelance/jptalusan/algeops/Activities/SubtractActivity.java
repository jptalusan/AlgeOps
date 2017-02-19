package com.freelance.jptalusan.algeops.Activities;

import android.content.Context;
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

import static com.freelance.jptalusan.algeops.R.id.layoutLeftOne;
import static com.freelance.jptalusan.algeops.R.id.layoutLeftX;
import static com.freelance.jptalusan.algeops.R.id.layoutRightOne;
import static com.freelance.jptalusan.algeops.R.id.layoutRightX;

public class SubtractActivity extends BaseOpsActivity {
    private static final String TAG = "SubActivity";
    private ImageView addXImageView;
    private ImageView subXImageView;
    private ImageView addOneImageView;
    private ImageView subOneImageView;
    private ImageView addsubXImageView;
    private ImageView addsubOneImageView;

    private ImageButton addPosXButton;
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

        startButton = (Button) findViewById(R.id.startButton);
        checkButton = (Button) findViewById(R.id.checkButton);

        firstPartEq = (TextView) findViewById(R.id.firstEqTextView);
        secondPartEq = (TextView) findViewById(R.id.secondEqTextView);

        addXImageView = (ImageView) findViewById(R.id.addXImageView);
        subXImageView = (ImageView) findViewById(R.id.subXImageView);
        addOneImageView = (ImageView) findViewById(R.id.addOneImageView);
        subOneImageView = (ImageView) findViewById(R.id.subOneImageView);
        addsubXImageView = (ImageView) findViewById(R.id.addsubXImageView);
        addsubOneImageView = (ImageView) findViewById(R.id.addsubOneImageView);

        xSeekbarImageView = (ImageView) findViewById(R.id.xSeekbarImageView);
        oneSeekbarImageView = (ImageView) findViewById(R.id.oneSeekbarImageView);

        operationImageView = (ImageView) findViewById(R.id.operationImageView);
        operationImageView.setImageResource(R.drawable.minus);

        addPosXButton = (ImageButton) findViewById(R.id.subPosXButton);
        subNegXButton = (ImageButton) findViewById(R.id.subNegXButton);
        addPosNegXButton = (ImageButton) findViewById(R.id.addPosNegXButton);

        subPosOneButton = (ImageButton) findViewById(R.id.subPosOneButton);
        subNegOneButton = (ImageButton) findViewById(R.id.subNegOneButton);
        addPosNegOneButton = (ImageButton) findViewById(R.id.addPosNegOneButton);

        subLayout = (AlgeOpsRelativeLayout) findViewById(R.id.subAlgeOpsRelLayout);

        xSeekbar = (LayoutWithSeekBarView) findViewById(R.id.subXSeekBar);
        oneSeekbar = (LayoutWithSeekBarView) findViewById(R.id.subOneSeekBar);

        startButton.setText("START");
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAlgeOps();
            }
        });
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelOutViews();
                isSeekBarAnswerCorrect();
            }
        });

        answerIsWrong();

        subLayout.getViewDimensions();

        addPosXButton.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB_POS_X, subLayout));
        subNegXButton.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB_NEG_X, subLayout));
        addPosNegXButton.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_ADD_POS_NEG_X, subLayout));

        subPosOneButton.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB_POS_ONE, subLayout));
        subNegOneButton.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB_NEG_ONE, subLayout));
        addPosNegOneButton.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_ADD_POS_NEG_ONE, subLayout));
    }

    //TODO: Do i also have to cancel out the initial views? or only added views?
    private void cancelOutViews() {
        int countX = LayoutUtilities.getNumberOfViewsToRemove(subLayout, Constants.OPS_X);
        Log.d(TAG, "Cancelling out X: " + countX);
        Handler handler1 = new Handler();
        for (int i = 0; i < countX; ++i) {
            handler1.postDelayed(new Runnable() {

                @Override
                public void run() {
                    subLayout.removeImage(Constants.POS_X);
                    subLayout.removeImage(Constants.NEG_X);
                }
            }, 1000 * i);
        }

        int countOne = LayoutUtilities.getNumberOfViewsToRemove(subLayout, Constants.OPS_ONE);
        Log.d(TAG, "Cancelling out 1: " + countOne);
        for (int i = 0; i < countOne; ++i) {
            handler1.postDelayed(new Runnable() {

                @Override
                public void run() {
                    subLayout.removeImage(Constants.POS_ONE);
                    subLayout.removeImage(Constants.NEG_ONE);
                }
            }, 1500 * i);
        }
    }

    private void isSeekBarAnswerCorrect() {
        Log.d("Seekbar", "corrX:" + (eq.getAx() - eq.getCx()) + "");
        Log.d("Seekbar", "corr1:" + (eq.getB() - eq.getD()) + "");

        if (xSeekbar.twoThumbs && oneSeekbar.twoThumbs) {
            if (xSeekbar.checkAnswer() && oneSeekbar.checkAnswer()) {
                playSound(R.raw.correct);
                Log.d(TAG, "Two thumb: Answer is correct.");
                //Added recent
                xSeekbar.setEnabled(false);
                oneSeekbar.setEnabled(false);
            } else {
                Log.d(TAG, "Two thumb: inccorect");
                playSound(R.raw.wrong);
                xSeekbar.setCorrectAnswer(eq.getAx() - eq.getCx());
                xSeekbar.answerIsIncorrect();

                oneSeekbar.setCorrectAnswer(eq.getB() - eq.getD());
                oneSeekbar.answerIsIncorrect();
            }
        } else {
            Log.d(TAG, "userX:" + xSeekbar.getUserAnswer() + ", user1:" + oneSeekbar.getUserAnswer());
            if (eq.isSubtractAnswerCorrect(xSeekbar.getUserAnswer(), oneSeekbar.getUserAnswer())) {
                playSound(R.raw.correct);
                Log.d(TAG, "One thumb: Answer is correct.");
                //Added recent
                xSeekbar.setEnabled(false);
                oneSeekbar.setEnabled(false);
            } else {
                Log.d(TAG, "One thumb: incorrect.");
                playSound(R.raw.wrong);
                xSeekbar.setCorrectAnswer(eq.getAx() - eq.getCx());
                xSeekbar.answerIsIncorrect();
                oneSeekbar.setCorrectAnswer(eq.getB() - eq.getD());
                oneSeekbar.answerIsIncorrect();
            }
        }
    }

    protected void startAlgeOps() {
        super.startAlgeOps();
        subLayout.resetLayout();
        xSeekbar.resetSeekBars();
        oneSeekbar.resetSeekBars();
        answerIsWrong();

        subLayout.populateImageViewBasedOnEq(SubtractActivity.this, eq);
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
                if (mView.setSubImage(mContext, mOperation)) {
                    playSound(R.raw.correct);
                } else {
                    playSound(R.raw.wrong);
                }

                //initx = initPosX - initNegX
                //init1 = initPos1 - initNegX
                //answerX = initx + -1(posX - negX)
                //answer1 = init1 + -1(pos1 - neg1)
                int initX = mView.initialPositiveX - mView.initialNegativeX;
                int init1 = mView.initialPositiveOne - mView.initialNegativeOne;
                int answX = initX + (mView.positiveX - mView.negativeX);
                int answ1 = init1 + (mView.positiveOne - mView.negativeOne);
                boolean isCorrect = eq.isSubtractAnswerCorrect(answX, answ1);

                if (isCorrect) {
                    xSeekbar.getViewDimensions();
                    oneSeekbar.getViewDimensions();
                    answerIsCorrect();
                } else {
                    answerIsWrong();
                }

                Log.d(TAG, "EQ:" + eq.toString());
                Log.d(TAG, "initX: " + initX + ", init1: " + init1);
                Log.d(TAG, "X: " + (mView.positiveX - mView.negativeX) + ", 1: " + (mView.positiveOne - mView.negativeOne));
                Log.d(TAG, "Correct:" + eq.computeSubtractAnswer());
                Log.d(TAG, "Answer:" + answX + " + " + answ1);
            }
        }
    }
}

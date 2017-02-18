package com.freelance.jptalusan.algeops.Activities;

import android.content.Context;
import android.os.Bundle;
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

    private ImageView xSeekbarImageView;
    private ImageView oneSeekbarImageView;

    private LayoutWithSeekBarView xSeekbar;
    private LayoutWithSeekBarView oneSeekbar;

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
        //TODO: Add method to reset everything, remove all child views and set all currvals to 0
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAlgeOps();
            }
        });
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        //TODO: What to do with 2 other buttons? (with 2 pics each)
    }

    //TODO Somethjing wrong here
    private void isSeekBarAnswerCorrect() {
        if (eq.isSubtractAnswerCorrect(xSeekbar.getUserAnswer(), oneSeekbar.getUserAnswer())) {
            playCorrectSound();
        } else {
            if (!xSeekbar.checkAnswer() && !oneSeekbar.checkAnswer()) {
                playWrongSound();
                Log.d("Seekbar", "corrX:" + (eq.getAx() - eq.getCx()) + "");
                xSeekbar.setCorrectAnswer(eq.getAx() - eq.getCx());
                xSeekbar.answerIsIncorrect();

                Log.d("Seekbar", "corr1:" + (eq.getB() - eq.getD()) + "");
                oneSeekbar.setCorrectAnswer(eq.getB() - eq.getD());
                oneSeekbar.answerIsIncorrect();
            } else {
                playCorrectSound();
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

    //TODO: move to base activity
    protected void answerIsCorrect() {
        super.answerIsCorrect();

        xSeekbar.setVisibility(View.VISIBLE);
        oneSeekbar.setVisibility(View.VISIBLE);
        xSeekbarImageView.setVisibility(View.VISIBLE);
        oneSeekbarImageView.setVisibility(View.VISIBLE);
    }

    //TODO: move to base activity
    protected void answerIsWrong() {
        super.answerIsWrong();
//        ConstraintLayout.LayoutParams newQuestionLayoutParams = (ConstraintLayout.LayoutParams) startButton.getLayoutParams();
//        newQuestionLayoutParams.leftToLeft = R.id.leftTwentyPercent;
//        newQuestionLayoutParams.leftMargin = 0;
//        newQuestionLayoutParams.rightMargin = 0;
//        newQuestionLayoutParams.rightToLeft = R.id.rightEightyPercent;
//        startButton.setLayoutParams(newQuestionLayoutParams);

        xSeekbar.setVisibility(View.GONE);
        oneSeekbar.setVisibility(View.GONE);
        xSeekbarImageView.setVisibility(View.GONE);
        oneSeekbarImageView.setVisibility(View.GONE);
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
                    playCorrectSound();
                } else {
                    playWrongSound();
                }

                //initx = initPosX - initNegX
                //init1 = initPos1 - initNegX
                //answerX = initx + -1(posX - negX)
                //answer1 = init1 + -1(pos1 - neg1)
                int initX = mView.initialPositiveX - mView.initialNegativeX;
                int init1 = mView.initialPositiveOne - mView.initialNegativeOne;
                int answX = initX + (mView.positiveX - mView.negativeX);
                int answ1 = init1 + (mView.positiveOne - mView.negativeOne);
//TODO: check if correct using initial (left side) and currvalues instead.
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

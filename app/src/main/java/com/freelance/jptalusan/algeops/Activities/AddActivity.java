package com.freelance.jptalusan.algeops.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.freelance.jptalusan.algeops.AlgeOpsRelativeLayout;
import com.freelance.jptalusan.algeops.AlgeOpsSeekbar;
import com.freelance.jptalusan.algeops.R;
import com.freelance.jptalusan.algeops.Utilities.Constants;
import com.freelance.jptalusan.algeops.Utilities.Equation;
import com.freelance.jptalusan.algeops.Utilities.EquationGeneration;

//TODO: Add behavior and button responses
public class AddActivity extends BaseOpsActivity {
    private Button newQuestion;
    private Button checkAnswer;

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

    private AlgeOpsSeekbar xSeekbar;
    private AlgeOpsSeekbar oneSeekbar;

    private TextView firstPartEq;
    private TextView secondPartEq;
    private TextView xSeekbarText;
    private TextView oneSeekbarText;

    private ImageView xSeekbarImage;
    private ImageView oneSeekbarImage;

    private String firstPart;
    private String secondPart;

    protected Equation eq;

    protected boolean hasStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        newQuestion     = (Button) findViewById(R.id.newQuestionButton);
        checkAnswer     = (Button) findViewById(R.id.checkButton);

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

        firstPartEq     = (TextView) findViewById(R.id.firstEqTextView);
        secondPartEq    = (TextView) findViewById(R.id.secondEqTextView);

        xSeekbar        = (AlgeOpsSeekbar) findViewById(R.id.xSeekBar);
        oneSeekbar      = (AlgeOpsSeekbar) findViewById(R.id.oneSeekBar);

        xSeekbarText    = (TextView) findViewById(R.id.xSeekBarCount);
        oneSeekbarText  = (TextView) findViewById(R.id.oneSeekBarCount);

        xSeekbarImage   = (ImageView) findViewById(R.id.xSeekBarImage);
        oneSeekbarImage = (ImageView) findViewById(R.id.oneSeekBarImage);

        newQuestion.setText("START");
        //TODO: Add method to reset everything, remove all child views and set all currvals to 0
        newQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasStarted = true;
                eq = EquationGeneration.generateEquation("MAIN");
                firstPart = eq.getPart(1);
                secondPart = eq.getPart(2);

                firstPartEq.setText(firstPart);
                secondPartEq.setText(secondPart);

                newQuestion.setText("NEW");

                layoutLeftX.resetLayout();
                layoutRightX.resetLayout();
                layoutLeftOne.resetLayout();
                layoutRightOne.resetLayout();

                answerIsWrong();
            }
        });

        answerIsWrong();

        layoutLeftX.getViewDimensions();
        layoutRightX.getViewDimensions();
        layoutLeftOne.getViewDimensions();
        layoutRightOne.getViewDimensions();

        leftXAdd.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_ADD, layoutLeftX));
        leftXSub.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB, layoutLeftX));

        rightXAdd.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_ADD, layoutRightX));
        rightXSub.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB, layoutRightX));

        leftOneAdd.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_ADD, layoutLeftOne));
        leftOneSub.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB, layoutLeftOne));

        rightOneAdd.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_ADD, layoutRightOne));
        rightOneSub.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB, layoutRightOne));

        xSeekbar.setOnSeekBarChangeListener(new AlgeOpsSeekBarChangeListener(this, Constants.OPS_X, xSeekbarText));
        oneSeekbar.setOnSeekBarChangeListener(new AlgeOpsSeekBarChangeListener(this, Constants.OPS_ONE, oneSeekbarText));
    }

    private void answerIsCorrect() {
        //http://stackoverflow.com/questions/39831710/add-view-to-constraintlayout-with-constraints-similar-to-another-child
        //TODO: Make this more readable (this is to change the button placement when user has not yet answered correctly)
        ConstraintLayout.LayoutParams newQuestionLayoutParams = (ConstraintLayout.LayoutParams) newQuestion.getLayoutParams();
        newQuestionLayoutParams.leftToLeft = R.id.verticalFiftyPercent;
        newQuestionLayoutParams.leftMargin = 0;
        newQuestionLayoutParams.rightMargin = 0;
        newQuestion.setLayoutParams(newQuestionLayoutParams);

        checkAnswer.setVisibility(View.VISIBLE);
        xSeekbar.setVisibility(View.VISIBLE);
        oneSeekbar.setVisibility(View.VISIBLE);

        xSeekbarText.setVisibility(View.VISIBLE);
        oneSeekbarText.setVisibility(View.VISIBLE);

        xSeekbarImage.setVisibility(View.VISIBLE);
        oneSeekbarImage.setVisibility(View.VISIBLE);

        xSeekbar.setProgress(12);
        oneSeekbar.setProgress(12);

        hasStarted = false;
    }

    private void answerIsWrong() {
        //http://stackoverflow.com/questions/39831710/add-view-to-constraintlayout-with-constraints-similar-to-another-child
        //TODO: Make this more readable (this is to change the button placement when user has not yet answered correctly)
        ConstraintLayout.LayoutParams newQuestionLayoutParams = (ConstraintLayout.LayoutParams) newQuestion.getLayoutParams();
        newQuestionLayoutParams.leftToLeft = R.id.leftTwentyPercent;
        newQuestionLayoutParams.leftMargin = 0;
        newQuestionLayoutParams.rightMargin = 0;
        newQuestion.setLayoutParams(newQuestionLayoutParams);

        checkAnswer.setVisibility(View.GONE);
        xSeekbar.setVisibility(View.GONE);
        oneSeekbar.setVisibility(View.GONE);

        xSeekbarText.setVisibility(View.GONE);
        oneSeekbarText.setVisibility(View.GONE);

        xSeekbarImage.setVisibility(View.GONE);
        oneSeekbarImage.setVisibility(View.GONE);
    }

    public class AlgeOpsSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        private static final String TAG = "Seekbar";
        private Context mContext;
        private int mOperation;
        private TextView mTv;

        public AlgeOpsSeekBarChangeListener(Context context, int operation, TextView tv) {
            mContext = context;
            mOperation = operation;
            mTv = tv;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            mTv.setText(i + "");

            int x = xSeekbar.getProgress() - Constants.SEEKBAR_MAX;
            int one = oneSeekbar.getProgress() - Constants.SEEKBAR_MAX;

            Log.d(TAG, x + "," + one);
            //TODO: stop bar movement once correct
            if (eq.isFinalAnswerCorrect(x, one)) {
                Toast.makeText(mContext, "Correct!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    public class AlgeOpsButtonsOnClickListener implements View.OnClickListener {
        private static final String TAG = "AlgeOpsOnClickListener";
        private Context mContext;
        private int mOperation;
        private AlgeOpsRelativeLayout mView;

        public AlgeOpsButtonsOnClickListener(Context context, int operation, AlgeOpsRelativeLayout view) {
            mContext = context;
            mOperation = operation;
            mView = view;
        }

        @Override
        public void onClick(View view) {
            if (hasStarted) {
                mView.setImage(mContext, mOperation);

                boolean isCorrect = eq.isAnswerCorrect(layoutLeftX.currVal,
                        layoutLeftOne.currVal,
                        layoutRightX.currVal,
                        layoutRightOne.currVal);

                if (isCorrect) {
                    answerIsCorrect();
                } else {
                    answerIsWrong();
                }

                Log.d(TAG, isCorrect + "");
                Log.d(TAG, "Currval: " + mView.currVal);
                Log.d(TAG, mView.dimensions.toString());
                Log.d(TAG, "Children: " + mView.getChildCount() + "");
            }
        }
    }
}

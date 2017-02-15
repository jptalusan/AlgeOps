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

//TODO: Add behavior and button responses
public class AddActivity extends BaseOpsActivity {
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

    private LayoutWithSeekBarView xSeekbar;
    private LayoutWithSeekBarView oneSeekbar;

    private TextView xSeekbarText;
    private TextView oneSeekbarText;

    private ImageView xSeekbarImage;
    private ImageView oneSeekbarImage;

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

        firstPartEq     = (TextView) findViewById(R.id.firstEqTextView);
        secondPartEq    = (TextView) findViewById(R.id.secondEqTextView);

        xSeekbar        = (LayoutWithSeekBarView) findViewById(R.id.xSeekBar);
        oneSeekbar      = (LayoutWithSeekBarView) findViewById(R.id.oneSeekBar);

        xSeekbarImage   = (ImageView) findViewById(R.id.xSeekBarImage);
        oneSeekbarImage = (ImageView) findViewById(R.id.oneSeekBarImage);

        operationImageView = (ImageView) findViewById(R.id.operationImageView);
        operationImageView.setImageResource(R.drawable.plus);

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
                playCorrectSound();
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

//        xSeekbar.setOnSeekBarChangeListener(new AlgeOpsSeekBarChangeListener(this, Constants.OPS_X, xSeekbarText));
//        oneSeekbar.setOnSeekBarChangeListener(new AlgeOpsSeekBarChangeListener(this, Constants.OPS_ONE, oneSeekbarText));
    }

    protected void startAlgeOps() {
        super.startAlgeOps();

        layoutLeftX.resetLayout();
        layoutRightX.resetLayout();
        layoutLeftOne.resetLayout();
        layoutRightOne.resetLayout();

        answerIsWrong();
    }

    //TODO: move to base activity
    protected void answerIsCorrect() {
        super.answerIsCorrect();

        xSeekbar.setVisibility(View.VISIBLE);
        oneSeekbar.setVisibility(View.VISIBLE);

        xSeekbarImage.setVisibility(View.VISIBLE);
        oneSeekbarImage.setVisibility(View.VISIBLE);

//        xSeekbar.setProgress(12);
//        oneSeekbar.setProgress(12);

    }

    //TODO: move to base activity
    protected void answerIsWrong() {
        super.answerIsWrong();

        checkButton.setVisibility(View.GONE);
        xSeekbar.setVisibility(View.GONE);
        oneSeekbar.setVisibility(View.GONE);

        xSeekbarImage.setVisibility(View.GONE);
        oneSeekbarImage.setVisibility(View.GONE);
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
                mView.setImage(mContext, mOperation);

                boolean isCorrect = eq.isAdditionAnswerCorrect(layoutLeftX.currXVal,
                        layoutLeftOne.currOneVal,
                        layoutRightX.currXVal,
                        layoutRightOne.currOneVal);

                if (isCorrect) {
                    xSeekbar.getViewDimensions();
                    oneSeekbar.getViewDimensions();
                    answerIsCorrect();
                } else {
                    answerIsWrong();
                }

                Log.d(TAG, isCorrect + "");
                Log.d(TAG, mView.dimensions.toString());
                Log.d(TAG, "Children: " + mView.getChildCount() + "");
            }
        }
    }
}

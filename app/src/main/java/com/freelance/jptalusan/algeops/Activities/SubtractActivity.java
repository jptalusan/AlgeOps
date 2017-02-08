package com.freelance.jptalusan.algeops.Activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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

    private ImageButton addXButton;
    private ImageButton subXButton;
    private ImageButton addOneButton;
    private ImageButton subOneButton;
    private ImageButton addsubXButton;
    private ImageButton addsubOneButton;

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

        addXImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                addXImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width, height;
                width  = addXImageView.getMeasuredWidth();
                height = addXImageView.getMeasuredHeight();

                ViewGroup.LayoutParams params = addXImageView.getLayoutParams();
            }
        });

        addXButton = (ImageButton) findViewById(R.id.addXButton);
        subXButton = (ImageButton) findViewById(R.id.subXButton);
        addOneButton = (ImageButton) findViewById(R.id.addOneButton);
        subOneButton = (ImageButton) findViewById(R.id.subOneButton);
        addsubXButton = (ImageButton) findViewById(R.id.addsubXButton);
        addsubOneButton = (ImageButton) findViewById(R.id.addsubOneButton);

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
        answerIsWrong();

        subLayout.getViewDimensions();

        addXButton.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_ADD_X, subLayout));
        subXButton.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB_X, subLayout));
        addOneButton.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_ADD_ONE, subLayout));
        subOneButton.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_SUB_ONE, subLayout));

        //TODO: What to do with 2 other buttons? (with 2 pics each)
    }

    protected void startAlgeOps() {
        super.startAlgeOps();

        subLayout.resetLayout();

        answerIsWrong();
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

        public AlgeOpsButtonsOnClickListener(Context context, int operation, AlgeOpsRelativeLayout view) {
            mContext = context;
            mOperation = operation;
            mView = view;
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "OnClick");
            if (hasStarted) {
                mView.setImage(mContext, mOperation);

//                boolean isCorrect = eq.isAnswerCorrect(layoutLeftX.currVal,
//                        layoutLeftOne.currVal,
//                        layoutRightX.currVal,
//                        layoutRightOne.currVal);
//
//                if (isCorrect) {
//                    answerIsCorrect();
//                } else {
//                    answerIsWrong();
//                }

//                Log.d(TAG, isCorrect + "");
                Log.d(TAG, "CurrXval: " + mView.currXVal);
                Log.d(TAG, "CurrOneval: " + mView.currOneVal);
                Log.d(TAG, mView.dimensions.toString());
                Log.d(TAG, "Children: " + mView.getChildCount() + "");
            }
        }
    }
}

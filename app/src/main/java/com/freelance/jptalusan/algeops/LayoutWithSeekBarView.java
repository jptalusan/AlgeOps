package com.freelance.jptalusan.algeops;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.freelance.jptalusan.algeops.Utilities.Constants;
import com.freelance.jptalusan.algeops.Utilities.Dimensions;

public class LayoutWithSeekBarView extends ConstraintLayout {
    private static final String TAG = "SeekbarView";
    public ComboSeekBar seekBar;
    public RelativeLayout relativeLayout;
    public RelativeLayout numbersLayout;
    private Dimensions dimensions = new Dimensions();
    private Dimensions layoutDims = new Dimensions();
    private LayoutWithSeekBarView layoutWithSeekBarView = this;
    private int type = -1;
    private int userAnswer = 0;
    private int correctAnswer = 0;
    public boolean isAnswerIncorrect = false;

    public LayoutWithSeekBarView(Context context) {
        super(context);
        initializeViews(context);
    }

    public LayoutWithSeekBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LayoutWithSeekBarView, 0, 0);
        type = a.getInt(R.styleable.LayoutWithSeekBarView_type, 0);
        initializeViews(context);
        a.recycle();
    }

    public LayoutWithSeekBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LayoutWithSeekBarView, 0, 0);
        type = a.getInt(R.styleable.LayoutWithSeekBarView_type, 0);
        initializeViews(context);
        a.recycle();
    }

    /**
     * Inflates the views in the layout.
     *
     * @param context
     *           the current context for the view.
     */
    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layoutwithseekbarview, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        relativeLayout = (RelativeLayout) this.findViewById(R.id.subLayout);
        numbersLayout  = (RelativeLayout) this.findViewById(R.id.numbersLayout);
        seekBar        = (ComboSeekBar) this.findViewById(R.id.seekbar);
    }

    public void getViewDimensions() {
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layoutWithSeekBarView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                layoutDims.height = layoutWithSeekBarView.getMeasuredHeight();
            }
        });

        relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                relativeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                dimensions.width = relativeLayout.getMeasuredWidth();
                dimensions.height = layoutDims.height / 2;
//                Log.d(TAG, "rel w x h: " + dimensions.width + " x " + dimensions.height);
            }
        });
    }

    //Change the rangeseekbar being shown at run time, when user inputs incorrect value
    //when min and max values have been matched (at correctValue) then that is OK.
    public void answerIsIncorrect() {
        isAnswerIncorrect = true;

        Log.d("AddAc", "userAnswer: " + userAnswer);
        //drawValuesInRelativeLayout(userAnswer, false);
        drawValuesInRelativeLayout(correctAnswer, true);

        seekBar.setEnabled(false);
    }

    public void resetSeekBars() {
        seekBar.setProgress(10);
        seekBar.setEnabled(true);
        seekBar.setVisibility(VISIBLE);
        relativeLayout.removeAllViews();
        userAnswer = 0;
        correctAnswer = 0;
        seekBar.setEnabled(true);
        isAnswerIncorrect = false;
    }

    public void removeAllViewsInRelativeLayout() {
        if (!isAnswerIncorrect)
            relativeLayout.removeAllViews();
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(int answer) {
        userAnswer = answer;
    }

    public void drawValuesInRelativeLayout(Integer maxValue, boolean colorLast) {
//        Log.d(TAG, "drawValuesInRelativeLayout()");
        double center = dimensions.width / 2;

        for (int i = 0; i < Math.abs(maxValue); ++i) {

            ImageView imageView = new ImageView(getContext());

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    (int) dimensions.width / 20,
                    (int) dimensions.height);

//            Log.d(TAG, "Params: " + params.width + " / " + params.height);
            if (maxValue > 0) {
                if (type == Constants.X) {
                    imageView.setImageResource(R.drawable.green_cube);
                } else {
                    imageView.setImageResource(R.drawable.green_circle);
                }
                params.leftMargin = (int) (center + params.width / 2) + (params.width * i);
                params.topMargin = 0;
            } else if (maxValue < 0){
                if (type == Constants.X) {
                    imageView.setImageResource(R.drawable.red_cube);
                } else {
                    imageView.setImageResource(R.drawable.red_circle);
                }
                //TODO: WOW MAGIC Number
                params.leftMargin = (int) (center - params.width / 2) - (params.width * (i + 1));
                params.topMargin = 0;
            }

            if (maxValue != 0) {
                if (colorLast && i == Math.abs(maxValue) - 1)
                    imageView.setBackgroundColor(Color.BLUE);
                imageView.setLayoutParams(params);
                relativeLayout.addView(imageView);
            }
        }

        if (0 == maxValue) {
            ImageView imageView = new ImageView(getContext());

            if (type == Constants.X) {
                imageView.setImageResource(R.drawable.cube);
            } else {
                imageView.setImageResource(R.drawable.circle);
            }

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    (int) dimensions.width / 20,
                    (int) dimensions.height);

            params.leftMargin = (int) (center - params.width / 2);
            params.topMargin = 0;

            imageView.setBackgroundColor(Color.BLUE);
            imageView.setLayoutParams(params);
            if (colorLast)
                imageView.setVisibility(VISIBLE);
            else
                imageView.setVisibility(INVISIBLE);

            relativeLayout.addView(imageView);
        }
    }
}

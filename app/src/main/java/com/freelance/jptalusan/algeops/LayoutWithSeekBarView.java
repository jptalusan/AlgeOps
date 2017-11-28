package com.freelance.jptalusan.algeops;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.freelance.jptalusan.algeops.ComboSeekBarView.ComboSeekBar;
import com.freelance.jptalusan.algeops.Utilities.AutoResizeTextView;
import com.freelance.jptalusan.algeops.Utilities.Constants;
import com.freelance.jptalusan.algeops.Utilities.Dimensions;

import java.util.ArrayList;

public class LayoutWithSeekBarView extends ConstraintLayout {
    private static final String TAG = "SeekbarView";
    public AppCompatSeekBar seekBar;
    public RelativeLayout relativeLayout;
    public RelativeLayout numbersLayout;
    private Dimensions dimensions = new Dimensions();
    private Dimensions layoutDims = new Dimensions();
    private Dimensions numbersDimension = new Dimensions();
    private LayoutWithSeekBarView layoutWithSeekBarView = this;
    private int type = -1;
    private int userAnswer = 0;
    private int correctAnswer = 0;
    public boolean isAnswerIncorrect = false;
    private double center = 0;
    private ArrayList<String> mValues = new ArrayList<>();
    private int tickOffset = 0;

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

        relativeLayout = this.findViewById(R.id.subLayout);
        numbersLayout  = this.findViewById(R.id.numbersLayout);
        seekBar        = this.findViewById(R.id.seekbar);
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

                numbersDimension.width  = relativeLayout.getMeasuredWidth() / ((seekBar.getMax()));
                numbersDimension.height = relativeLayout.getMeasuredHeight();

                tickOffset = relativeLayout.getMeasuredWidth() / seekBar.getMax();
                center = dimensions.width / 2;

                numbersLayout.removeAllViews();
                drawNumbers();

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
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                } else {
                    imageView.setImageResource(R.drawable.green_circle);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
                params.leftMargin = (int) (center + params.width / 2) + (params.width * i) - params.width / 2;
                params.topMargin = 0;
            } else if (maxValue < 0){
                if (type == Constants.X) {
                    imageView.setImageResource(R.drawable.red_cube);
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                } else {
                    imageView.setImageResource(R.drawable.red_circle);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
                //TODO: WOW MAGIC Number
                params.leftMargin = (int) (center - params.width / 2) - (params.width * (i + 1)) + params.width / 2;
                params.topMargin = 0;
            }

            if (maxValue != 0) {
                if (colorLast && i == Math.abs(maxValue) - 1)
                    imageView.setBackgroundColor(Color.BLUE);
                imageView.setLayoutParams(params);
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                imageView.setPadding(2, 0, 2, 0);
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

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(2, 0, 2, 0);
            relativeLayout.addView(imageView);
        }
    }

    public void setValues(ArrayList<String> values) {
        numbersLayout.removeAllViews();
        mValues = values;
    }

    private RelativeLayout.LayoutParams generateNumbersParams(int val) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                (int) numbersDimension.width,
                (int) numbersDimension.height);

        if (val > 0) { //add some factor since it does not have the '-' symbol.
            params.leftMargin = (int)center + tickOffset + (tickOffset * (val - 1));
        } else if (val < 0) {
            params.leftMargin = (int)center + tickOffset - (tickOffset * Math.abs(val - 1)) + params.width / 2;
        } else {
            params.leftMargin = (int)center;
        }

        params.topMargin = 30;

        return params;
    }

    public void drawNumbers() {
        if (mValues == null) {
            return;
        }
        for (int i = 0; i < mValues.size(); ++i) {
            AutoResizeTextView tv = new AutoResizeTextView(getContext());
            tv.setMinTextSize(10.0f);
//            tv.setTextSize(10);
            tv.setText(mValues.get(i));
//            tv.setSingleLine(true);
            tv.setLayoutParams(generateNumbersParams(i - 10));
            numbersLayout.addView(tv);
        }
    }
}

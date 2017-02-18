package com.freelance.jptalusan.algeops;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.freelance.jptalusan.algeops.Utilities.Constants;
import com.freelance.jptalusan.algeops.Utilities.Dimensions;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import static android.R.attr.max;
//https://github.com/anothem/android-range-seek-bar <--might go back to this
//https://github.com/syedowaisali/crystal-range-seekbar <-- seems better
/**
 * Created by jtalusan on 2/7/2017.
 */
//TODO: add listener on change and addview to layout
//TODO: add attr for number of objects
//Same value for initial (before check) then if wrong, move the corresponding value to right one
public class LayoutWithSeekBarView extends LinearLayout {
    private static final String TAG = "SeekbarView";
    private RangeSeekBar<Integer> seekBar;
    private RangeSeekBar<Integer> rangeSeekBar;
    private RelativeLayout relativeLayout;
    private Dimensions dimensions = new Dimensions();
    private Dimensions layoutDims = new Dimensions();
    private LayoutWithSeekBarView layoutWithSeekBarView = this;
    private int type = -1;
    private int userAnswer = 0;
    private int correctAnswer = 0;
    private boolean twoThumbs = false;

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

        // Sets the images for the previous and next buttons. Uses
        // built-in images so you don't need to add images, but in
        // a real application your images should be in the
        // application package so they are always available.
        relativeLayout = (RelativeLayout) this.findViewById(R.id.subLayout);
        seekBar = (RangeSeekBar<Integer>) this.findViewById(R.id.seekbar);
        rangeSeekBar = (RangeSeekBar<Integer>) this.findViewById(R.id.rangeseekbar);

        seekBar.setOnRangeSeekBarChangeListener(new AlgeOpsRangeSeekBarListener());

        ImageView iv = new ImageView(this.getContext());
        iv.setImageResource(R.drawable.chrome);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(40, 40);
        iv.setLayoutParams(params);
        relativeLayout.addView(iv);
    }

    public void getViewDimensions() {
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layoutWithSeekBarView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                layoutDims.height = layoutWithSeekBarView.getMeasuredHeight();

                Log.d(TAG, "thismeasW: " + layoutDims.width);
                Log.d(TAG, "thismeasH: " + layoutDims.height);
            }
        });

        relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                relativeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                dimensions.width = relativeLayout.getMeasuredWidth();
                dimensions.height = layoutDims.height / 2;

                Log.d(TAG, "measW: " + dimensions.width);
                Log.d(TAG, "measH: " + dimensions.height);
            }
        });
    }

    //TODO: get correct and incorrect values and use that to set the thumbs.
    //TODO: change color for min/max values
    //Change the rangeseekbar being shown at run time, when user inputs incorrect value
    //when min and max values have been matched (at correctValue) then that is OK.
    public void answerIsIncorrect() {
        twoThumbs = true;
        if (userAnswer < correctAnswer) {
            rangeSeekBar.setSelectedMinValue(userAnswer);
            rangeSeekBar.setSelectedMaxValue(correctAnswer);

            drawValuesInRelativeLayout(userAnswer, false);
            drawValuesInRelativeLayout(correctAnswer, true);
        } else {
            rangeSeekBar.setSelectedMaxValue(userAnswer);
            rangeSeekBar.setSelectedMinValue(correctAnswer);

            drawValuesInRelativeLayout(userAnswer, false);
            drawValuesInRelativeLayout(correctAnswer, true);
        }

        rangeSeekBar.setVisibility(VISIBLE);
        seekBar.setVisibility(GONE);
    }

    public void resetSeekBars() {
        rangeSeekBar.setEnabled(true);
        seekBar.setEnabled(true);
        rangeSeekBar.setVisibility(GONE);
        seekBar.setVisibility(VISIBLE);
        seekBar.resetSelectedValues();
        relativeLayout.removeAllViews();
        userAnswer = 0;
        correctAnswer = 0;
        twoThumbs = false;
    }

    //TODO: Return two single thumb if correct, review this, something is wrong
    public boolean checkAnswer() {
        int max = rangeSeekBar.getSelectedMaxValue();
        int min = rangeSeekBar.getSelectedMinValue();
        if (twoThumbs) {
            Log.d(TAG, "max: " + max + ",min: " + min + ",corr:" + correctAnswer);
            if ((max == correctAnswer) && (min == correctAnswer)) {
//                rangeSeekBar.setEnabled(false);
                return true;
            } else {
                return false;
            }
        } else {
            if (max == correctAnswer) {
//                seekBar.setEnabled(false);
                return true;
            } else {
                return false;
            }
        }
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getUserAnswer() {
        return userAnswer;
    }

    //TODO: add for rangeseekbar also
    public class AlgeOpsRangeSeekBarListener implements RangeSeekBar.OnRangeSeekBarChangeListener<Integer> {

        @Override
        public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
            //At single thumb, maxValue is used
//            getViewDimensions();
            relativeLayout.removeAllViews();
            userAnswer = maxValue;
            Log.d(TAG, maxValue + ";max ");
            drawValuesInRelativeLayout(maxValue, false);
        }
    }

    private void drawValuesInRelativeLayout(Integer maxValue, boolean colorLast) {
        double center = 0.0;
        center = dimensions.width / 2;

        //TODO: add if statement if single thumb?
        for (int i = 0; i < Math.abs(maxValue); ++i) {

            ImageView imageView = new ImageView(getContext());

            if (type == Constants.X) {
                imageView.setImageResource(R.drawable.cube);
            } else {
                imageView.setImageResource(R.drawable.circle);
            }
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    (int) dimensions.width / 20,
                    (int) dimensions.height);

            if (maxValue > 0) {
                imageView.setBackgroundColor(Color.GREEN);
                params.leftMargin = (int) (center + params.width / 2) + (params.width * i);
                params.topMargin = 0;
            } else if (maxValue < 0){
                imageView.setBackgroundColor(Color.RED);
                //TODO: WOW MAGIC Number
                params.leftMargin = (int) (center - params.width / 2) - (params.width * (i + 1));
                Log.d(TAG, "Left:" + params.leftMargin);
                Log.d(TAG, "i:" + i);
                params.topMargin = 0;
            }

            if (maxValue != 0) {
                if (colorLast && i == Math.abs(maxValue) - 1)
                    imageView.setBackgroundColor(Color.BLUE);

                imageView.setLayoutParams(params);
                relativeLayout.addView(imageView);
            }
        }

        //TODO: What if correct answer is 0, 0 is blank when drawing, unless explitly set to zero
        if (maxValue == 0) {
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
            Log.d(TAG, "Left:" + params.leftMargin);
            params.topMargin = 0;

            imageView.setLayoutParams(params);
//            imageView.setVisibility(INVISIBLE);
            relativeLayout.addView(imageView);
        }
    }
}

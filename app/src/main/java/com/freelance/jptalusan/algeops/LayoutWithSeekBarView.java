package com.freelance.jptalusan.algeops;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.freelance.jptalusan.algeops.Utilities.Constants;
import com.freelance.jptalusan.algeops.Utilities.Dimensions;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import io.apptik.widget.MultiSlider;
//https://github.com/anothem/android-range-seek-bar <--might go back to this
//https://github.com/syedowaisali/crystal-range-seekbar <-- seems better
/**
 * Created by jtalusan on 2/7/2017.
 */
//TODO: problem when answer is zero, zero does not get colored blue
//TODO: add attr for number of object
//TODO: The items will appear above the number line as per their value. Example:
//Same value for initial (before check) then if wrong, move the corresponding value to right one
public class LayoutWithSeekBarView extends LinearLayout {
    private static final String TAG = "SeekbarView";
    public MultiSlider seekBar;
    public RelativeLayout relativeLayout;
    private Dimensions dimensions = new Dimensions();
    private Dimensions layoutDims = new Dimensions();
    private LayoutWithSeekBarView layoutWithSeekBarView = this;
    private int type = -1;
    private int userAnswer = 0;
    private int correctAnswer = 0;
    private MultiSlider.Thumb correctAnswerThumb;

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
        seekBar = (MultiSlider) this.findViewById(R.id.seekbar);
        correctAnswerThumb = seekBar.new Thumb();
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
            }
        });
    }

    //Change the rangeseekbar being shown at run time, when user inputs incorrect value
    //when min and max values have been matched (at correctValue) then that is OK.
    public void answerIsIncorrect() {
        correctAnswerThumb.setMin(correctAnswer);
        correctAnswerThumb.setMax(correctAnswer);
        correctAnswerThumb.setValue(correctAnswer);
//        correctAnswerThumb.setThumb(new ColorDrawable(Color.RED));
        seekBar.addThumb(correctAnswerThumb);
//        seekBar.getThumb(1).setRange(new ColorDrawable(Color.RED));

        drawValuesInRelativeLayout(userAnswer, false);
        drawValuesInRelativeLayout(correctAnswer, true);

        seekBar.setEnabled(false);
    }

    public void resetSeekBars() {
        seekBar.setEnabled(true);
        seekBar.setVisibility(VISIBLE);
        relativeLayout.removeAllViews();
        userAnswer = 0;
        correctAnswer = 0;
        seekBar.setEnabled(true);
        seekBar.removeThumb(correctAnswerThumb);
        seekBar.getThumb(0).setValue(0);
//        seekBar.clearThumbs();
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
        double center = dimensions.width / 2;

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
                params.topMargin = 0;
            }

            if (maxValue != 0) {
                if (colorLast && i == Math.abs(maxValue) - 1)
                    imageView.setBackgroundColor(Color.BLUE);

                imageView.setLayoutParams(params);
                relativeLayout.addView(imageView);
            }
        }

        //TODO: if zero is correct then color blue
//        if (maxValue == 0) {
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
            if (colorLast && 0 == maxValue)
                imageView.setBackgroundColor(Color.BLUE);
            imageView.setLayoutParams(params);
            relativeLayout.addView(imageView);
//        }
    }
}

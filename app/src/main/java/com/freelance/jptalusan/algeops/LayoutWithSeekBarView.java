package com.freelance.jptalusan.algeops;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
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
//https://github.com/anothem/android-range-seek-bar <--might go back to this
//https://github.com/syedowaisali/crystal-range-seekbar <-- seems better
/**
 * Created by jtalusan on 2/7/2017.
 */
//TODO: add listener on change and addview to layout
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
    public void answerIsIncorrect(int incorrectValue, int correctValue) {
        if (incorrectValue < correctValue) {
            rangeSeekBar.setSelectedMinValue(incorrectValue);
            rangeSeekBar.setSelectedMaxValue(correctValue);
        } else {
            rangeSeekBar.setSelectedMaxValue(incorrectValue);
            rangeSeekBar.setSelectedMinValue(correctValue);
        }
        rangeSeekBar.setVisibility(VISIBLE);
        seekBar.setVisibility(GONE);
    }

    public void resetSeekBars() {
        rangeSeekBar.setVisibility(GONE);
        seekBar.setVisibility(VISIBLE);
        seekBar.resetSelectedValues();
    }

    //TODO: fix this, should be 20? not 10 and start from middle (left = neg, right = pos)
    public class AlgeOpsRangeSeekBarListener implements RangeSeekBar.OnRangeSeekBarChangeListener<Integer> {

        @Override
        public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
            //At single thumb, maxValue is used
//            getViewDimensions();
            relativeLayout.removeAllViews();
            //TODO: add if statement if single thumb?
            for (int i = 0; i < Math.abs(maxValue); ++i) {
                ImageView imageView = new ImageView(getContext());

                if (type == Constants.X) {
                    imageView.setImageResource(R.drawable.cube);
                } else {
                    imageView.setImageResource(R.drawable.circle);
                }

                if (maxValue > 0) {
                    imageView.setBackgroundColor(Color.GREEN);
                } else {
                    imageView.setBackgroundColor(Color.RED);
                }

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        (int) dimensions.width / 10,
                        (int) dimensions.height);
                params.leftMargin = params.width * i;
                params.topMargin = 0;

                imageView.setLayoutParams(params);
                relativeLayout.addView(imageView);
            }

            if (maxValue == 0) {
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(R.mipmap.ic_launcher);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        (int) dimensions.width / 10,
                        (int) dimensions.height);
                params.leftMargin = 0;
                params.topMargin = 0;

                imageView.setLayoutParams(params);
                imageView.setVisibility(INVISIBLE);
                relativeLayout.addView(imageView);
            }
        }
    }
}

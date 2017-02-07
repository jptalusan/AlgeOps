package com.freelance.jptalusan.algeops;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.freelance.jptalusan.algeops.Utilities.Constants;

/**
 * Created by JPTalusan on 29/01/2017.
 */

//http://stackoverflow.com/questions/22779422/custom-view-extending-relative-layout
public class AlgeOpsRelativeLayout extends RelativeLayout {
    private final static String TAG = "AlgeOpsRelLayout";
    public int currVal = 0;
    private AlgeOpsRelativeLayout algeOpsRelativeLayout = this;
    public Dimensions dimensions = new Dimensions();
    private int rows = 0;
    private int cols = 0;
    private int maxChildren = 0;

    public AlgeOpsRelativeLayout(Context context) {
        super(context);
    }

    public AlgeOpsRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AlgeOpsRelativeLayoutOptions, 0, 0);
        rows = a.getInt(R.styleable.AlgeOpsRelativeLayoutOptions_rows, 0);
        cols = a.getInt(R.styleable.AlgeOpsRelativeLayoutOptions_cols, 0);
        maxChildren = rows * cols;
    }

    public AlgeOpsRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AlgeOpsRelativeLayoutOptions, 0, 0);
        rows = a.getInt(R.styleable.AlgeOpsRelativeLayoutOptions_rows, 0);
        cols = a.getInt(R.styleable.AlgeOpsRelativeLayoutOptions_cols, 0);
        maxChildren = rows * cols;
    }

    public void getViewDimensions() {
        algeOpsRelativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                algeOpsRelativeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                dimensions.width  = algeOpsRelativeLayout.getMeasuredWidth();
                dimensions.height = algeOpsRelativeLayout.getMeasuredHeight();
            }
        });
    }

    public class Dimensions {
        double height;
        double width;

        @Override
        public String toString() {
            return "Dimensions{" +
                    "height=" + height +
                    ", width=" + width +
                    '}';
        }
    }

    //TODO: fix for sub. modulo problem
    public RelativeLayout.LayoutParams generateParams() {
        //TODO: move to get dimensions
        double scaledWidth = dimensions.width / cols;
        double scaledHeight = dimensions.height / rows;
        //(width, height)
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                (int) scaledWidth,
                (int) scaledHeight);

        int rowFactor = getChildCount() % rows;
        int colFactor = getChildCount() % cols;

        //TODO: Fix arrangement of how objects are added (simple math)
        double topMargin = rowFactor * scaledHeight;
        double leftMargin = colFactor * scaledHeight;

        Log.d(TAG, "Top: " + topMargin + ", Left: " + leftMargin);
        Log.d(TAG, "rowF: " + rowFactor + ", colF: " + colFactor);
        params.topMargin = (int) topMargin;
        params.leftMargin = (int) leftMargin;

        return params;
    }

    //TODO: Change drawables
    //TODO: Add new param or make use now of the addx, addone, subx, subone constants since in sub activity, only use 1 layout for all
    //TODO: need to add new handling to subtract/add either one or x.
    public void setImage(Context mContext, int mOperation) {
        if (Constants.OPS_ADD == mOperation && 0 <= currVal && getChildCount() < maxChildren) {
            //Add positive imageView drawable
            ImageView myImage = new ImageView(mContext);
            myImage.setImageResource(R.mipmap.ic_launcher);
            myImage.setLayoutParams(generateParams());
            addView(myImage);
            currVal += 1;
        } else if (Constants.OPS_ADD == mOperation && 0 > currVal) {
            if (getChildCount() > 0) {
                removeViewAt(getChildCount() - 1);
            }
            currVal += 1;
        }

        if (Constants.OPS_SUB == mOperation && 0 >= currVal && getChildCount() < maxChildren) {
            //Add positive imageView drawable
            ImageView myImage = new ImageView(mContext);
            myImage.setImageResource(R.drawable.chrome);
            myImage.setLayoutParams(generateParams());
            addView(myImage);
            currVal -= 1;
        } else if (Constants.OPS_SUB == mOperation && 0 < currVal) {
            if (getChildCount() > 0) {
                removeViewAt(getChildCount() - 1);
            }
            currVal -= 1;
        }
    }

    public void resetLayout() {
        this.removeAllViews();
        currVal = 0;
    }
}

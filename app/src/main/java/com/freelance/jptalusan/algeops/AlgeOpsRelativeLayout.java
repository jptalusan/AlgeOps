package com.freelance.jptalusan.algeops;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.freelance.jptalusan.algeops.Utilities.Constants;

/**
 * Created by JPTalusan on 29/01/2017.
 */

//http://stackoverflow.com/questions/22779422/custom-view-extending-relative-layout
public class AlgeOpsRelativeLayout extends RelativeLayout {
    private final static String TAG = "AlgeOpsRelLayout";
    public int currXVal = 0;
    public int currOneVal = 0;
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

        //TODO: Should factor in x and ones values before computing
        int rowFactor = (Math.abs(currXVal) + Math.abs(currOneVal)) / cols;
        int colFactor = (Math.abs(currXVal) + Math.abs(currOneVal)) % cols;

//        int rowFactor = getChildCount() / cols;
//        int colFactor = getChildCount() % cols;

        //TODO: Fix arrangement of how objects are added (simple math)
        double leftMargin = colFactor * scaledHeight;
        double topMargin = rowFactor * scaledHeight;

        Log.d(TAG, "Top: " + topMargin + ", Left: " + leftMargin);
        Log.d(TAG, "rowF: " + rowFactor + ", colF: " + colFactor);
        params.topMargin = (int) topMargin;
        params.leftMargin = (int) leftMargin;

        return params;
    }

    //TODO: Figure out how to implemement both x and 1 views in single layout (might have to use matrix)
    //TODO: Create new method since they are similar (if/else)
    public void setImage(Context mContext, int mOperation) {
        if (mOperation == Constants.OPS_ADD_X && currXVal >= 0 && getChildCount() < maxChildren) {
            AlgeOpsImageView opsImageView = new AlgeOpsImageView(mContext);
            opsImageView.setValue(Constants.OPS_ADD_X);
            opsImageView.setImageResource(R.mipmap.ic_launcher);
            opsImageView.setLayoutParams(generateParams());
            addView(opsImageView);
            currXVal += 1;
        } else if (mOperation == Constants.OPS_ADD_X && currXVal < 0) {
            if (getChildCount() > 0) { //unnecessary i think, but good to check
                for (int i = getChildCount(); i != 0; --i) {
                    AlgeOpsImageView temp = (AlgeOpsImageView) this.getChildAt(i - 1);
                    if (temp.getValue() == Constants.OPS_SUB_X) {
                        removeView(temp);
                        break;
                    }
                }
            }
            currXVal += 1;
        }

        if (mOperation == Constants.OPS_SUB_X && currXVal <= 0 && getChildCount() < maxChildren) {
            AlgeOpsImageView opsImageView = new AlgeOpsImageView(mContext);
            opsImageView.setValue(Constants.OPS_SUB_X);
            opsImageView.setImageResource(R.drawable.neglauncher);
            opsImageView.setLayoutParams(generateParams());
            addView(opsImageView);
            currXVal -= 1;
        } else if (mOperation == Constants.OPS_SUB_X && currXVal > 0) {
            if (getChildCount() > 0) { //unnecessary i think, but good to check
                for (int i = getChildCount(); i != 0; --i) {
                    AlgeOpsImageView temp = (AlgeOpsImageView) this.getChildAt(i - 1);
                    if (temp.getValue() == Constants.OPS_ADD_X) {
                        removeView(temp);
                        break;
                    }
                }
            }
            currXVal -=1;
        }

        if (mOperation == Constants.OPS_ADD_ONE && currOneVal >= 0 && getChildCount() < maxChildren) {
            AlgeOpsImageView opsImageView = new AlgeOpsImageView(mContext);
            opsImageView.setValue(Constants.OPS_ADD_ONE);
            opsImageView.setImageResource(R.drawable.chrome);
            opsImageView.setLayoutParams(generateParams());
            addView(opsImageView);
            currOneVal += 1;
        } else if (mOperation == Constants.OPS_ADD_ONE && currOneVal < 0) {
            if (getChildCount() > 0) { //unnecessary i think, but good to check
                for (int i = getChildCount(); i != 0; --i) {
                    AlgeOpsImageView temp = (AlgeOpsImageView) this.getChildAt(i - 1);
                    if (temp.getValue() == Constants.OPS_SUB_ONE) {
                        removeView(temp);
                        break;
                    }
                }
            }
            currOneVal += 1;
        }

        if (mOperation == Constants.OPS_SUB_ONE && currOneVal <= 0 && getChildCount() < maxChildren) {
            AlgeOpsImageView opsImageView = new AlgeOpsImageView(mContext);
            opsImageView.setValue(Constants.OPS_SUB_ONE);
            opsImageView.setImageResource(R.drawable.negchrome);
            opsImageView.setLayoutParams(generateParams());
            addView(opsImageView);
            currOneVal -= 1;
        } else if (mOperation == Constants.OPS_SUB_ONE && currOneVal > 0) {
            if (getChildCount() > 0) { //unnecessary i think, but good to check
                for (int i = getChildCount(); i != 0; --i) {
                    AlgeOpsImageView temp = (AlgeOpsImageView) this.getChildAt(i - 1);
                    if (temp.getValue() == Constants.OPS_ADD_ONE) {
                        removeView(temp);
                        break;
                    }
                }
            }
            currOneVal -= 1;
        }

        //TODO: Could add a refresh here to redraw all the images again this time at correct positions
    }

    public void resetLayout() {
        this.removeAllViews();
        currXVal = 0;
        currOneVal = 0;
    }
}

package com.freelance.jptalusan.algeops;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.freelance.jptalusan.algeops.Utilities.Constants;
import com.freelance.jptalusan.algeops.Utilities.Equation;

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

    public int positiveX;
    public int negativeX;
    public int positiveOne;
    public int negativeOne;

    public int initialValueForSub = 0;

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
//        int rowFactor = (Math.abs(currXVal) + Math.abs(currOneVal)) / cols;
//        int colFactor = (Math.abs(currXVal) + Math.abs(currOneVal)) % cols;

        int rowFactor = getChildCount() / cols;
        int colFactor = getChildCount() % cols;

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
            currXVal += 1;
        } else if (mOperation == Constants.OPS_ADD_X && currXVal < 0) {
            if (getChildCount() > 0) { //unnecessary i think, but good to check
                for (int i = getChildCount(); i != 0; --i) {
                    AlgeOpsImageView temp = (AlgeOpsImageView) this.getChildAt(i - 1);
                    if (temp.getValue() == Constants.OPS_SUB_X) {
                        removeView(temp);
                        currXVal += 1;
                        break;
                    }
                }
            }
        }

        if (mOperation == Constants.OPS_SUB_X && currXVal <= 0 && getChildCount() < maxChildren) {
            currXVal -= 1;
        } else if (mOperation == Constants.OPS_SUB_X && currXVal > 0) {
            if (getChildCount() > 0) { //unnecessary i think, but good to check
                for (int i = getChildCount(); i != 0; --i) {
                    AlgeOpsImageView temp = (AlgeOpsImageView) this.getChildAt(i - 1);
                    if (temp.getValue() == Constants.OPS_ADD_X) {
                        removeView(temp);
                        currXVal -=1;
                        break;
                    }
                }
            }
        }

        if (mOperation == Constants.OPS_ADD_ONE && currOneVal >= 0 && getChildCount() < maxChildren) {
            currOneVal += 1;
        } else if (mOperation == Constants.OPS_ADD_ONE && currOneVal < 0) {
            if (getChildCount() > 0) { //unnecessary i think, but good to check
                for (int i = getChildCount(); i != 0; --i) {
                    AlgeOpsImageView temp = (AlgeOpsImageView) this.getChildAt(i - 1);
                    if (temp.getValue() == Constants.OPS_SUB_ONE) {
                        removeView(temp);
                        currOneVal += 1;
                        break;
                    }
                }
            }
        }

        if (mOperation == Constants.OPS_SUB_ONE && currOneVal <= 0 && getChildCount() < maxChildren) {
            currOneVal -= 1;
        } else if (mOperation == Constants.OPS_SUB_ONE && currOneVal > 0) {
            if (getChildCount() > 0) { //unnecessary i think, but good to check
                for (int i = getChildCount(); i != 0; --i) {
                    AlgeOpsImageView temp = (AlgeOpsImageView) this.getChildAt(i - 1);
                    if (temp.getValue() == Constants.OPS_ADD_ONE) {
                        removeView(temp);
                        currOneVal -= 1;
                        break;
                    }
                }
            }
        }

        //TODO: Could add a refresh here to redraw all the images again this time at correct positions
        //If i remove it all here then i wouldn't have to do everything above. just get the currvals
        removeAllViews();
        for (int i = 0; i < Math.abs(currXVal); ++i) {
            if (currXVal > 0) {
                addImageToView(mContext, R.drawable.cube, Color.GREEN, Constants.OPS_ADD_X);
            } else {
                addImageToView(mContext, R.drawable.cube, Color.RED, Constants.OPS_SUB_X);
            }
        }

        for (int i = 0; i < Math.abs(currOneVal); ++i) {
            if (currOneVal > 0) {
                addImageToView(mContext, R.drawable.circle, Color.GREEN, Constants.OPS_ADD_ONE);
            } else {
                addImageToView(mContext, R.drawable.circle, Color.RED, Constants.OPS_SUB_ONE);
            }
        }
    }

    public boolean setSubImage(Context context, int operation) {
        if (operation == Constants.OPS_SUB_POS_X) {
            if (getChildCount() > initialValueForSub) { //unnecessary i think, but good to check
                for (int i = getChildCount(); i != initialValueForSub; --i) {
                    AlgeOpsImageView temp = (AlgeOpsImageView) this.getChildAt(i - 1);
                    if (temp.getValue() == Constants.POS_X) {
                        removeView(temp);
                        positiveX -= 1;
                        break;
                    }
                }
            } else {
                return false;
            }
        } else if (operation == Constants.OPS_SUB_NEG_X) {
            if (getChildCount() > initialValueForSub) { //unnecessary i think, but good to check
                for (int i = getChildCount(); i != initialValueForSub; --i) {
                    AlgeOpsImageView temp = (AlgeOpsImageView) this.getChildAt(i - 1);
                    if (temp.getValue() == Constants.NEG_X) {
                        removeView(temp);
                        negativeX -= 1;
                        break;
                    }
                }
            } else {
                return false;
            }
        } else if (operation == Constants.OPS_ADD_POS_NEG_X) {
            if (getChildCount() + 1 < maxChildren) {
                addImageToView(context, R.drawable.cube, Color.GREEN, Constants.POS_X);
                addImageToView(context, R.drawable.cube, Color.RED, Constants.NEG_X);

                positiveX += 1;
                negativeX += 1;
            } else {
                return false;
            }
        } else if (operation == Constants.OPS_SUB_POS_ONE) {
            if (getChildCount() > initialValueForSub) { //unnecessary i think, but good to check
                for (int i = getChildCount(); i != initialValueForSub; --i) {
                    AlgeOpsImageView temp = (AlgeOpsImageView) this.getChildAt(i - 1);
                    if (temp.getValue() == Constants.POS_ONE) {
                        removeView(temp);
                        positiveOne -= 1;
                        break;
                    }
                }
            } else {
                return false;
            }
        } else if (operation == Constants.OPS_SUB_NEG_ONE) {
            if (getChildCount() > initialValueForSub) { //unnecessary i think, but good to check
                for (int i = getChildCount(); i != initialValueForSub; --i) {
                    AlgeOpsImageView temp = (AlgeOpsImageView) this.getChildAt(i - 1);
                    if (temp.getValue() == Constants.NEG_ONE) {
                        removeView(temp);
                        negativeOne -= 1;
                        break;
                    }
                }
            } else {
                return false;
            }
        } else if (operation == Constants.OPS_ADD_POS_NEG_ONE) {
            if (getChildCount() + 1 < maxChildren) {
                addImageToView(context, R.drawable.circle, Color.GREEN, Constants.POS_ONE);
                addImageToView(context, R.drawable.circle, Color.RED, Constants.NEG_ONE);

                positiveOne += 1;
                negativeOne += 1;
            } else {
                return false;
            }
        }

        removeAllViews();
        for (int i = 0; i < positiveX; ++i) {
            addImageToView(context, R.drawable.cube, Color.GREEN, Constants.POS_X);
        }
        for (int i = 0; i < negativeX; ++i) {
            addImageToView(context, R.drawable.cube, Color.RED, Constants.NEG_X);
        }
        for (int i = 0; i < positiveOne; ++i) {
            addImageToView(context, R.drawable.circle, Color.GREEN, Constants.POS_ONE);
        }
        for (int i = 0; i < negativeOne; ++i) {
            addImageToView(context, R.drawable.circle, Color.RED, Constants.NEG_ONE);
        }
        return true;
    }

    //TODO: Why is this not working, not image is displayed
    public void populateImageViewBasedOnEq(Context context, Equation eq) {
        Log.d(TAG, "populate");
        int x = eq.getAx();
        int b = eq.getB();
        Log.d(TAG, x + "+" + b);

        for (int i = 0; i < Math.abs(x); ++i) {
            if (x > 0) {
                addImageToView(context, R.drawable.cube, Color.GREEN, Constants.POS_X);
                positiveX += 1;
            } else {
                addImageToView(context, R.drawable.cube, Color.RED, Constants.NEG_X);
                negativeX += 1;
            }
        }
        for (int i = 0; i < Math.abs(b); ++i) {
            if (b > 0) {
                addImageToView(context, R.drawable.circle, Color.GREEN, Constants.POS_ONE);
                positiveOne += 1;
            } else {
                addImageToView(context, R.drawable.circle, Color.RED, Constants.NEG_ONE);
                negativeOne += 1;
            }
        }
        initialValueForSub = getChildCount();
    }

    private void addImageToView(Context c, int drawable, int color, int value) {
        AlgeOpsImageView opsImageView = new AlgeOpsImageView(c);
        opsImageView.setValue(value);
        opsImageView.setImageResource(drawable);
        opsImageView.setBackgroundColor(color);
        opsImageView.setLayoutParams(generateParams());
        addView(opsImageView);
    }

    public void resetLayout() {
        this.removeAllViews();
        currXVal = 0;
        currOneVal = 0;
        positiveX = 0;
        negativeX = 0;
        positiveOne = 0;
        negativeOne = 0;
        initialValueForSub = 0;
    }
}

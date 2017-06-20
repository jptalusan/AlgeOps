package com.freelance.jptalusan.algeops;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.freelance.jptalusan.algeops.Utilities.Constants;
import com.freelance.jptalusan.algeops.Utilities.Dimensions;
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

    private double scaledWidth;
    private double scaledHeight;

    public AlgeOpsRelativeLayout(Context context) {
        super(context);
    }

    public AlgeOpsRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AlgeOpsRelativeLayoutOptions, 0, 0);
        rows = a.getInt(R.styleable.AlgeOpsRelativeLayoutOptions_rows, 0);
        cols = a.getInt(R.styleable.AlgeOpsRelativeLayoutOptions_cols, 0);
        a.recycle();
        maxChildren = rows * cols;
        listener = null;
    }

    public AlgeOpsRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AlgeOpsRelativeLayoutOptions, 0, 0);
        rows = a.getInt(R.styleable.AlgeOpsRelativeLayoutOptions_rows, 0);
        cols = a.getInt(R.styleable.AlgeOpsRelativeLayoutOptions_cols, 0);
        a.recycle();
        maxChildren = rows * cols;
        listener = null;
    }

    public void getViewDimensions() {
        algeOpsRelativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                algeOpsRelativeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                dimensions.width  = algeOpsRelativeLayout.getMeasuredWidth();
                dimensions.height = algeOpsRelativeLayout.getMeasuredHeight();

                scaledWidth = dimensions.width / cols;
                scaledHeight = dimensions.height / rows;
            }
        });
    }

    public RelativeLayout.LayoutParams generateParams() {
        //(width, height)
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                (int) scaledWidth,
                (int) scaledHeight);

        int rowFactor = getChildCount() / cols;
        int colFactor = getChildCount() % cols;

        double leftMargin = colFactor * scaledWidth;
        double topMargin = rowFactor * scaledHeight;

        params.topMargin = (int) topMargin;
        params.leftMargin = (int) leftMargin;

        return params;
    }

    public boolean removeSubImage(int type) {
        if (getChildCount() > 0) {
            for (int i = 0; i < getChildCount(); ++i) {
                AlgeOpsImageView temp = (AlgeOpsImageView) this.getChildAt(i);
                if (temp.getValue() == type) {
                    Log.d(TAG, "attempting to remove sub: " + (i));
                    fadeOut(temp, type);
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public boolean removeImage(int type) {
        if (getChildCount() > 0) {
            for (int i = getChildCount(); i != 0; --i) {
                AlgeOpsImageView temp = (AlgeOpsImageView) this.getChildAt(i - 1);
                if (temp.getValue() == type) {
                    Log.d(TAG, "attempting to remove.");
                    removeView(temp);
                    switch (type) {
                        case Constants.OPS_SUB_X:
                            currXVal += 1;
                            break;
                        case Constants.OPS_ADD_X:
                            currXVal -= 1;
                            break;
                        case Constants.OPS_SUB_ONE:
                            currOneVal += 1;
                            break;
                        case Constants.OPS_ADD_ONE:
                            currOneVal -= 1;
                            break;
                        case Constants.POS_X:
                            positiveX -= 1;
                            break;
                        case Constants.NEG_X:
                            negativeX -= 1;
                            break;
                        case Constants.POS_ONE:
                            positiveOne -= 1;
                            break;
                        case Constants.NEG_ONE:
                            negativeOne -= 1;
                            break;
                    }
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public boolean performOperation(AlgeOpsImageView iv, int type) {
        if (getChildCount() > 0) {
            Log.d(TAG, "attempting to remove.");
            removeView(iv);
            switch (type) {
                case Constants.OPS_SUB_X:
                    currXVal += 1;
                    break;
                case Constants.OPS_ADD_X:
                    currXVal -= 1;
                    break;
                case Constants.OPS_SUB_ONE:
                    currOneVal += 1;
                    break;
                case Constants.OPS_ADD_ONE:
                    currOneVal -= 1;
                    break;
                case Constants.POS_X:
                    positiveX -= 1;
                    break;
                case Constants.NEG_X:
                    negativeX -= 1;
                    break;
                case Constants.POS_ONE:
                    positiveOne -= 1;
                    break;
                case Constants.NEG_ONE:
                    negativeOne -= 1;
                    break;
            }
            return true;
        }
        return false;
    }

    public boolean performOperation(int i, int type) {
        if (getChildCount() > 0) {
            Log.d(TAG, "attempting to remove.");
            removeView(getChildAt(i));
            switch (type) {
                case Constants.OPS_SUB_X:
                    currXVal += 1;
                    break;
                case Constants.OPS_ADD_X:
                    currXVal -= 1;
                    break;
                case Constants.OPS_SUB_ONE:
                    currOneVal += 1;
                    break;
                case Constants.OPS_ADD_ONE:
                    currOneVal -= 1;
                    break;
                case Constants.POS_X:
                    positiveX -= 1;
                    break;
                case Constants.NEG_X:
                    negativeX -= 1;
                    break;
                case Constants.POS_ONE:
                    positiveOne -= 1;
                    break;
                case Constants.NEG_ONE:
                    negativeOne -= 1;
                    break;
            }
            return true;
        }
        return false;
    }

    public void fadeOut(int i) {
        final int temp = i;

        // 1
        ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0.0f);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                // 2
                getChildAt(0).setAlpha(value);
            }
        });

        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(2);
        animator.setDuration(750);
        animator.setStartDelay(i * 2000);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                performOperation(0, getObjectTypeInside());
                if (listener != null)
                    listener.onAnimationEnded(temp);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public void fadeOut(AlgeOpsImageView iv, int type) {
        final int temp = type;
        final AlgeOpsImageView iv_temp = iv;
        ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0.0f);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                // 2
                iv_temp.setAlpha(value);
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(3);
        animator.setDuration(750);
        animator.setStartDelay(0);
        animator.start();

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //removeImage(temp);
                performOperation(iv_temp, getObjectTypeInside());
                if (listener != null)
                    listener.onAnimationEnded(temp);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public interface AnimationEndListener {
        // These methods are the different events and
        // need to pass relevant arguments related to the event triggered
        // or when data has been loaded
        void onAnimationEnded(int val);
//        void onSubAnimationEnded(int val, int type);
    }

    private AnimationEndListener listener;

    // Assign the listener implementing events interface that will receive the events
    public void onAnimationEndListener(AnimationEndListener listener) {
        this.listener = listener;
    }

    //TODO: Add sub level restrictions
    public boolean setImage(Context mContext, int mOperation) {
        if (mOperation == Constants.OPS_ADD_X && currXVal >= 0 && getChildCount() < maxChildren) {
            currXVal += 1;
        } else if (mOperation == Constants.OPS_ADD_X && currXVal < 0) {
            removeImage(Constants.OPS_SUB_X);
        } else if (mOperation == Constants.OPS_ADD_X && currXVal == 6 && getChildCount() >= maxChildren) {
            return false;
        }

        if (mOperation == Constants.OPS_SUB_X && currXVal <= 0 && getChildCount() < maxChildren) {
            currXVal -= 1;
        } else if (mOperation == Constants.OPS_SUB_X && currXVal > 0) {
            removeImage(Constants.OPS_ADD_X);
        } else if (mOperation == Constants.OPS_SUB_X && currXVal == -6 && getChildCount() >= maxChildren) {
            return false;
        }

        if (mOperation == Constants.OPS_ADD_ONE && currOneVal >= 0 && getChildCount() < maxChildren) {
            currOneVal += 1;
        } else if (mOperation == Constants.OPS_ADD_ONE && currOneVal < 0) {
            removeImage(Constants.OPS_SUB_ONE);
        } else if (mOperation == Constants.OPS_ADD_ONE && currOneVal == 6 && getChildCount() >= maxChildren) {
            return false;
        }

        if (mOperation == Constants.OPS_SUB_ONE && currOneVal <= 0 && getChildCount() < maxChildren) {
            currOneVal -= 1;
        } else if (mOperation == Constants.OPS_SUB_ONE && currOneVal > 0) {
            removeImage(Constants.OPS_ADD_ONE);
        } else if (mOperation == Constants.OPS_SUB_ONE && currOneVal == -6 && getChildCount() >= maxChildren) {
            return false;
        }

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
        return true;
    }

    public boolean setSubImage(Context context, int operation) {
        if (operation == Constants.OPS_SUB_POS_X) {
            if (positiveX > 0) { //unnecessary i think, but good to check
                removeImage(Constants.POS_X);
            } else {
                return false;
            }
        } else if (operation == Constants.OPS_SUB_NEG_X) {
            if (negativeX > 0) { //unnecessary i think, but good to check
                removeImage(Constants.NEG_X);
            } else {
                return false;
            }
        } else if (operation == Constants.OPS_ADD_POS_NEG_X) {
            if (getChildCount() + 1 < maxChildren) {
//                addImageToView(context, R.drawable.cube, Color.GREEN, Constants.POS_X);
//                addImageToView(context, R.drawable.cube, Color.RED, Constants.NEG_X);

                positiveX += 1;
                negativeX += 1;
            } else {
                return false;
            }
        } else if (operation == Constants.OPS_SUB_POS_ONE) {
            if (positiveOne > 0) { //unnecessary i think, but good to check
                removeImage(Constants.POS_ONE);
            } else {
                return false;
            }
        } else if (operation == Constants.OPS_SUB_NEG_ONE) {
            if (negativeOne > 0) { //unnecessary i think, but good to check
                removeImage(Constants.NEG_ONE);
            } else {
                return false;
            }
        } else if (operation == Constants.OPS_ADD_POS_NEG_ONE) {
            if (getChildCount() + 1 < maxChildren) {
//                addImageToView(context, R.drawable.circle, Color.GREEN, Constants.POS_ONE);
//                addImageToView(context, R.drawable.circle, Color.RED, Constants.NEG_ONE);

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

    public boolean setSubImage(Context context, int operation, Equation eqs) {
        int ax = eqs.getAx();
        int b  = eqs.getB();
        int cx = eqs.getCx();
        int d  = eqs.getD();

        int tempX = ax - cx;
        int temp1 = b  - d;
        int limitPosX = 0;
        int limitNegX = 0;
        int limitPos1 = 0;
        int limitNeg1 = 0;
        if (tempX > 0) {
            limitPosX = Math.abs(tempX);
        } else {
            limitNegX = Math.abs(tempX);
        }

        if (temp1 > 0) {
            limitPos1 = Math.abs(temp1);
        } else {
            limitNeg1 = Math.abs(temp1);
        }

        Log.d(TAG, "+x,-x,+1,-1: " + limitPosX + "," + limitNegX + "," + limitPos1 + "," + limitNeg1);

        if (operation == Constants.OPS_SUB_POS_X) {
            if (positiveX > limitPosX) { //unnecessary i think, but good to check
                removeImage(Constants.POS_X);
            } else {
                return false;
            }
        } else if (operation == Constants.OPS_SUB_NEG_X) {
            if (negativeX > limitNegX) { //unnecessary i think, but good to check
                removeImage(Constants.NEG_X);
            } else {
                return false;
            }
        } else if (operation == Constants.OPS_SUB_POS_ONE) {
            if (positiveOne > limitPos1) { //unnecessary i think, but good to check
                removeImage(Constants.POS_ONE);
            } else {
                return false;
            }
        } else if (operation == Constants.OPS_SUB_NEG_ONE) {
            if (negativeOne > limitNeg1) { //unnecessary i think, but good to check
                removeImage(Constants.NEG_ONE);
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

    public void populateImageViewBasedOnLeftSideEq(Context context, Equation eq) {
        int x = eq.getAx();
        int b = eq.getB();

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
    }

    private void addImageToView(Context c, int drawable, int color, int value) {
        AlgeOpsImageView opsImageView = new AlgeOpsImageView(c);
        opsImageView.setValue(value);

        if (Color.GREEN == color) {
            if (R.drawable.cube == drawable) {
                opsImageView.setBackgroundResource(R.drawable.green_cube);
                opsImageView.setTextItem(c, "X");
            } else {
                opsImageView.setBackgroundResource(R.drawable.green_circle);
                opsImageView.setTextItem(c, "1");
            }
        } else if (Color.RED == color) {
            if (R.drawable.cube == drawable) {
                opsImageView.setBackgroundResource(R.drawable.red_cube);
                opsImageView.setTextItem(c, "X");
            } else {
                opsImageView.setBackgroundResource(R.drawable.red_circle);
                opsImageView.setTextItem(c, "1");
            }
        }
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
    }

    public int getObjectTypeInside() {
        if (this.getChildCount() > 0) {
            AlgeOpsImageView temp = (AlgeOpsImageView) getChildAt(0);
            return temp.getValue();
        } else {
            return -1;
        }
    }
}

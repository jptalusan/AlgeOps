package com.freelance.jptalusan.algeops;

import android.content.Context;
import android.util.AttributeSet;
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

    public AlgeOpsRelativeLayout(Context context) {
        super(context);
    }

    public AlgeOpsRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlgeOpsRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

    public static class Dimensions {
        public static double height;
        public static double width;

        @Override
        public String toString() {
            return "Dimensions{" +
                    "height=" + height +
                    ", width=" + width +
                    '}';
        }
    }

    public RelativeLayout.LayoutParams generateParams() {
        //(width, height)
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                (int) dimensions.width / 2,
                (int) dimensions.height / 3);

        switch (this.getChildCount()) {
            case 0:
                params.leftMargin = 0;
                params.topMargin  = 0;
                break;
            case 1:
                params.leftMargin = (int) dimensions.width / 2;
                params.topMargin  = 0;
                break;
            case 2:
                params.leftMargin = 0;
                params.topMargin  = (int) dimensions.height / 3;
                break;
            case 3:
                params.leftMargin = (int) dimensions.width  / 2;
                params.topMargin  = (int) dimensions.height / 3;
                break;
            case 4:
                params.leftMargin = 0;
                params.topMargin  = (int)((dimensions.height * 2) / 3);
                break;
            case 5:
                params.leftMargin = (int) dimensions.width / 2;
                params.topMargin  = (int) ((dimensions.height * 2) / 3);
                break;
            default:
                break;
        }

        return params;
    }

    //TODO: Change drawables
    public void setImage(Context mContext, int mOperation) {
        if (Constants.OPS_ADD == mOperation && 0 <= currVal && getChildCount() < 6) {
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

        if (Constants.OPS_SUB == mOperation && 0 >= currVal && getChildCount() < 6) {
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

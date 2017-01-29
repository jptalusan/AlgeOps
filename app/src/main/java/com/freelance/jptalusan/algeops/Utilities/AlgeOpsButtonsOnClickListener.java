package com.freelance.jptalusan.algeops.Utilities;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.freelance.jptalusan.algeops.AlgeOpsRelativeLayout;
import com.freelance.jptalusan.algeops.R;

/**
 * Created by JPTalusan on 29/01/2017.
 */

//TODO: probably better to put this in inner class of Add Activity or interface so it could be used in sub activity
public class AlgeOpsButtonsOnClickListener implements View.OnClickListener {
    private static final String TAG = "AlgeOpsOnClickListener";
    private Context mContext;
    private int mOperation;
    private AlgeOpsRelativeLayout mView;
    private AlgeOpsRelativeLayout.Dimensions rlDim = new AlgeOpsRelativeLayout.Dimensions();

    public AlgeOpsButtonsOnClickListener(Context context, int operation, AlgeOpsRelativeLayout view) {
        mContext = context;
        mOperation = operation;
        mView = view;
    }

    @Override
    public void onClick(View view) {
        //TODO: How expensive is this?
        rlDim = mView.dimensions;

        int currVal = mView.currVal;

        //TODO: probably should move these things to the Relativelayout class
        //TODO: instead, just place here the algorithm operations or in the other onclick the "Check" button.
        //TODO: place here checking each time of all the layouts if it has the correct answer. then make check button, seekbars visible (how to do without passing all layouts)
        if (Constants.OPS_ADD == mOperation && 0 <= currVal && mView.getChildCount() < 6) {
            //Add positive imageView drawable
            ImageView myImage = new ImageView(mContext);
            myImage.setImageResource(R.mipmap.ic_launcher);
            myImage.setLayoutParams(generateParams(mView.getChildCount()));
            mView.addView(myImage);
            currVal += 1;
        } else if (Constants.OPS_ADD == mOperation && 0 > currVal) {
            if (mView.getChildCount() > 0) {
                mView.removeViewAt(mView.getChildCount() - 1);
            }
            currVal += 1;
        }

        if (Constants.OPS_SUB == mOperation && 0 >= currVal && mView.getChildCount() < 6) {
            //Add positive imageView drawable
            ImageView myImage = new ImageView(mContext);
            myImage.setImageResource(R.drawable.chrome);
            myImage.setLayoutParams(generateParams(mView.getChildCount()));
            mView.addView(myImage);
            currVal -= 1;
        } else if (Constants.OPS_SUB == mOperation && 0 < currVal) {
            if (mView.getChildCount() > 0) {
                mView.removeViewAt(mView.getChildCount() - 1);
            }
            currVal -= 1;
        }

        mView.currVal = currVal;
        Log.d(TAG, "Currval: " + currVal);
        Log.d(TAG, rlDim.toString());
        Log.d(TAG, "Children: " + mView.getChildCount() + "");
    }

    //TODO: change height and width to exact dimensions of the grid in relative layout
    private RelativeLayout.LayoutParams generateParams(int currentChildCount) {
        //(width, height)
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                (int) rlDim.width / 2,
                (int) rlDim.height / 3);

        switch (currentChildCount) {
            case 0:
                params.leftMargin = 0;
                params.topMargin  = 0;
                break;
            case 1:
                params.leftMargin = (int) rlDim.width / 2;
                params.topMargin  = 0;
                break;
            case 2:
                params.leftMargin = 0;
                params.topMargin  = (int) rlDim.height / 3;
                break;
            case 3:
                params.leftMargin = (int) rlDim.width  / 2;
                params.topMargin  = (int) rlDim.height / 3;
                break;
            case 4:
                params.leftMargin = 0;
                params.topMargin  = (int)((rlDim.height * 2) / 3);
                break;
            case 5:
                params.leftMargin = (int) rlDim.width / 2;
                params.topMargin  = (int) ((rlDim.height * 2) / 3);
                break;
            default:
                break;
        }

        return params;
    }
}

package com.freelance.jptalusan.algeops.Utilities;

import android.os.Handler;
import android.util.Log;

import com.freelance.jptalusan.algeops.AlgeOpsRelativeLayout;

/**
 * Created by talusan on 2/19/2017.
 */

public class LayoutUtilities {
    private final static String TAG = "LayoutUtil";
    public static int getNumberOfViewsToRemove(AlgeOpsRelativeLayout left, AlgeOpsRelativeLayout right, int type) {
        int currValLeft = 0;
        int currValRight = 0;
        if (Constants.OPS_X == type) {
            currValLeft = left.currXVal;
            currValRight = right.currXVal;
        } else if (Constants.OPS_ONE == type){
            currValLeft = left.currOneVal;
            currValRight = right.currOneVal;
        }

        if (currValLeft == currValRight) {
            return 0;
        } else if ((currValLeft > 0 && currValRight < 0) ||
            (currValLeft < 0 && currValRight > 0)) {
            return Math.abs(currValLeft) > Math.abs(currValRight) ? Math.abs(currValRight) : Math.abs(currValLeft);
        }

        //Nothing to cancel
        return 0;
    }

    //Remove layout.init... if init is not included
    public static int getNumberOfViewsToRemove(AlgeOpsRelativeLayout layout, int type) {
        int pos = 0;
        int neg = 0;
        if (Constants.OPS_X == type) {
            pos = layout.positiveX + layout.initialPositiveX;
            neg = layout.negativeX + layout.initialNegativeX;
        } else if (Constants.OPS_ONE == type){
            pos = layout.positiveOne + layout.initialPositiveOne;
            neg = layout.negativeOne + layout.initialNegativeOne;
        }
        Log.d(TAG, "Pos: " + pos + ", Neg:" + neg);

        if (Math.abs(pos) == Math.abs(neg)) {
            return Math.abs(pos);
        } else {
            return Math.abs(pos) > Math.abs(neg) ? Math.abs(neg) : Math.abs(pos);
        }
    }

    public interface DelayCallback{
        void afterDelay();
    }

    public static void delay(int secs, final DelayCallback delayCallback){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                delayCallback.afterDelay();
            }
        }, secs * 1000); // afterDelay will be executed after (secs*1000) milliseconds.
    }
}

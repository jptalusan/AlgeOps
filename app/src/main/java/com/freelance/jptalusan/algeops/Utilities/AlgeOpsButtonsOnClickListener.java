package com.freelance.jptalusan.algeops.Utilities;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.freelance.jptalusan.algeops.R;

/**
 * Created by JPTalusan on 29/01/2017.
 */

public class AlgeOpsButtonsOnClickListener implements View.OnClickListener {
    private Context mContext;
    private int mOperation;
    private RelativeLayout mView;

    public AlgeOpsButtonsOnClickListener(Context context, int operation, RelativeLayout view) {
        mContext = context;
        mOperation = operation;
        mView = view;
    }

    @Override
    public void onClick(View view) {
        ImageView myImage = new ImageView(mContext);
        myImage.setImageResource(R.mipmap.ic_launcher);

        mView.addView(myImage);
    }
}

package com.freelance.jptalusan.algeops;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;

import com.freelance.jptalusan.algeops.Utilities.AutoResizeTextView;
import com.freelance.jptalusan.algeops.Utilities.Constants;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by JPTalusan on 08/02/2017.
 */

public class AlgeOpsImageView extends AutoResizeTextView {
    private int value = -1;
    protected SharedPreferences prefs;
    protected int level;

    public AlgeOpsImageView(Context context) {
        super(context);
    }

    public AlgeOpsImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AlgeOpsImageView, 0, 0);
        value = a.getInt(R.styleable.AlgeOpsImageView_value, 0);
    }

    public AlgeOpsImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AlgeOpsImageView, 0, 0);
        value = a.getInt(R.styleable.AlgeOpsImageView_value, 0);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setTextItem(String text) {
        prefs = getContext().getSharedPreferences(Constants.PREFS, MODE_PRIVATE);
        if (prefs.getInt(Constants.ADD_LEVEL, 1) == Constants.LEVEL_2 ||
                prefs.getInt(Constants.SUB_LEVEL, 1) >= Constants.LEVEL_2) {
            setText(text);
            setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        }

    }
}

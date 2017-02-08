package com.freelance.jptalusan.algeops;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by JPTalusan on 08/02/2017.
 */

public class AlgeOpsImageView extends ImageView {
    private int value = -1;

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
}

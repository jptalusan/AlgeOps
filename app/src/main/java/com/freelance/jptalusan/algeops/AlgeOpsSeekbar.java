package com.freelance.jptalusan.algeops;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;

import com.freelance.jptalusan.algeops.Utilities.Constants;

/**
 * Created by JPTalusan on 31/01/2017.
 */

public class AlgeOpsSeekbar extends SeekBar {

    protected int minimumValue = Constants.SEEKBAR_MIN;
    protected int maximumValue = Constants.SEEKBAR_MAX;

    protected OnSeekBarChangeListener listener;

    public AlgeOpsSeekbar(Context context){
        super(context);
        setUpInternalListener();
    }

    public AlgeOpsSeekbar(Context context, AttributeSet attrs){
        super(context, attrs);
        setUpInternalListener();
    }

    public AlgeOpsSeekbar(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        setUpInternalListener();
    }

    public void setMin(int min){
        this.minimumValue = min;
        super.setMax(maximumValue - minimumValue);
    }

    public void setMax(int max){
        this.maximumValue = max;
        super.setMax(maximumValue - minimumValue);
    }

    @Override
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener listener){
        this.listener = listener;
    }

    private void setUpInternalListener(){
        super.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(listener != null){
                    listener.onProgressChanged(seekBar, minimumValue + i, b);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if(listener != null)
                    listener.onStartTrackingTouch(seekBar);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(listener != null)
                    listener.onStopTrackingTouch(seekBar);
            }
        });
    }
}

package com.freelance.jptalusan.algeops;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
//https://github.com/anothem/android-range-seek-bar
//https://github.com/syedowaisali/crystal-range-seekbar <-- seems better
/**
 * Created by jtalusan on 2/7/2017.
 */

public class LayoutWithSeekBarView extends LinearLayout {
    private static final String TAG = "SeekbarView";
    private CrystalRangeSeekbar crystalRangeSeekbar;
    private RelativeLayout relativeLayout;

    public LayoutWithSeekBarView(Context context) {
        super(context);
        initializeViews(context);
    }

    public LayoutWithSeekBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public LayoutWithSeekBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    /**
     * Inflates the views in the layout.
     *
     * @param context
     *           the current context for the view.
     */
    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layoutwithseekbarview, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // Sets the images for the previous and next buttons. Uses
        // built-in images so you don't need to add images, but in
        // a real application your images should be in the
        // application package so they are always available.
        relativeLayout = (RelativeLayout) this.findViewById(R.id.relativeLayout);
        crystalRangeSeekbar = (CrystalRangeSeekbar)this.findViewById(R.id.seekbar);
        crystalRangeSeekbar.setLeftThumbDrawable(null);
        crystalRangeSeekbar.setLeftThumbHighlightDrawable(null);
        ImageView iv = new ImageView(this.getContext());
        iv.setImageResource(R.drawable.chrome);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(40, 40);
        iv.setLayoutParams(params);
        relativeLayout.addView(iv);
    }
}

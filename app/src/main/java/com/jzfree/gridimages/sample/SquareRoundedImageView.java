package com.jzfree.gridimages.sample;

import android.content.Context;
import android.util.AttributeSet;

import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by Wang Jiuzhou on 2018/6/14 19:44
 */
public class SquareRoundedImageView extends RoundedImageView {

    public SquareRoundedImageView(Context context) {
        super(context);
    }

    public SquareRoundedImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareRoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}

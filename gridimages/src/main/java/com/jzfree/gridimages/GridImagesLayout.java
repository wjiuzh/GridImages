package com.jzfree.gridimages;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by Wang Jiuzhou on 2018/6/23 22:35
 */
public class GridImagesLayout extends FrameLayout {
    private ImageView singleImageView;
    private GridLayout gridImageLayout;
    private Callback callback;
    private int singleImageMaxSize = 300; //px
    private int columnCount = 2;
    private int dividerWidth = 10;  //px

    public GridImagesLayout(Context context) {
        this(context, null);
    }

    public GridImagesLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridImagesLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GridImagesLayout);
        columnCount = a.getColor(R.styleable.GridImagesLayout_columnCount, 2);
        singleImageMaxSize = a.getDimensionPixelSize(R.styleable.GridImagesLayout_singleImageMaxSize, 300);
        dividerWidth = a.getDimensionPixelSize(R.styleable.GridImagesLayout_dividerWidth, 10);
        a.recycle();
        init();
    }

    private void init() {
        gridImageLayout = new GridLayout(getContext());
        gridImageLayout.setColumnCount(columnCount);
        gridImageLayout.setVisibility(GONE);
        LayoutParams gridLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WRAP_CONTENT);
        addView(gridImageLayout, gridLayoutParams);
    }

    public void setImageLoader(Callback imageLoader) {
        this.callback = imageLoader;
    }

    public void setColumnCount(int count) {
        columnCount = count;
        gridImageLayout.setColumnCount(columnCount);
    }

    public void setSingleImageMaxSize(int size) {
        this.singleImageMaxSize = size;
    }

    public void setDividerWidth(int dividerWidth) {
        this.dividerWidth = dividerWidth;
    }

    public void setImages(List<String> images) {
        if (images.isEmpty()) {
            if (singleImageView != null) {
                singleImageView.setVisibility(GONE);
            }
            gridImageLayout.setVisibility(GONE);
        } else if (images.size() == 1) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.width = WRAP_CONTENT;
            layoutParams.height = WRAP_CONTENT;
            setLayoutParams(layoutParams);

            if (callback != null) {
                singleImageView = callback.createSingleImageView();
            } else {
                singleImageView = new ImageView(getContext());
            }
            singleImageView.setAdjustViewBounds(true);
            singleImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            singleImageView.setVisibility(GONE);
            singleImageView.setMaxWidth(singleImageMaxSize);
            singleImageView.setMaxHeight((int) (singleImageMaxSize * 1.5));
            LayoutParams lp = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            addView(singleImageView, lp);

            if (callback != null) {
                callback.loadImage(images.get(0), singleImageView);
            }
            singleImageView.setVisibility(VISIBLE);
            gridImageLayout.setVisibility(GONE);
        } else {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.width = MATCH_PARENT;
            layoutParams.height = WRAP_CONTENT;
            setLayoutParams(layoutParams);

            if (singleImageView != null) {
                singleImageView.setVisibility(GONE);
            }
            gridImageLayout.setVisibility(VISIBLE);
            gridImageLayout.removeAllViews();
            for (int i = 0; i < images.size(); i++) {
                ImageView imageView;
                if (callback != null) {
                    imageView = callback.createGridImageView();
                } else {
                     imageView = new SquareImageView(getContext());
                }
                imageView.setAdjustViewBounds(true);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                GridLayout.Spec rowSpec = GridLayout.spec(i / columnCount, 1f);
                GridLayout.Spec columnSpec = GridLayout.spec(i % columnCount, 1f);
                GridLayout.LayoutParams lp = new GridLayout.LayoutParams(rowSpec, columnSpec);
                lp.width = 0;
                lp.height = 0;
                lp.bottomMargin = dividerWidth;
                lp.rightMargin = dividerWidth;
                if (i % columnCount == columnCount - 1) {
                    lp.rightMargin = 0;
                }
                if (i / columnCount == (images.size() / columnCount)) {
                    lp.bottomMargin = 0;
                }
                if (callback != null) {
                    callback.loadImage(images.get(i), imageView);
                }
                gridImageLayout.addView(imageView, lp);
            }
        }
    }

    public interface Callback {
        void loadImage(String url, ImageView imageView);

        ImageView createSingleImageView();

        ImageView createGridImageView();
    }
}

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
    private SquareImageView singleImageView;
    private GridLayout gridImageLayout;
    private ImageLoader imageLoader;
    private int singleImageSize = 300; //px
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
        singleImageSize = a.getDimensionPixelSize(R.styleable.GridImagesLayout_singleImageSize, 300);
        dividerWidth = a.getDimensionPixelSize(R.styleable.GridImagesLayout_dividerWidth, 10);
        a.recycle();
        init();
    }

    private void init() {
        singleImageView = new SquareImageView(getContext());
        singleImageView.setAdjustViewBounds(true);
        singleImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        singleImageView.setVisibility(GONE);
        LayoutParams layoutParams = new LayoutParams(singleImageSize, WRAP_CONTENT);
        addView(singleImageView, layoutParams);

        gridImageLayout = new GridLayout(getContext());
        gridImageLayout.setColumnCount(columnCount);
        gridImageLayout.setVisibility(GONE);
        LayoutParams gridLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WRAP_CONTENT);
        addView(gridImageLayout, gridLayoutParams);
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public void setColumnCount(int count) {
        columnCount = count;
        gridImageLayout.setColumnCount(columnCount);
    }

    public void setSingleImageSize(int size) {
        this.singleImageSize = size;
        LayoutParams layoutParams = new LayoutParams(singleImageSize, WRAP_CONTENT);
        removeView(singleImageView);
        addView(singleImageView, layoutParams);
    }

    public void setDividerWidth(int dividerWidth) {
        this.dividerWidth = dividerWidth;
    }

    public void setImages(List<String> images) {
        if (images.isEmpty()) {
            singleImageView.setVisibility(GONE);
            gridImageLayout.setVisibility(GONE);
        } else if (images.size() == 1) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.width = WRAP_CONTENT;
            layoutParams.height = WRAP_CONTENT;
            setLayoutParams(layoutParams);
            if (imageLoader != null) {
                imageLoader.loadImage(images.get(0), singleImageView);
            }
            singleImageView.setVisibility(VISIBLE);
            gridImageLayout.setVisibility(GONE);
        } else {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.width = MATCH_PARENT;
            layoutParams.height = WRAP_CONTENT;
            setLayoutParams(layoutParams);
            singleImageView.setVisibility(GONE);
            gridImageLayout.setVisibility(VISIBLE);
            gridImageLayout.removeAllViews();
            for (int i = 0; i < images.size(); i++) {
                SquareImageView imageView = new SquareImageView(getContext());
                imageView.setAdjustViewBounds(true);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                GridLayout.Spec rowSpec = GridLayout.spec(i / columnCount, 1f);
                GridLayout.Spec columnSpec = GridLayout.spec(i % columnCount, 1f);
                GridLayout.LayoutParams lp = new GridLayout.LayoutParams(rowSpec, columnSpec);
                lp.height = 0;
                lp.width = 0;
                lp.bottomMargin = dividerWidth;
                lp.rightMargin = dividerWidth;
                if (i % columnCount == columnCount - 1) {
                    lp.rightMargin = 0;
                }
                if (i / columnCount == (images.size() / columnCount)) {
                    lp.bottomMargin = 0;
                }
                if (imageLoader != null) {
                    imageLoader.loadImage(images.get(i), imageView);
                }
                gridImageLayout.addView(imageView, lp);
            }
        }
    }

    public interface ImageLoader {
        void loadImage(String url, ImageView imageView);
    }
}

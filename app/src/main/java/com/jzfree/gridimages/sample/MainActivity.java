package com.jzfree.gridimages.sample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jzfree.gridimages.GridImagesLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridImagesLayout gridImagesLayoutSingle = findViewById(R.id.gridimages_layout_single);
        gridImagesLayoutSingle.setColumnCount(3);
        gridImagesLayoutSingle.setDividerWidth(dp2px(this, 4));
        gridImagesLayoutSingle.setSingleImageMaxSize(dp2px(this, 200));
        gridImagesLayoutSingle.setImageLoader(new GridImagesLayout.ImageLoader() {
            @Override
            public void loadImage(String url, ImageView imageView) {
                Glide.with(MainActivity.this).load(url).into(imageView);
            }
        });

        List<String> images = new ArrayList<>();
        images.add("http://p93x5zjwb.bkt.clouddn.com/test_img_1.jpg");
        gridImagesLayoutSingle.setImages(images);



        GridImagesLayout gridImagesLayout = findViewById(R.id.gridimages_layout);
//        gridImagesLayout.setColumnCount(3);
//        gridImagesLayout.setDividerWidth(dp2px(this, 4));
//        gridImagesLayout.setSingleImageMaxSize(dp2px(this, 200));
        gridImagesLayout.setImageLoader(new GridImagesLayout.ImageLoader() {
            @Override
            public void loadImage(String url, ImageView imageView) {
                Glide.with(MainActivity.this).load(url).into(imageView);
            }
        });

        List<String> images2 = new ArrayList<>();
        images2.add("http://p93x5zjwb.bkt.clouddn.com/test_img_1.jpg");
        images2.add("http://p93x5zjwb.bkt.clouddn.com/test_img_2.jpg");
        images2.add("http://p93x5zjwb.bkt.clouddn.com/test_img_3.jpg");
        images2.add("http://p93x5zjwb.bkt.clouddn.com/test_img_4.jpg");
        images2.add("http://p93x5zjwb.bkt.clouddn.com/test_img_5.jpeg");
        images2.add("http://p93x5zjwb.bkt.clouddn.com/test_img_6.jpg");
        images2.add("http://p93x5zjwb.bkt.clouddn.com/test_img_7.jpg");
        gridImagesLayout.setImages(images2);
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

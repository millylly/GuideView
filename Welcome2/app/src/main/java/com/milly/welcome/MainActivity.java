package com.milly.welcome;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewpager;
    private LinearLayout ll_indicate;
    private int[] image_ids = new int[]{
            R.mipmap.navigation_1,
            R.mipmap.navigation_2,
            R.mipmap.navigation_3
    };
    private ImageView image;
    private int pointLen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewpager = (ViewPager) findViewById(R.id.viewpager);
        ll_indicate = (LinearLayout) findViewById(R.id.ll_indicate);
        image = (ImageView) findViewById(R.id.image);

        initIndicate();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter();
        viewpager.setAdapter(viewPagerAdapter);
//        viewpager.setPageTransformer(true,new DepthPageTransformer());
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int i = (int) (pointLen * (position + positionOffset));
                RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) image
                        .getLayoutParams();
                params.leftMargin = i;
                image.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initIndicate() {
        int num = image_ids.length;
        for (int i = 0; i < num; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(R.drawable.bg_solid);

            //设置小圆点的布局参数
            int pointSize = getResources().getDimensionPixelSize(R.dimen.point_size);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(pointSize, pointSize);

            if (i > 0) {
                layoutParams.leftMargin = getResources().getDimensionPixelOffset(R.dimen.point_left_length);
            }

            imageView.setLayoutParams(layoutParams);
            ll_indicate.addView(imageView);
        }

        ll_indicate.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ll_indicate.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //获取圆点之间的距离
                pointLen = ll_indicate.getChildAt(1).getLeft() - ll_indicate.getChildAt(0).getLeft();
            }
        });
    }

    class ViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return image_ids.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_welcome, null);
            ImageView image = (ImageView) view.findViewById(R.id.image);
            image.setBackgroundResource(image_ids[position]);
            container.addView(view);
            return view;
        }
    }
}

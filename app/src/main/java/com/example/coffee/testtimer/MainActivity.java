package com.example.coffee.testtimer;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private XBanner mBanner2;
    private List<TestActivity> imlist = new ArrayList<>();
    private List<FunctionalModuleEntity> rvIcon = new ArrayList<>();
    private RecyclerView rv_modular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_page);
        mBanner2 = findViewById(R.id.banner2);
        rv_modular = findViewById(R.id.rv_modular);

        initBanner(mBanner2);
        setBanner();
        setRv_icon();
    }

    /**
     * 初始化XBanner
     */
    private void initBanner(XBanner banner) {
        //设置广告图片点击事件
        banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                Toast.makeText(MainActivity.this, "点击了第" + (position + 1) + "图片", Toast.LENGTH_SHORT).show();
            }
        });
        //加载广告图片
        banner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                TestActivity testActivity = (TestActivity) model;
                String url = testActivity.getImurl();
//                Glide.with(MainActivity.this).load(url).into((ImageView) view);

                Glide.with(MainActivity.this)
                        .load(url)
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) //设置缓存
                        .into(new BitmapImageViewTarget((ImageView) view) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                super.setResource(resource);
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(MainActivity.this.getResources(), resource);
                                circularBitmapDrawable.setCornerRadius(10); //设置圆角弧度
                                view.setImageDrawable(circularBitmapDrawable);
                            }
                        });
            }
        });
    }

    private void setBanner() {
        imlist.add(new TestActivity("http://pic3.nipic.com/20090527/1242397_102231006_2.jpg"));
        imlist.add(new TestActivity("http://img3.3lian.com/2013/s1/51/d/114.jpg"));
        imlist.add(new TestActivity("http://img3.3lian.com/2013/s1/51/d/118.jpg"));
        imlist.add(new TestActivity("http://b-ssl.duitang.com/uploads/blog/201403/09/20140309132935_PQhtQ.thumb.700_0.jpeg"));
        //刷新数据之后，需要重新设置是否支持自动轮播
        mBanner2.setAutoPlayAble(imlist.size() > 1);
        mBanner2.setBannerData(imlist);
    }

    private void setRv_icon() {
        rvIcon.add(new FunctionalModuleEntity(R.drawable.icon_class_notice, "班级通知"));
        rvIcon.add(new FunctionalModuleEntity(R.drawable.icon_eight_point_reading, "八点阅读"));
        rvIcon.add(new FunctionalModuleEntity(R.drawable.icon_class_vote, "班级投票"));
        rvIcon.add(new FunctionalModuleEntity(R.drawable.icon_class_album, "班级相册"));
        rvIcon.add(new FunctionalModuleEntity(R.drawable.icon_class_course, "课程表"));
        rvIcon.add(new FunctionalModuleEntity(R.drawable.icon_school_mien, "校园风采"));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_modular.setLayoutManager(gridLayoutManager);
        DynamicPraiseUserAdapter adapter = new DynamicPraiseUserAdapter(this, rvIcon);
        rv_modular.setAdapter(adapter);
    }
}

package com.example.coffee.testtimer;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.coffee.testtimer.apapter.InformationAdapter;
import com.example.coffee.testtimer.entity.InformationEntity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private XBanner mBanner2;
    private List<TestActivity> imlist = new ArrayList<>();
    private List<FunctionalModuleEntity> rvIcon = new ArrayList<>();
    private RecyclerView rv_modular, rv_photo;
    private GridImageAdapter gridImageAdapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private int maxSelectNum = 9;
    private Dialog bottomDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_page);
        mBanner2 = findViewById(R.id.banner2);
        rv_modular = findViewById(R.id.rv_modular);
        rv_photo = findViewById(R.id.rv_photo);

        initBanner(mBanner2);
        setBanner();
        setRv_icon();
//        setPhotoAdapter();
        setInformationAdapter();
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(MainActivity.this);
                } else {
                    Toast.makeText(MainActivity.this,
                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
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
                        .asBitmap()
                        .load(url)
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
        adapter.setOnItemClickListenerBack(new onItemClickListenerBack() {
            @Override
            public void onItemClickListener(View view, int position) {
                switch (position) {
                    case 5:
                        Intent intent = new Intent(MainActivity.this, MineListActivity.class);
                        startActivity(intent);
                        break;
                }
            }

            @Override
            public void onItemLongClickListener(View view, int position) {

            }
        });
    }

    private void setPhotoAdapter() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(MainActivity.this, 4, GridLayoutManager.VERTICAL, false);
        rv_photo.setLayoutManager(manager);
        gridImageAdapter = new GridImageAdapter(MainActivity.this, onAddPicClickListener);
        gridImageAdapter.setList(selectList);
        gridImageAdapter.setSelectMax(maxSelectNum);
        rv_photo.setAdapter(gridImageAdapter);
        gridImageAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(MainActivity.this).themeStyle(R.style.picture_default_style).openExternalPreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(MainActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(MainActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
    }

    private void setInformationAdapter() {
        List<InformationEntity> informationEntities = new ArrayList<>();
        informationEntities.add(new InformationEntity("孩子没上过幼儿园，小学接收吗？", "现在一般的小学都要求孩子上过幼儿园，否则就不愿意接收孩子上学。就连我老家，", "https://pics7.baidu.com/feed/a9d3fd1f4134970a6c60173514a477cca7865d0f.jpeg?token=3c962ef5327fd052df185243892dc14b&s=DA937D8548737F86219054E30300E030"));
        informationEntities.add(new InformationEntity("国内的学前教育有哪些很明显的不足之处？", "对于中国学前教育的现状来说，有点“前卫”。现在大多数有在上幼儿园的孩子的", "https://pics0.baidu.com/feed/7c1ed21b0ef41bd56bca38c7d5b427cf38db3ddf.jpeg?token=651c067b8a1dd00bc64f99666f627f6d&s=8E2A6D8716613EA520B881A603005013"));
        informationEntities.add(new InformationEntity("家长该如何正确的面对孩子的错误和叛逆的行为呢？", "孩子在成长过程中总会犯错，而大人在面对种种错误，总忍不住想要发脾气，但其", "https://pics0.baidu.com/feed/caef76094b36acaf820600a9fdb72b1400e99c70.jpeg?token=936716faddd8e396f845df2243021298&s=7DA026D7165245D6D73DF5A80300F05A"));
        informationEntities.add(new InformationEntity("杭州天成教育集团五年级还有这样一个与众不同的孩子", "　钱江晚报记者在采访中发现，小董已经是五年级学生了，但不懂得英文，在英语上的学习更是吃力。不", "http://zhejiang.eol.cn/zhejiang_news/201904/W020190408327052489027.jpg"));
        InformationAdapter informationAdapter = new InformationAdapter(this, informationEntities, R.layout.item_infomation_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_photo.setLayoutManager(layoutManager);
        rv_photo.setAdapter(informationAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
                    }
                    gridImageAdapter.setList(selectList);
                    gridImageAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            show();
        }

    };

    //选择相机还是相册
    private void show() {
        bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.popup_bottom, null);
        Button PopupOk = (Button) contentView.findViewById(R.id.popup_ok);
        Button popup_xiangji = (Button) contentView.findViewById(R.id.popup_xiangji);
        PopupOk.setText("从相册中选择");
        popup_xiangji.setText("拍摄或录制");
        popup_xiangji.setVisibility(View.VISIBLE);
        Button PopupCancel = (Button) contentView.findViewById(R.id.popup_cancel);
        PopupOk.setOnClickListener(selectPhotoOnClickListener);
        PopupCancel.setOnClickListener(selectPhotoOnClickListener);
        popup_xiangji.setOnClickListener(selectPhotoOnClickListener);
        bottomDialog.setContentView(contentView);
        //老员工这样写的
        Window dialogWindow = bottomDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = -20; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        contentView.measure(0, 0);
        lp.height = contentView.getMeasuredHeight();
        dialogWindow.setAttributes(lp);

        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
//        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    View.OnClickListener selectPhotoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.popup_ok:
                    selectPhotoVoide(PictureMimeType.ofImage(), maxSelectNum, 4, false);
                    break;
                case R.id.popup_xiangji:
                    selectPhotoVoide(PictureMimeType.ofVideo(), maxSelectNum, 4, true);
                    break;
                case R.id.popup_cancel:
                    bottomDialog.cancel();
                    break;

            }
        }
    };

    private void selectPhotoVoide(int pictureMimeType, int maxSelectNum, int lineNum, boolean Single) {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(MainActivity.this)
                .openGallery(pictureMimeType)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(lineNum)// 每行显示个数
                .selectionMode(Single ?
                        PictureConfig.SINGLE : PictureConfig.MULTIPLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(true)// 是否可预览视频
                .enablePreviewAudio(true) // 是否可播放音频
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                .hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
//                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
//                .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
//                .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(false)// 是否开启点击声音
                .selectionMedia(selectList)// 是否传入已选图片
                //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled(true) // 裁剪是否可旋转图片
                //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }
}

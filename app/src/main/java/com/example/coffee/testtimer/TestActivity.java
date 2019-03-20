package com.example.coffee.testtimer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

public class TestActivity extends SimpleBannerInfo {
    private String imurl;

    public TestActivity(String imurl) {
        this.imurl = imurl;
    }

    @Override
    public Object getXBannerUrl() {
        return imurl;
    }

    public String getImurl() {
        return imurl;
    }
}

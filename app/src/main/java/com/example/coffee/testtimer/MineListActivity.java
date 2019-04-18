package com.example.coffee.testtimer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.coffee.testtimer.apapter.InformationAdapter;
import com.example.coffee.testtimer.entity.InformationEntity;

import java.util.ArrayList;
import java.util.List;

public class MineListActivity extends AppCompatActivity {
    private RecyclerView rv_mine_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_list);
        rv_mine_list = findViewById(R.id.rv_mine_list);
        setAdapter();
    }

    private void setAdapter() {
        List<InformationEntity> informationEntities = new ArrayList<>();
        informationEntities.add(new InformationEntity("孩子没上过幼儿园，小学接收吗？", "现在一般的小学都要求孩子上过幼儿园，否则就不愿意接收孩子上学。就连我老家，", "https://pics7.baidu.com/feed/a9d3fd1f4134970a6c60173514a477cca7865d0f.jpeg?token=3c962ef5327fd052df185243892dc14b&s=DA937D8548737F86219054E30300E030"));
        informationEntities.add(new InformationEntity("国内的学前教育有哪些很明显的不足之处？", "对于中国学前教育的现状来说，有点“前卫”。现在大多数有在上幼儿园的孩子的", "https://pics0.baidu.com/feed/7c1ed21b0ef41bd56bca38c7d5b427cf38db3ddf.jpeg?token=651c067b8a1dd00bc64f99666f627f6d&s=8E2A6D8716613EA520B881A603005013"));
        informationEntities.add(new InformationEntity("家长该如何正确的面对孩子的错误和叛逆的行为呢？", "孩子在成长过程中总会犯错，而大人在面对种种错误，总忍不住想要发脾气，但其", "https://pics0.baidu.com/feed/caef76094b36acaf820600a9fdb72b1400e99c70.jpeg?token=936716faddd8e396f845df2243021298&s=7DA026D7165245D6D73DF5A80300F05A"));
        informationEntities.add(new InformationEntity("杭州天成教育集团五年级还有这样一个与众不同的孩子", "　钱江晚报记者在采访中发现，小董已经是五年级学生了，但不懂得英文，在英语上的学习更是吃力。不", "http://zhejiang.eol.cn/zhejiang_news/201904/W020190408327052489027.jpg"));
        InformationAdapter informationAdapter = new InformationAdapter(this, informationEntities, R.layout.item_school_information);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_mine_list.setLayoutManager(layoutManager);
        rv_mine_list.setAdapter(informationAdapter);
    }
}

package com.example.yukisnote.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.yukisnote.R;
import com.example.yukisnote.common.Baseactivity;
import com.example.yukisnote.common.Constant;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends Baseactivity {


    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.github_btn) Button githubBtn;
    @Bind(R.id.advice_feedback) Button feedbackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initToolbar();
        initView();
    }


    public void initToolbar()
    {
        toolbar.setTitle(Constant.ABOUT);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    public void initView()
    {
        githubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                //TODO github地址
                Uri uri = Uri.parse("http://www.baidu.com");
                intent.setData(uri);
                startActivity(intent);
            }
        });
        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AboutActivity.this,FeedbackActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
           finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

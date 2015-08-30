package com.example.yukisnote.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.yukisnote.R;
import com.example.yukisnote.common.Baseactivity;
import com.example.yukisnote.common.Constant;
import com.example.yukisnote.util.PreferenceUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PersonalActivity extends Baseactivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.username) MaterialEditText userName;
    @Bind(R.id.sign) MaterialEditText sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);
        initToolbar();
        initView();
    }

    public void initView()
    {
        userName.setText(PreferenceUtils.getInstance(this).getStringParam(Constant.USER_NAME));
        sign.setText(PreferenceUtils.getInstance(this).getStringParam(Constant.SIGN));
    }


    public void initToolbar()
    {
        toolbar.setTitle(Constant.PERSONAL);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void savePersonalMessage()
    {
        PreferenceUtils.getInstance(this).saveParam(Constant.USER_NAME,userName.getText().toString());
        PreferenceUtils.getInstance(this).saveParam(Constant.SIGN,sign.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_personal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        else if(id == R.id.save)
        {
            savePersonalMessage();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

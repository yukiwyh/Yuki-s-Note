package com.example.yukisnote.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.yukisnote.R;
import com.example.yukisnote.common.Baseactivity;
import com.example.yukisnote.common.Constant;
import com.example.yukisnote.model.Advice;
import com.example.yukisnote.model.AdviceFeedback;
import com.example.yukisnote.network.RestClient;
import com.example.yukisnote.util.Util;
import com.nispok.snackbar.Snackbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FeedbackActivity extends Baseactivity {
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.advice) EditText adviceEdt;
    @Bind(R.id.contact) EditText contactEdt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        initToolbar();
    }


    public void initToolbar()
    {
        toolbar.setTitle(Constant.FEEDBACK);

        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        if(id == R.id.submit)
        {
            checkAdvice();
            submitAdvice();

        }

        return super.onOptionsItemSelected(item);
    }

    public void checkAdvice()
    {
        if(!adviceEdt.getText().toString().equals(""))
        {
            submitAdvice();
        }
        else
        {
            Snackbar snackbar = Snackbar.with(this);
            snackbar.text(Constant.TOAST_SUBMIT_ADVICE).show(this);
        }
    }

    public void submitAdvice()
    {
        Util.Log_wtf("submit yes");
        Advice advice = new Advice();
        advice.setAdviceContent(adviceEdt.getText().toString());
        advice.setContactInformation(contactEdt.getText().toString());
        RestClient.getInstance().sendAdvice(advice,new Callback<AdviceFeedback>() {
            @Override
            public void success(AdviceFeedback adviceFeedback, Response response) {
                Util.Log_wtf(response.getStatus() + "");
            }

            @Override
            public void failure(RetrofitError error) {
                Util.Log_wtf(error.getMessage());
            }
        });
    }
}

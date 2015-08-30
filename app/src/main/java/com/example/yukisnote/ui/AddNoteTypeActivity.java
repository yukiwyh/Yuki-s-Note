package com.example.yukisnote.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.yukisnote.R;
import com.example.yukisnote.common.Baseactivity;
import com.example.yukisnote.common.Constant;
import com.example.yukisnote.data.DrawMenuData;
import com.example.yukisnote.model.DrawMenu;
import com.nispok.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddNoteTypeActivity extends Baseactivity {


    @Bind(R.id.newtype) MaterialEditText newtype;
    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_type);
        ButterKnife.bind(this);
        initToolbar();
    }

    public void initToolbar()
    {
        toolbar.setTitle(Constant.NEW_TYPE);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_add_note_type, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            case R.id.save:
                saveNewType();
                if(getIntent().getStringExtra("fromHome").equals("yes"))
                {
                    AddNoteActivity.startEditNoteActivity(this,Constant.NO_POSITION,Constant.ADD);
                }
                finish();
        }

        return super.onOptionsItemSelected(item);
    }


    private void saveNewType()
    {
        if(newtype.getText().toString().equals(""))
        {
            Snackbar snackbar = Snackbar.with(this);
            snackbar.text(Constant.TOAST_ADD_NOTETYPE).show(this);
        }
        else
        {
            DrawMenu drawMenu = new DrawMenu(newtype.getText().toString(),0);
            drawMenu.save();
            DrawMenuData.initDisplayDrawMenuData();
        }
    }




}

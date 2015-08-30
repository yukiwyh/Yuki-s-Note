package com.example.yukisnote.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.yukisnote.R;
import com.example.yukisnote.adpater.NoteListAdapter;
import com.example.yukisnote.common.Baseactivity;
import com.example.yukisnote.common.Constant;
import com.example.yukisnote.data.DrawMenuData;
import com.example.yukisnote.model.DrawMenu;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TidyNoteActivity extends Baseactivity {


    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.notelist) ListView notelist;
    NoteListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tidy_note);
        ButterKnife.bind(this);
        initToolbar();
        initNoteList();
    }

    public void initNoteList()
    {
        DrawMenuData.findAllData();
        List<DrawMenu> list = DrawMenuData.drawMenuData;
        adapter = new NoteListAdapter(list,this,Constant.NORMAL);
        notelist.setAdapter(adapter);

    }

    public void initToolbar()
    {
        toolbar.setTitle(Constant.TIDY_NOTE);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.updateList();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tidy_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId())
       {
           case android.R.id.home:
               finish();
               break;
           case R.id.add:
               Intent i = new Intent(this,AddNoteTypeActivity.class);
               i.putExtra("fromHome","no");
               startActivity(i);
               break;

       }
        return super.onOptionsItemSelected(item);
    }
}

package com.example.yukisnote.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yukisnote.R;
import com.example.yukisnote.data.DataBaseHelper;
import com.example.yukisnote.model.DrawMenu;
import com.example.yukisnote.model.Note;
import com.example.yukisnote.ui.HomeActivity;
import com.example.yukisnote.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestDbActivity extends Activity {


    @Bind(R.id.createitem) Button btn;
    @Bind(R.id.testquery) Button btn1;
    @Bind(R.id.testedit) Button btn2;
    @Bind(R.id.starthome) Button btn3;
    @Bind(R.id.testdelete) Button btn4;
    @Bind(R.id.testdeleteall) Button btn5;
    @Bind(R.id.createdraw) Button btn6;
    @Bind(R.id.clean) Button btn7;
    @Bind(R.id.createnewdraw) Button btn8;
    @Bind(R.id.notetype) EditText edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_db);
        ButterKnife.bind(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testcreatenote();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testquery();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               testedit();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestDbActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testdelete();
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testdeleteAll();
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testcreatedraw();
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testcleanAll();
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDraw();
            }
        });
    }

    public void testcreatenote()
    {
        //Note.deleteAll(Note.class);
        ArrayList<DrawMenu> list = (ArrayList<DrawMenu>) DrawMenu.find(DrawMenu.class,"Type=?","学习笔记");

       // Note note = new Note("test","你好","12345",Integer.parseInt(list.get(0).getId()+""),1);
        /*note.save();*/
      //  DataBaseHelper.getInstance().createItem(note);
    }

    public void testquery()
    {
        List<Note> list = Note.find(Note.class, "content=?", "你好");
        for(int i = 0; i < list.size(); i++)
        {
            Util.Log_wtf(list.get(i).content);

            Util.Log_wtf(list.get(i).getId()+"");
        }
    }

    public void testedit()
    {
        long id = 19;
        Note note = Note.findById(Note.class,id);
        note.content = "ahahahhaha";
        note.title = "这是测试标题";
        DataBaseHelper.getInstance().editItem(note);
    }

    public void testdelete()
    {
        long id = 19;
        Note note = Note.findById(Note.class,id);
        DataBaseHelper.getInstance().deleteItem(note);
    }

    public void testdeleteAll()
    {
        Note.deleteAll(Note.class);
    }

    public void testcreatedraw()
    {
        DrawMenu.deleteAll(DrawMenu.class);
        DrawMenu drawMenu = new DrawMenu("学习笔记",0);
        drawMenu.save();
    }

    public void testcleanAll()
    {
        Note.deleteAll(Note.class);
        DrawMenu.deleteAll(DrawMenu.class);
    }

    public void createDraw()
    {
        DrawMenu drawMenu = new DrawMenu(edt.getText().toString(),0);
        drawMenu.save();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_db, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}

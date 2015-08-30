package com.example.yukisnote.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.yukisnote.R;
import com.example.yukisnote.common.Baseactivity;
import com.example.yukisnote.common.Constant;
import com.example.yukisnote.data.DrawMenuData;
import com.example.yukisnote.data.NoteData;
import com.example.yukisnote.model.DrawMenu;
import com.example.yukisnote.model.Note;
import com.example.yukisnote.util.TimeUtils;
import com.example.yukisnote.util.Util;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.ganfra.materialspinner.MaterialSpinner;

public class AddNoteActivity extends Baseactivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.title) MaterialEditText titleText;
    @Bind(R.id.content) MaterialEditText contentText;
    @Bind(R.id.spinner) MaterialSpinner spinner;
    @Bind(R.id.notetime) TextView time;

    MenuItem doneMenuItem;
    Intent intent;
    Note noteitem;
    String noteTitle = "";
    String noteContent = "";
    String noteType = "";
    ArrayAdapter<String> adapter;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_note);

        ButterKnife.bind(this);

        intent = getIntent();

        initSpinner();

        checkType();

        initToolBar();

    }


    public void initSpinner()
    {
        DrawMenuData.findAllData();
        int targetNoteTypePos = 0;
        String[] items = new String[DrawMenuData.drawMenuData.size()];
        for(int i = 0; i < DrawMenuData.drawMenuData.size(); i++)
        {
            items[i] = new String();
            items[i] = DrawMenuData.drawMenuData.get(i).Type;
            if(HomeActivity.noteTypeName.equals(items[i]))
            {
                targetNoteTypePos = i;
            }
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        spinner.setSelection(targetNoteTypePos);
    }

    private void checkType()
    {
        type = intent.getIntExtra("type",Constant.ADD);
        if(type == Constant.EDIT)
        {
            initView();
        }
    }

    /***
     *
     * @param context
     * from activity
     * @param position
     * 如果是编辑的话，此参数是item在list的位置
     * @param type
     * 新建笔记还是编辑笔记
     */
    public static void startEditNoteActivity(Context context,int position,int type)
    {
        Intent intent = new Intent(context,AddNoteActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    public void initView()
    {
        titleText.setText(NoteData.noteData.get(intent.getIntExtra("position",1)).title);
        contentText.setText(NoteData.noteData.get(intent.getIntExtra("position",1)).content);
        time.setText("编辑于  "+TimeUtils.getConciseTime(NoteData.noteData.get(intent.getIntExtra("position",1)).time,this));
        noteTitle = NoteData.noteData.get(intent.getIntExtra("position",1)).title;
        noteContent = NoteData.noteData.get(intent.getIntExtra("position",1)).content;
        noteType = DrawMenu.findById(DrawMenu.class, Long.parseLong(NoteData.noteData.get(intent.getIntExtra("position",1)).type+"")).Type;
        Util.Log_wtf(NoteData.noteData.get(intent.getIntExtra("position",1)).type+"");


       /* Util.Log_wtf(noteType);
        spinner.setSelection(adapter.getPosition(noteType));
     */
    }

    public void initToolBar()
    {
        toolbar.setTitle(Constant.NEW_NOTE);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titleText.addTextChangedListener(new ContentWatcher());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_note, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        doneMenuItem = menu.getItem(0);
        if(type == Constant.ADD) {
            doneMenuItem.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       switch (item.getItemId())
       {
           case android.R.id.home:
               //TODO  判断现有的文字状态，状态改变进行保存提示
               checkNoteStatus();
               break;
           case R.id.save:
               saveNote();
               finish();
               break;
           default:
               break;
       }
        return super.onOptionsItemSelected(item);
    }

    public void checkNoteStatus()
    {
        if(!noteTitle.equals(titleText.getText().toString())||!noteContent.equals(contentText.getText().toString()))
        {
            showAlertdialog();
            Util.Log_wtf("change");
        }
        else
        {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        checkNoteStatus();
    }

    public void showAlertdialog()
    {

        AlertDialog.Builder saveNoteNotify = new AlertDialog.Builder(this);
        saveNoteNotify.setTitle(Constant.DIALOG_TITLE);
        saveNoteNotify.setMessage(Constant.DIALOG_MESSAGE);
        saveNoteNotify.setPositiveButton(Constant.OK,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveNote();
                finish();
            }
        });
        saveNoteNotify.setNegativeButton(Constant.CANCEL,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        saveNoteNotify.show();
    }

    private void saveNote()
    {
        /*type is 1 edit , 2 is new add*/
        if(type == 1) {
            //noteitem = NoteData.noteData.get(intent.getIntExtra("position",1));
            noteitem = Note.findById(Note.class, NoteData.noteData.get(intent.getIntExtra("position", 1)).getId());
            noteitem.title = titleText.getText().toString();
            noteitem.content = contentText.getText().toString();
            noteitem.time = TimeUtils.getCurrentTimeInLong();
            ArrayList<DrawMenu> list = (ArrayList<DrawMenu>) DrawMenu.find(DrawMenu.class,"type=?",spinner.getSelectedItem().toString());
            noteitem.type = Integer.parseInt(list.get(0).getId()+"");
            noteitem.save();
        }
        else
        {
            //TODO 更改为选择类型
            ArrayList<DrawMenu> list = (ArrayList<DrawMenu>) DrawMenu.find(DrawMenu.class,"type=?",spinner.getSelectedItem().toString());
            Util.Log_wtf(spinner.getSelectedItem().toString());
            Util.Log_wtf(list.get(0).getId()+"");
            noteitem = new Note(titleText.getText().toString(),contentText.getText().toString(), TimeUtils.getCurrentTimeInLong(),
                    Integer.parseInt(list.get(0).getId()+""),1);
            noteitem.save();
        }
    }


    class ContentWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String content = titleText.getText().toString();
            if(content.equals("")) doneMenuItem.setVisible(false);
            else doneMenuItem.setVisible(true);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}

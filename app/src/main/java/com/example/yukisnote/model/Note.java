package com.example.yukisnote.model;

import com.orm.SugarRecord;

/**
 * Created by 钰辉 on 2015/8/13.
 */
public class Note extends SugarRecord<Note> {
    public String title;
    public String content;
    public long time;
    public int type;
    public int important_level;
    // public String color;

    public Note() {}
    public Note(String title,String content,long time,int type,int important_level)
    {
        this.title = title;
        this.content = content;
        this.time = time;
        this.type = type;
        this.important_level = important_level;



    }

}


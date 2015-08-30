package com.example.yukisnote.model;

import com.orm.SugarRecord;

/**
 * Created by 钰辉 on 2015/8/17.
 */
public class DrawMenu extends SugarRecord<DrawMenu> {

    public String Type;
    public int display;/* 0 is display*/
    public DrawMenu(){}
    public DrawMenu(String Type, int display)
    {
        this.Type = Type;
        this.display = display;
    }
}

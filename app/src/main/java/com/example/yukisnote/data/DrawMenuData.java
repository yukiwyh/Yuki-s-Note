package com.example.yukisnote.data;

import com.example.yukisnote.model.DrawMenu;
import com.example.yukisnote.util.Util;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by 钰辉 on 2015/8/17.
 */
public class DrawMenuData {
    public static ArrayList<DrawMenu> drawMenuData;
    public static ArrayList<DrawMenu> displayDrawMenuData;


    public static void findAllData()
    {
        Iterator<DrawMenu> itr = DrawMenu.findAll(DrawMenu.class);
        drawMenuData = new ArrayList<>();
        Util.Log_wtf(DrawMenu.findAll(DrawMenu.class).toString());
        while(itr.hasNext())
        {
            drawMenuData.add(itr.next());
        }
    }
    public static void initDisplayDrawMenuData()
    {
        displayDrawMenuData = (ArrayList<DrawMenu>) DrawMenu.find(DrawMenu.class, "display=?","0");
    }
}

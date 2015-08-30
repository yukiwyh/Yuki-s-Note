package com.example.yukisnote.data;

import com.orm.SugarRecord;

/**
 * Created by 钰辉 on 2015/8/17.
 */
public class Test extends SugarRecord<Test> {
    public String Type;
    public int display;

    public Test(){}

    public Test(String Type, int display)
    {
        this.Type = Type;
        this.display = display;
    }
}

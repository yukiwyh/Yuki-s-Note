package com.example.yukisnote.data;

import com.example.yukisnote.common.Constant;
import com.example.yukisnote.model.DrawMenu;
import com.example.yukisnote.model.Note;
import com.example.yukisnote.util.Util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by 钰辉 on 2015/8/17.
 */
public class DataBaseHelper {
   private static DataBaseHelper dataBaseHelper;
   private  DataBaseHelper()
   {}
   public static synchronized DataBaseHelper getInstance()
   {
       if(dataBaseHelper == null)
           dataBaseHelper = new DataBaseHelper();
       return dataBaseHelper;
   }

    public void createItem(Object note)
    {
        Note myNote = (Note) note;
        myNote.save();
    }

    public void createItem(Class<?> c,Object obj)
    {
        String name = c.getSimpleName();
        switch (name)
        {
            case Constant.Note:
                Note note = (Note) obj;
                note.save();
                break;
            case Constant.DrawMenu:
                DrawMenu drawMenu = (DrawMenu) obj;
                drawMenu.save();
                break;
        }
    }

    public void editItem(Object note)
    {
        Note Mynote = (Note) note;
        Mynote.content = ((Note) note).content;
        Mynote.title = ((Note) note).title;
        Mynote.save();

    }

    public void saveItem(Object note)
    {
        Note Mynote = (Note) note;
        Mynote.content = ((Note) note).content;
        Mynote.title = ((Note) note).title;
        Mynote.save();
    }

    public void deleteItem(Object note)
    {
        Note Mynote = (Note)note;
        Mynote.delete();
    }

    public void deleteAll(Class c)
    {
        try {
            Method method = c.getMethod("deleteAll");
            method.invoke(Note.class);
            Util.Log_wtf("success");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

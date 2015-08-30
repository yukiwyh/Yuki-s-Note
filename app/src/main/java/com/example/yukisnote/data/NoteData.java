package com.example.yukisnote.data;


import com.example.yukisnote.model.Note;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by 钰辉 on 2015/8/13.
 */
public class NoteData {
    public static ArrayList<Note> noteData;

    public static void initdata() {
        noteData = new ArrayList<>();
        Iterator<Note> itr = Note.findAll(Note.class);
        while(itr.hasNext())
        {
            noteData.add(itr.next());
        }
    }

    public static void queryNoteByType(int type)
    {
        if(type == -1)
        {
            initdata();
            return;
        }
        noteData = (ArrayList<Note>) Note.find(Note.class,"type=?",type+"");
    }

}


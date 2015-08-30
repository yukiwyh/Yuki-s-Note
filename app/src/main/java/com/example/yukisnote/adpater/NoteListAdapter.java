package com.example.yukisnote.adpater;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.yukisnote.R;
import com.example.yukisnote.common.Constant;
import com.example.yukisnote.data.DrawMenuData;
import com.example.yukisnote.model.DrawMenu;
import com.example.yukisnote.model.Note;
import com.example.yukisnote.ui.HomeActivity;
import com.example.yukisnote.util.Util;
import com.gc.materialdesign.views.ButtonRectangle;

import java.util.List;

/**
 * Created by 钰辉 on 2015/8/18.
 */
public class NoteListAdapter extends BaseAdapter {

    private List<DrawMenu> list;
    private Context mContext;
    private LayoutInflater inflater;
    private int showType;

    public NoteListAdapter(List<DrawMenu> list, Context mContext,int showType) {
        this.showType = showType;
        this.list = list;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(this.showType == Constant.NORMAL) {
            ViewHolder viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.notelist_item, null);
            viewHolder.noteName = (TextView) convertView.findViewById(R.id.notename);
            viewHolder.isDispaly = (SwitchCompat) convertView.findViewById(R.id.noteswitch);
            viewHolder.deleteNote = (ButtonRectangle) convertView.findViewById(R.id.deletenotetype);
            viewHolder.noteName.setText(list.get(position).Type);
            if (list.get(position).display == 0) {
                viewHolder.isDispaly.setChecked(true);
            }
            viewHolder.isDispaly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    CtrlSwitch(position, isChecked);
                }
            });
            viewHolder.deleteNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(position);
                }
            });
        }
        else
        {
            TextView noteType = new TextView(mContext);
            convertView = inflater.inflate(R.layout.item_notetype_popup,null);
            noteType = (TextView) convertView.findViewById(R.id.notename);
            noteType.setText(list.get(position).Type);
        }

        return convertView;
    }


    private void CtrlSwitch(int position, boolean isChecked) {
        if (isChecked == true) {
            redirectForDisplay(position);
            list.get(position).display = 0;
            list.get(position).save();
        } else {
            redirectForDisplay(position);
            list.get(position).display = 1;
            list.get(position).save();
        }
    }

    private void showDialog(final int position)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle(Constant.DIALOG_TITLE);
        dialog.setMessage(Constant.DELETE_TYPE_MESSAGE);
        dialog.setPositiveButton(Constant.OK,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteNote(position);
            }
        });
        dialog.setNegativeButton(Constant.CANCEL,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }
    private void deleteNote(int position)
    {
       redirect(position);
       DrawMenu targetMenu = list.get(position);
       Long index = targetMenu.getId();
       Note.deleteAll(Note.class,"type=?",index+"");
       targetMenu.delete();
       Util.Log_wtf(targetMenu.Type);
       updateList();
    }
    /*
    * 删除操作和展示操作时候，使drawmenu正常显示;
    * */
    public void redirect(int position)
    {
        if(list.get(position).getId() == HomeActivity.type)
        {
            if(list.size() != 1)
            {
                if(position != list.size() - 1)
                {
                    HomeActivity.type = Integer.parseInt(list.get(position+1).getId()+"");
                    Util.Log_wtf("typeposition+1");
                }
                else
                {
                    HomeActivity.type = Integer.parseInt(list.get(0).getId()+"");
                    HomeActivity.selectPosition = 0;
                    Util.Log_wtf("typeposition+1");
                }
            }
        }
    }

    public void redirectForDisplay(int position)
    {
        int firstPos = findFirstPosition();
        if(firstPos>position)
        {
            Util.Log_wtf("fordisplay");
            HomeActivity.type = Integer.parseInt(list.get(position).getId()+"");
            return;
        }
        redirect(position);
    }

    public int findFirstPosition()
    {
        int pos = 0;
        for(int i = 0; i < list.size(); i++)
        {
            if(list.get(i).display == 0)
            {
                pos = i;
                break;
            }
        }
        return pos;
    }
    public void updateList()
    {
        DrawMenuData.findAllData();
        this.list = DrawMenuData.drawMenuData;
        notifyDataSetChanged();
    }
    class ViewHolder
    {
        TextView noteName;
        SwitchCompat isDispaly;
        ButtonRectangle deleteNote;
    }

}

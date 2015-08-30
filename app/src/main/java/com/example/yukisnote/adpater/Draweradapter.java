package com.example.yukisnote.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yukisnote.R;
import com.example.yukisnote.data.DrawMenuData;
import com.example.yukisnote.model.DrawMenu;
import com.example.yukisnote.ui.HomeActivity;

import java.util.List;

/**
 * Created by 钰辉 on 2015/8/9.
 */
public class Draweradapter extends BaseAdapter {

    private Context mContext;
    private List<DrawMenu> list;
    private LayoutInflater inflater;
    private final static int TYPE_COUNT = 2;
    private final static int TYPE_PERSONAL = 0;
    private final static int TYPE_NOTETYPE = 1;
    public Draweradapter(List<DrawMenu> list, Context context)
    {
        this.list = list;
        this.mContext = context;
        this.inflater = LayoutInflater.from(mContext);
    }

    public void updateData()
    {
        DrawMenuData.initDisplayDrawMenuData();
        this.list = DrawMenuData.displayDrawMenuData;
        notifyDataSetChanged();
    }

    public void AllData()
    {
        DrawMenuData.findAllData();
        this.list = DrawMenuData.drawMenuData;
        notifyDataSetChanged();
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
    public int getItemViewType(int position) {
        if(position == 0) return TYPE_PERSONAL;
        else return TYPE_NOTETYPE;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.drawer_item,null);
        if(position == HomeActivity.selectPosition)
        {
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.grey_light));
        }
        TextView noteType = (TextView) convertView.findViewById(R.id.note_type);
        noteType.setText(list.get(position).Type);
        return convertView;
    }
}

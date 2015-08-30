package com.example.yukisnote.adpater;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;

import com.example.yukisnote.R;
import com.example.yukisnote.common.Constant;
import com.example.yukisnote.data.NoteData;
import com.example.yukisnote.model.Note;
import com.example.yukisnote.ui.AddNoteActivity;
import com.example.yukisnote.util.TimeUtils;
import com.example.yukisnote.util.Util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 钰辉 on 2015/8/9.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> implements NoteClickListener {

    private List<Note> list;
    private int upDownFactor = 1;
    private boolean isShowScaleAnimate = false;
    private NoteClickListener nListener;
    private NoteMoreClickListener mListener;
    private  static Context mContext;

    public NoteAdapter(List<Note> list, Context mContext)
    {
        this.list = list;
        this.mContext = mContext;
    }
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item,viewGroup,false);

        return new ViewHolder(view,nListener,mListener);
    }

    public void setOnNoteClickListener(NoteClickListener nListener)
    {
        this.nListener = nListener;
    }

    public void setOnNoteMoreClickListener(NoteMoreClickListener mListener) {
        this.mListener = mListener;
    }


    /* bind data to viewholder */
    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder viewHolder, int i) {

        viewHolder.noteContent.setText(list.get(i).content);
        viewHolder.noteTitle.setText(list.get(i).title);
        viewHolder.time.setText(TimeUtils.getConciseTime(list.get(i).time,mContext));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    public Filter getFilter()
    {
        NoteData.initdata();
        this.list = NoteData.noteData;
        return new NoteFilter(this,list);
    }

    public void updateData()
    {
        NoteData.initdata();
        this.list = NoteData.noteData;
        notifyDataSetChanged();
    }

    public void changeData(int Type)
    {
        NoteData.queryNoteByType(Type);
        this.list = NoteData.noteData;
        notifyDataSetChanged();
    }

    public void setUpFactor()
    {
        upDownFactor = -1;
        isShowScaleAnimate = true;
    }
    public void setDownFactor(){
        upDownFactor = 1;
        isShowScaleAnimate = false;
    }

    public static void showPopupMenu(View view,String Type, final int position)
    {
        PopupMenu popupMenu = new PopupMenu(mContext,view);
        switch(Type)
        {
            case Constant.TRASH_TYPE:
                //TODO 添加回收站行为
                break;
            default:
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.edit:
                                AddNoteActivity.startEditNoteActivity(mContext,position,Constant.EDIT);
                                break;
                            case R.id.movetoother:
                                break;
                            case R.id.movetotrash:
                                break;
                        }
                        return true;
                    }
                });
                break;
        }
    }



    public static class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView noteTitle;
        TextView noteContent;
        TextView time;
        Button more;
        private NoteClickListener nListener;
        private NoteMoreClickListener mListener;

        public ViewHolder(View itemView) {
            super(itemView);
            noteTitle = (TextView) itemView.findViewById(R.id.notename);
            noteContent = (TextView) itemView.findViewById(R.id.notetext);
            time = (TextView) itemView.findViewById(R.id.time);
            more = (Button) itemView.findViewById(R.id.note_more);
        }
        public ViewHolder(View rootView,NoteClickListener nListener,NoteMoreClickListener mListener)
        {
            super(rootView);
            noteTitle = (TextView) itemView.findViewById(R.id.notename);
            noteContent = (TextView) itemView.findViewById(R.id.notetext);
            time = (TextView) itemView.findViewById(R.id.notetime);
            more = (Button) itemView.findViewById(R.id.note_more);
            this.nListener = nListener;
            this.mListener = mListener;
            rootView.setOnClickListener(this);
            more.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            /*Button "more"*/
            if(v.getId() == R.id.note_more)
            {
              mListener.InNoteMoreClick(v,getPosition());
            }
            else {
                if (this.nListener != null) {
                    nListener.onItemClick(v, getPosition());
                }
            }

        }

    }

    public class NoteFilter extends Filter
    {
        private NoteAdapter noteAdapter;
        private List<Note> noFilterList;
        private List<Note> filterList;

        public NoteFilter(NoteAdapter noteAdapter,List<Note> noFilterList)
        {
            this.noteAdapter = noteAdapter;
            this.noFilterList = new LinkedList<>(noFilterList);
            this.filterList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filterList.clear();
            FilterResults results = new FilterResults();
            Util.Log_wtf("filter  "+constraint.toString());
            if(constraint.equals(""))
            {
                filterList.addAll(noFilterList);
            }
            else
            {
                for(int i = 0; i < noFilterList.size(); i++)
                {
                    if(noFilterList.get(i).content.contains(constraint)||noFilterList.get(i).title.contains(constraint))
                    {
                        filterList.add(noFilterList.get(i));
                    }
                }
            }
            results.values = filterList;
            results.count = filterList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            noteAdapter.list.clear();
            noteAdapter.list.addAll((ArrayList<Note>)results.values);
            noteAdapter.notifyDataSetChanged();
            Util.Log_wtf("update yes");

        }
    }


}

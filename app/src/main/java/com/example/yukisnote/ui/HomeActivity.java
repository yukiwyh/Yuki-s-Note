package com.example.yukisnote.ui;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yukisnote.R;
import com.example.yukisnote.adpater.Draweradapter;
import com.example.yukisnote.adpater.NoteAdapter;
import com.example.yukisnote.adpater.NoteClickListener;
import com.example.yukisnote.adpater.NoteListAdapter;
import com.example.yukisnote.adpater.NoteMoreClickListener;
import com.example.yukisnote.common.Baseactivity;
import com.example.yukisnote.common.Constant;
import com.example.yukisnote.data.DrawMenuData;
import com.example.yukisnote.data.NoteData;
import com.example.yukisnote.model.DrawMenu;
import com.example.yukisnote.model.Note;
import com.example.yukisnote.util.PreferenceUtils;
import com.example.yukisnote.util.Util;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class HomeActivity extends Baseactivity implements SwipeRefreshLayout.OnRefreshListener, NoteMoreClickListener{


    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.note_recycler) RecyclerView noteView;
    @Bind(R.id.note_drawlayout) DrawerLayout drawerLayout;
    @Bind(R.id.left_drawer)  View drawLeft;
    @Bind(R.id.note_type_list) ListView noteTypeList;
    @Bind(R.id.note_add_fab) FloatingActionButton noteAdd;
    @Bind(R.id.note_swiperefresh) SwipeRefreshLayout refreshLayout;
    @Bind(R.id.personal_message) LinearLayout personalMessage;
    @Bind(R.id.tidyMenu) com.gc.materialdesign.views.Button tidyMenu;
    @Bind(R.id.username) TextView userName;
    @Bind(R.id.useravatorname) TextView userAvatorName;
    @Bind(R.id.sign) TextView sign;

    private SearchView searchView;
    private List<Note> list;
    private List<DrawMenu> drawerList;
    private ActionBarDrawerToggle mToggle;
    private static int backButtonType = 0; /*设置搜索时按下返回键时,笔记返回搜索时分类状态*/
    public static int type = -1;/*当前笔记类型*/
    public static int selectPosition = 0;
    private boolean rightmode = false;
    public static String noteTypeName = "";
    NoteAdapter mAdapter;
    Draweradapter mdrawadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initToolbar();
        initDrawerView();
        initRecyclerView();

    }


    @Override
    protected void onStart() {
        super.onStart();
        if(rightmode == PreferenceUtils.getInstance(this).getBooleanParam(getString(R.string.right_key)))
        {
           showMenuLocation(Gravity.START);
        }
        else {
            showMenuLocation(Gravity.END);
        }
        initDrawAvatorView();
    }



    private void showMenuLocation(int gravity)
    {
        DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) drawLeft.getLayoutParams();
        layoutParams.gravity = gravity;
        drawLeft.setLayoutParams(layoutParams);
    }

    public void initRecyclerView()
    {
        noteView.setLayoutManager((new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)));

        if(drawerList.size() != 0) {
            NoteData.queryNoteByType(Integer.parseInt(drawerList.get(0).getId() + ""));
        } else
        {
           NoteData.initdata();
        }

        mAdapter = new NoteAdapter(NoteData.noteData, this);

        SpacesItemDecoration decoration = new SpacesItemDecoration(30);

        noteView.setAdapter(mAdapter);

        noteView.addItemDecoration(decoration);

        mAdapter.setOnNoteClickListener(new NoteClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AddNoteActivity.startEditNoteActivity(HomeActivity.this,position,Constant.EDIT);
            }
        });
        mAdapter.setOnNoteMoreClickListener(new NoteMoreClickListener() {
            @Override
            public void InNoteMoreClick(View view, int position) {
                showPopupMenu(view,"haha",position);
            }
        });

        noteAdd.attachToRecyclerView(noteView,new ScrollDirectionListener() {
            @Override
            public void onScrollDown() {
                mAdapter.setDownFactor();
            }

            @Override
            public void onScrollUp() {
                mAdapter.setUpFactor();
            }
        });

        noteAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawMenuData.findAllData();
                if(DrawMenuData.drawMenuData.size() == 0)
                {
                    showNoNoteTypeDialog();
                }else
                {
                    AddNoteActivity.startEditNoteActivity(HomeActivity.this,Constant.NO_POSITION,Constant.ADD);
                }
            }
        });
        refreshLayout.setColorSchemeColors(R.color.blue_light);
        refreshLayout.setOnRefreshListener(this);
    }

    public void showNoNoteTypeDialog()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(Constant.DIALOG_TITLE);
        dialog.setMessage(Constant.ADD_NOTETYPE);
        dialog.setPositiveButton(Constant.OK,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(HomeActivity.this, AddNoteTypeActivity.class);
                i.putExtra("fromHome","yes");
                startActivity(i);
            }
        });
        dialog.setNegativeButton(Constant.CANCEL,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }


    public void initDrawerView()
    {
        initDrawPersonal();
        initDrawListView();
        initDrawAvatorView();
    }

    public void initDrawAvatorView()
    {
        String userNameTxt = PreferenceUtils.getInstance(this).getStringParam(Constant.USER_NAME);
        String signTxt = PreferenceUtils.getInstance(this).getStringParam(Constant.SIGN);
        if(userNameTxt.equals(""))
        {
            userName.setText(Constant.NO_USERNAME);
            userAvatorName.setText("昵");
        }
        else
        {
            userName.setText(userNameTxt);
            userAvatorName.setText(userNameTxt.substring(userNameTxt.length()-1));
        }

        if(signTxt.equals(""))
        {
            sign.setText(Constant.NO_SIGN);
        }
        else
        {
            sign.setText(signTxt);
        }

    }


    public void initDrawPersonal()
    {
        personalMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,PersonalActivity.class);
                startActivity(i);
            }
        });
    }

    public void initDrawListView() {

        /*init listview*/
        DrawMenuData.initDisplayDrawMenuData();
        drawerList = DrawMenuData.displayDrawMenuData;
        mdrawadapter = new Draweradapter(DrawMenuData.displayDrawMenuData, this);
        noteTypeList.setAdapter(mdrawadapter);
        noteTypeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectPosition = position;
                Util.Log_wtf(selectPosition+"");
                mdrawadapter.notifyDataSetChanged();
                mAdapter.changeData(Integer.parseInt(drawerList.get(position).getId()+""));
                toolbar.setTitle(DrawMenuData.displayDrawMenuData.get(position).Type);
                noteTypeName = DrawMenuData.displayDrawMenuData.get(position).Type;
                type = Integer.parseInt(DrawMenuData.displayDrawMenuData.get(position).getId()+"");/*保存当前笔记类型*/
                closeDrawer();
            }
        });


        /*init tidy button*/
        tidyMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(HomeActivity.this,TidyNoteActivity.class);
               startActivity(intent);
            }
        });
    }




    public void initToolbar()
    {
        toolbar.setTitle(Constant.APP_NAME);
        toolbar.collapseActionView();
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        //创建返回键，并实现打开关/闭监听
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.open,R.string.close);
        mToggle.syncState();
        drawerLayout.setDrawerListener(mToggle);
        drawerLayout.setScrimColor(getResources().getColor(R.color.drawer_scrim_color));/*菜单栏滑出是 主界面颜色*/
        if (rightmode){
            showMenuLocation(Gravity.END);
        }

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        mdrawadapter.updateData();
        drawerList = DrawMenuData.displayDrawMenuData;
        if(drawerList.size() == 0)
        {
            toolbar.setTitle(Constant.APP_NAME);
        }
        if(type == -1)
        {
            mAdapter.updateData();
            Util.Log_wtf("updateall yes");
        }
        else
        {
            mAdapter.changeData(type);
            toolbar.setTitle(DrawMenu.findById(DrawMenu.class,Long.parseLong(type+"")).Type);
            Util.Log_wtf("updatebytype yes");
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(drawLeft)) {
            closeDrawer();
        } else {
            finish();
        }
    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(drawLeft);
    }

    /*about v7 toolbar*/

    /***
     * init toolbar
     * @param menu
     * @return
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        MenuItem menuMore = menu.findItem(R.id.toolbar_more);
        menuMore.setVisible(true);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuSearch = menu.findItem(R.id.toolbar_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuSearch);
        ComponentName componentName = getComponentName();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(componentName));
        searchView.setQueryHint(Constant.SEARCH_NOTE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Util.Log_wtf("查询触发");
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //TODO  当搜索文字改变时,过滤信息
                backButtonType = 0;
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }

    /**
     * toolbar menu listener
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId())
        {
           /* case R.id.settings:
                intent = new Intent(this,SettingActivity.class);
                startActivity(intent);
                break;*/
            case R.id.about:
                intent = new Intent(this,AboutActivity.class);
                startActivity(intent);
                break;
            case  android.R.id.home:
                Util.Log_wtf("监听触发");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        Toast.makeText(this, "同步成功", Toast.LENGTH_SHORT).show();
        //refreshLayout.setRefreshing(false);
        refreshLayout.setRefreshing(true);
    }


    public void showPopupMenu(View view, final String Type, final int position)
    {
        PopupMenu popupMenu = new PopupMenu(this,view);
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
                        switch (item.getItemId()) {
                            case R.id.edit:
                                AddNoteActivity.startEditNoteActivity(HomeActivity.this, position, Constant.EDIT);
                                break;
                            case R.id.movetoother:
                                chooseTypeofNote(position);
                                break;
                            case R.id.movetotrash:
                                showDeleteDialog(position);
                                break;
                        }
                        return false;
                    }
                });
                break;
        }
    }

    private void chooseTypeofNote(final int noteposition)
    {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("选择分组");
        ListView typelist = new ListView(this);
        DrawMenuData.findAllData();
        final String[] items = new String[DrawMenuData.drawMenuData.size()];

        for(int i = 0; i < items.length; i++)
        {
           items[i] = DrawMenuData.drawMenuData.get(i).Type;
        }
        NoteListAdapter noteListAdapter = new NoteListAdapter(DrawMenuData.drawMenuData,this,Constant.POPUP_MENU);
        typelist.setAdapter(noteListAdapter);
        dialog.setView(typelist);
        final AlertDialog alertDialog = dialog.show();
        typelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<DrawMenu> list = (ArrayList<DrawMenu>) DrawMenu.find(DrawMenu.class, "type=?", items[position]);
                Note currentNote = NoteData.noteData.get(noteposition);
                currentNote.type = Integer.parseInt(list.get(0).getId() + "");
                currentNote.save();
                mAdapter.changeData(type);
                alertDialog.dismiss();
            }
        });
    }


    public void showDeleteDialog(final int position)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(Constant.DIALOG_TITLE);
        dialog.setMessage(Constant.DELETE_NOTE_MESSAGE);
        dialog.setPositiveButton(Constant.OK,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Util.Log_wtf(NoteData.noteData.get(position).title);
                Util.Log_wtf(NoteData.noteData.get(position).content);
                NoteData.noteData.get(position).delete();
                mAdapter.changeData(type);
            }
        });
        dialog.setNegativeButton(Constant.CANCEL,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }


    //TODO 测试接口是否弃用
    @Override
    public void InNoteMoreClick(View view, int position) {

    }



    /**
     * add itemdecoration to item
     */
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration
    {
        private int space;

        public SpacesItemDecoration(int space)
        {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            if(parent.getChildPosition(view) == parent.getChildCount()/2 ||parent.getChildPosition(view) == 0)

            {
                outRect.top = space;
            }
        }
    }
}

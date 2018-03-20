package com.searchdemo.demo.searchdemo.searchview;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.searchdemo.demo.searchdemo.R;

/**
 * @ Creator     :     chenchao
 * @ CreateDate  :     2018/3/19 0019 17:29
 * @ Description :     SearchDemo
 */

public class SearchView extends LinearLayout implements View.OnClickListener {

    private final Context                mContext;
    private       float                  mTextSearchSize;
    private       int                    mTextSearchColor;
    private       String                 mTextSearchHint;
    private       int                    mSearchBlockHeight;
    private       int                    mSearchBackground;
    private       HistorySQlitOpenHelper mHistorySQlitOpenHelper;
    private       SimpleCursorAdapter    mAdapter;
    private       ScrollListView         mScrollListView;
    private       TextView               mTv_clear;
    private       SQLiteDatabase         mDb;
    private       CustomerEditText       mEtSearch;
    private       IcallBack              icallBack;
    private       BcallBack              bcallBack;
    private       ImageView              mSearchBack;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initAttrs(context, attrs);
        init();
    }

    private void init() {
        //1.初始化布局view
        initView();
        //2.获取SQliteOpenHelper对象
        mHistorySQlitOpenHelper = new HistorySQlitOpenHelper(mContext);
        //3.每次进入界面查询一下历史记录
        queryHistory("");

        //4.监听“清空搜索历史”按钮
        mTv_clear.setOnClickListener(this);
        //5.监听 edittext输入内容后键盘上面的搜索按钮
        mEtSearch.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    //1.点击搜索键的时候把输入框中的keyword回调给调用者
                    if (icallBack != null) {
                        icallBack.SearchAciton(mEtSearch.getText().toString().trim());
                    }
                    //2.查询历史记录数据库是否包含这个keyword
                    boolean hasKeyword = hasKeyword(mEtSearch.getText().toString().trim());
                    //如果不包含这个记录则在数据库插入记录
                    if (!hasKeyword) {
                        insertHistory(mEtSearch.getText().toString().trim());
                        //插入数据后再刷新一下数据库
                        queryHistory("");
                    }
                }
                return false;
            }
        });
        //6.监听edittext的文本变化
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //每次输入keyword后，模糊查询一次数据库
                queryHistory(mEtSearch.getText().toString());
            }
        });
        //7.对历史记录列表listview的条目点击进行监听
        mScrollListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击条目后把该条目的keyword展示到输入框中
                //在赋值之前先把输入框的内容清空
//                mEtSearch.setText("");
                TextView textView = view.findViewById(android.R.id.text1);
                mEtSearch.setText(textView.getText().toString().trim());
            }
        });
        //8.对返回按钮的点击进行监听
        mSearchBack.setOnClickListener(this);
    }

    private void insertHistory(String keyWord) {
        mDb = mHistorySQlitOpenHelper.getWritableDatabase();
        mDb.execSQL("insert into records(name) values('" + keyWord + "')");
        mDb.close();
    }

    private boolean hasKeyword(String keyWord) {
        Cursor cursor = mHistorySQlitOpenHelper.getReadableDatabase().rawQuery("select id as _id,name from records where name=?", new String[]{keyWord});
        return cursor.moveToNext();
    }


    private void initView() {
        //1.初始化view
        LayoutInflater.from(mContext).inflate(R.layout.layout_searchview, this);
        mEtSearch = (CustomerEditText) findViewById(R.id.et_search);
        mScrollListView = (ScrollListView) findViewById(R.id.listView);
        mSearchBack = (ImageView) findViewById(R.id.search_back);
        LinearLayout searchBlock = findViewById(R.id.search_block);
        //赋值输入框数值
        mEtSearch.setTextColor(mTextSearchColor);
        mEtSearch.setTextSize(mTextSearchSize);
        mEtSearch.setHint(mTextSearchHint);
        //赋值输入框的高度
        LayoutParams layoutParams = (LayoutParams) searchBlock.getLayoutParams();
        layoutParams.height = mSearchBlockHeight;
        searchBlock.setLayoutParams(layoutParams);
        mEtSearch.setBackgroundColor(mSearchBackground);
        //历史记录删除按钮
        mTv_clear = (TextView) findViewById(R.id.tv_clear);
        //初始状态历史记录为空，则按钮不显示
        mTv_clear.setVisibility(INVISIBLE);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SearchView);
        mTextSearchSize = ta.getDimension(R.styleable.SearchView_textSearchSize, 10);
        mTextSearchColor = ta.getColor(R.styleable.SearchView_textSearchColor, 0);
        mTextSearchHint = ta.getString(R.styleable.SearchView_textSearchHint);
        mSearchBlockHeight = ta.getInteger(R.styleable.SearchView_searchBlockHeight, 10);
        mSearchBackground = ta.getColor(R.styleable.SearchView_searchBackground, 0);
        ta.recycle();
    }

    private void queryHistory(String keyWord) {
        mDb = mHistorySQlitOpenHelper.getWritableDatabase();
        Cursor cursor = mDb.rawQuery("select id as _id,name from records where name like '%" + keyWord + "%' order by id desc ", null);
        //创建list适配器
        mAdapter = new SimpleCursorAdapter(mContext, android.R.layout.simple_list_item_1, cursor, new String[]{"name"}, new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        //设置列表
        mScrollListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        // 当输入框为空 & 数据库中有搜索记录时，显示 "删除搜索记录"按钮
        if ("".equals(keyWord) && cursor.getCount() != 0) {
            mTv_clear.setVisibility(VISIBLE);
        } else {
            mTv_clear.setVisibility(INVISIBLE);
        }
//        cursor.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clear://清空历史记录
                clearHistory();
            queryHistory("");
                break;
            case R.id.search_back://返回按钮监听
                if (bcallBack != null) {
                    bcallBack.BackAciton();
                }
                break;
            default:
                break;
        }
    }

    private void clearHistory() {
        mDb = mHistorySQlitOpenHelper.getWritableDatabase();
        mDb.execSQL("delete from records");
        mDb.close();
        //同时隐藏清除历史记录按钮
        mTv_clear.setVisibility(INVISIBLE);
    }

    //对键盘搜索键进行监听
    public void setOnClickSearchListener(IcallBack icallBack) {
        this.icallBack = icallBack;
    }

    //对返回键进行监听
    public void setOnClickBackListener(BcallBack bcallBack) {
        this.bcallBack = bcallBack;
    }
}

package com.searchdemo.demo.searchdemo.searchview;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.searchdemo.demo.searchdemo.R;

/**
 * @ Creator     :     chenchao
 * @ CreateDate  :     2018/3/19 0019 15:58
 * @ Description :     SearchDemo
 */

public class CustomerEditText extends android.support.v7.widget.AppCompatEditText {

    private Drawable mSearch;
    private Drawable mClear;
    private Drawable mBack;

    public CustomerEditText(Context context) {
        this(context, null);
    }

    public CustomerEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    private void initView() {
        //1.初始化edittext
        //返回键
        mBack = getResources().getDrawable(R.drawable.back);
        //搜索图片
        mSearch = getResources().getDrawable(R.drawable.search);
        //清空图标
        mClear = getResources().getDrawable(R.drawable.clear);
        //设置搜索和清空图标的初始位置（初始状态清空图标不显示）
        setCompoundDrawablesWithIntrinsicBounds(mSearch, null, null, null);
        //设置padding
//        setCompoundDrawablePadding(20);
//        setPadding(0, 0, 20, 0);
    }

    //动态监听输入框控制删除图标的显示隐藏
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
            //获取焦点且字符串长度大于0
            showDeleteIcon(text.length() > 0 && hasFocus());
            Log.d("xxx","onTextChanged=="+text.length()+"hasFocus="+hasFocus());
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
            //获取焦点且字符串长度大于0
            showDeleteIcon(focused && length() > 0);
            Log.d("xxx","onFocusChanged=="+length()+"hasFocus="+focused);
    }

    private void showDeleteIcon(boolean b) {
        setCompoundDrawablesWithIntrinsicBounds(mSearch, null, b ? mClear : null, null);
    }

    //监听删除按钮的点击事件

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Drawable drawable = mClear;
                //判断用户点击的位置
                if (drawable != null && (event.getX() <= getWidth() - getPaddingRight()) && (event.getX() >= getWidth() - getPaddingRight() - drawable.getBounds().width())) {
                    //点击的坐标为清除图标的位置
                    setText("");
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}

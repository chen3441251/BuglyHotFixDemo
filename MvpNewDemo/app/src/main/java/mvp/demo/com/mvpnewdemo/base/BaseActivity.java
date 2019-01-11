package mvp.demo.com.mvpnewdemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import mvp.demo.com.mvpnewdemo.interfaces.IPresenter;
import mvp.demo.com.mvpnewdemo.interfaces.IView;

/**
 * @ Creator     :     chenchao
 * @ CreateDate  :     2019/1/11 0011 10:59
 * @ Description :     MvpNewDemo
 */
public abstract class BaseActivity<T extends IPresenter> extends AppCompatActivity implements IView {

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( getLayoutId());
        initView();
        initPresenter();
    }

    private void initPresenter() {
        mPresenter= createPresenter();
        //与view生命周期绑定
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    protected abstract T createPresenter();

    protected abstract void initView();

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //与view生命周期解绑
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}

package mvp.demo.com.mvpnewdemo.base;

import mvp.demo.com.mvpnewdemo.interfaces.IPresenter;
import mvp.demo.com.mvpnewdemo.interfaces.IView;

/**
 * @ Creator     :     chenchao
 * @ CreateDate  :     2019/1/10 0010 15:23
 * @ Description :     MvpNewDemo
 */
public abstract class BasePresenter<T extends IView> implements IPresenter<T> {
    protected T mView;

    @Override
    public void attachView(T view) {
        this.mView=view;
    }

    @Override
    public void detachView() {
        mView=null;
    }

    @Override
    public boolean isViewAttached() {
        return mView!=null;
    }
}

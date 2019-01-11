package mvp.demo.com.mvpnewdemo.interfaces;

/**
 * @ Creator     :     chenchao
 * @ CreateDate  :     2019/1/10 0010 15:20
 * @ Description :     presenter通用接口
 */
public interface IPresenter<T extends IView> {
    /**
     * 绑定view
     * @param view
     */
    void attachView(T view);

    /**
     * 解绑view
     */
    void detachView();

    /**
     * 判断view是否绑定
     * @return
     */
    boolean isViewAttached();
}

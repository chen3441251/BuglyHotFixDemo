package mvp.demo.com.mvpnewdemo.interfaces;

/**
 * @ Creator     :     chenchao
 * @ CreateDate  :     2019/1/10 0010 15:12
 * @ Description :     MvpNewDemo
 */
public interface ModelCallBack<K,V> {
    void onSuccess(K data);
    void onFail(V data);
}

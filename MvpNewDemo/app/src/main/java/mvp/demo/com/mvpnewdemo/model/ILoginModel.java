package mvp.demo.com.mvpnewdemo.model;

import mvp.demo.com.mvpnewdemo.interfaces.IModel;
import mvp.demo.com.mvpnewdemo.interfaces.ModelCallBack;

/**
 * @ Creator     :     chenchao
 * @ CreateDate  :     2019/1/10 0010 15:07
 * @ Description :     登陆页面model接口
 */
public interface ILoginModel extends IModel {
    /**
     * 登录方法
     * @param account
     * @param password
     * @param modelCallBack
     */
    void login(String account, String password, ModelCallBack modelCallBack);

    /**
     * 注册方法
     * @param account
     * @param password
     * @param modelCallBack
     */
    void register(String account, String password, ModelCallBack modelCallBack);
}

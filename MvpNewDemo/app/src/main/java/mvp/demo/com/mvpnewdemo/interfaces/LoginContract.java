package mvp.demo.com.mvpnewdemo.interfaces;

import mvp.demo.com.mvpnewdemo.bean.LoginInfo;

/**
 * @ Creator     :     chenchao
 * @ CreateDate  :     2019/1/10 0010 15:05
 * @ Description :     MvpNewDemo
 */
public interface LoginContract {
  interface ILoginPresenter {
        void login(String account,String password);
        void register(String account,String password);
    }
     interface ILoginView extends IView {
        void showLoginSuccess(LoginInfo loginInfo);
        void showLoginFail(String error);
        String getLoginAccount();
        String getLoginPassword();
        void showRegisterSuccess(String registerInfo);
        void showRegisterFail(String error);
        String getRegisterAccount();
        String getRegisterPassword();
    }
}

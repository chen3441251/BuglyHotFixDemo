package mvp.demo.com.mvpnewdemo.presenter;

import mvp.demo.com.mvpnewdemo.base.BasePresenter;
import mvp.demo.com.mvpnewdemo.bean.LoginInfo;
import mvp.demo.com.mvpnewdemo.interfaces.LoginContract;
import mvp.demo.com.mvpnewdemo.interfaces.ModelCallBack;
import mvp.demo.com.mvpnewdemo.model.LoginModel;

/**
 * @ Creator     :     chenchao
 * @ CreateDate  :     2019/1/10 0010 15:26
 * @ Description :     MvpNewDemo
 */
public class LoginPresenter extends BasePresenter<LoginContract.ILoginView> implements LoginContract.ILoginPresenter {

    private final LoginModel mLoginModel;

    public LoginPresenter() {
        //构造函数中创建LoginModel对象
        mLoginModel = new LoginModel();
    }

    @Override
    public void login(String account, String password) {
        //调用loginModel的方法
        mLoginModel.login(account, password, new ModelCallBack<LoginInfo, String>() {

            @Override
            public void onSuccess(LoginInfo data) {
                //view回调结果
                mView.showLoginSuccess(data);
            }

            @Override
            public void onFail(String data) {
                mView.showLoginFail(data);
            }
        });
    }

    @Override
    public void register(String account, String password) {
        mLoginModel.register(account, password, new ModelCallBack<String, String>() {
            @Override
            public void onSuccess(String data) {
                mView.showRegisterSuccess(data);
            }

            @Override
            public void onFail(String data) {
                mView.showRegisterFail(data);
            }
        });
    }
}

package mvp.demo.com.mvpnewdemo.model;

import android.text.TextUtils;

import mvp.demo.com.mvpnewdemo.bean.LoginInfo;
import mvp.demo.com.mvpnewdemo.interfaces.ModelCallBack;

/**
 * @ Creator     :     chenchao
 * @ CreateDate  :     2019/1/10 0010 15:15
 * @ Description :     登陆页面的model
 */
public class LoginModel implements ILoginModel {
    @Override
    public void login(String account, String password, ModelCallBack modelCallBack) {
        if(TextUtils.isEmpty(account)||TextUtils.isEmpty(password)){
            modelCallBack.onFail("账号或密码不能为空");
            return;
        }
        if(TextUtils.equals(account,"chenchao")&&TextUtils.equals(password,"1234567a")){
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setName("chenchao");
            loginInfo.setAddress("四川北路1666号");
            loginInfo.setAge(32);
            loginInfo.setSex("man");
            modelCallBack.onSuccess(loginInfo);
        }else {
            modelCallBack.onFail("账号或者密码错误");
        }

    }

    @Override
    public void register(String account, String password, ModelCallBack modelCallBack) {
        if(TextUtils.isEmpty(account)||TextUtils.isEmpty(password)){
            modelCallBack.onFail("账号或密码不能为空");
            return;
        }
        if(password.length()<7){
            modelCallBack.onFail("密码不能小于7位");
            return;
        }

        modelCallBack.onSuccess("注册成功：账号："+account+",,密码："+password);
    }
}

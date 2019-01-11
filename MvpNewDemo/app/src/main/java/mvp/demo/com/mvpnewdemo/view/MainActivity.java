package mvp.demo.com.mvpnewdemo.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import mvp.demo.com.mvpnewdemo.R;
import mvp.demo.com.mvpnewdemo.base.BaseActivity;
import mvp.demo.com.mvpnewdemo.bean.LoginInfo;
import mvp.demo.com.mvpnewdemo.interfaces.LoginContract;
import mvp.demo.com.mvpnewdemo.presenter.LoginPresenter;

public class MainActivity extends BaseActivity<LoginPresenter> implements LoginContract.ILoginView, View.OnClickListener {
    private EditText mEt_name;
    private EditText mEt_password;
    private Button mBtn_login;
    private TextView mTv_info;
    private EditText mEt_register_name;
    private EditText mEt_register_password;
    private Button mBtn_register;
    private TextView mTv_register_info;


    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initView() {
        mEt_name = findViewById(R.id.et_name);
        mEt_password = findViewById(R.id.et_password);
        mBtn_login = findViewById(R.id.btn_login);
        mTv_info = findViewById(R.id.tv_info);
        mEt_register_name = findViewById(R.id.et_register_name);
        mEt_register_password = findViewById(R.id.et_register_password);
        mBtn_register = findViewById(R.id.btn_register);
        mTv_register_info = findViewById(R.id.tv_register_info);
        mBtn_login.setOnClickListener(this);
        mBtn_register.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void showLoginSuccess(LoginInfo loginInfo) {
        mTv_info.setText(loginInfo.toString());
    }

    @Override
    public void showLoginFail(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getLoginAccount() {
        return mEt_name.getText().toString().trim();
    }

    @Override
    public String getLoginPassword() {
        return mEt_password.getText().toString().trim();
    }

    @Override
    public void showRegisterSuccess(String registerInfo) {
        mTv_register_info.setText(registerInfo);
    }

    @Override
    public void showRegisterFail(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getRegisterAccount() {
        return mEt_register_name.getText().toString().trim();
    }

    @Override
    public String getRegisterPassword() {
        return mEt_register_password.getText().toString().trim();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                //调用登录接口
                mPresenter.login(getLoginAccount(), getLoginPassword());
                break;
            case R.id.btn_register:
                //调用注册接口
                mPresenter.register(getRegisterAccount(),getRegisterPassword());
                break;
            default:
                break;
        }
    }
}

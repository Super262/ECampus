package com.qilu.qilu.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Toast;

import com.qilu.qilu.delegates.QiluDelegate;
import com.qilu.qilu.ec.R;
import com.qilu.qilu.net.RestClient;
import com.qilu.qilu.net.callback.IFailure;
import com.qilu.qilu.net.callback.ISuccess;

public class SignInDelegate extends QiluDelegate implements View.OnClickListener {

    private TextInputEditText mPhone = null;
    private TextInputEditText mPassword = null;
    private ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mPhone = $(R.id.edit_sign_in_phone_num);
        mPassword = $(R.id.edit_sign_in_password);
        $(R.id.btn_sign_in).setOnClickListener(this);
        $(R.id.tv_link_sign_up).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_sign_in) {
            onClickSignIn();
        } else if (i == R.id.tv_link_sign_up) {
            onClickLink();
        }
    }

    private void onClickLink() {
        getSupportDelegate().startWithPop(new SignUpDelegate());
    }

    private void onClickSignIn() {
        if (checkForm()) {
            final String phone= mPhone.getText().toString().trim();
            final String pwd=mPassword.getText().toString().trim();
            RestClient.builder()
                    .url("http://123.207.13.112:8080/qilu/Login/Verify")
                    .params("phoneNum",phone)
                    .params("passWord",pwd)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            System.out.println("response is"+" "+response);
                            SignHandler.onSignIn(phone,pwd,response, mISignListener);
                        }
                    })
                    .failure(new IFailure() {
                                 @Override
                                 public void onFailure() {
                                     Toast.makeText(getContext(), "网络错误",Toast.LENGTH_LONG).show();
                                 }
                             }
                    )
                    .loader(getContext())
                    .build()
                    .post();
        }
    }
    private boolean checkForm() {
        final String phone = mPhone.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();

        boolean isPass = true;

        if (phone.isEmpty() ||phone.length()!=11) {
            mPhone.setError("错误的手机号");
            isPass = false;
        } else {
            mPhone.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        return isPass;
    }
}

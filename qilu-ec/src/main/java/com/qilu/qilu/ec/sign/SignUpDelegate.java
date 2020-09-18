package com.qilu.qilu.ec.sign;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;
import com.qilu.qilu.delegates.QiluDelegate;
import com.qilu.qilu.ec.R;
import com.qilu.qilu.net.RestClient;
import com.qilu.qilu.net.callback.IFailure;
import com.qilu.qilu.net.callback.ISuccess;
import java.io.IOException;


public class SignUpDelegate extends QiluDelegate implements View.OnClickListener {
    private String DEFAULT_AVATAR_DATA=null;
    private final String[] mTypes=new String[]{"学生","其他教工","教室管理员","设备维护负责人"};
    private String selectedTypeIndex="0";
    private AppCompatTextView type_choser=null;
    private TextInputEditText mName = null;
    private TextInputEditText mCode = null;
    private TextInputEditText mPhone = null;
    private TextInputEditText mPassword = null;
    private TextInputEditText mRePassword = null;
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
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mName = $(R.id.edit_sign_up_name);
        mCode = $(R.id.edit_sign_up_work_code);
        mPhone = $(R.id.edit_sign_up_phone);
        mPassword = $(R.id.edit_sign_up_password);
        mRePassword = $(R.id.edit_sign_up_re_password);
        type_choser=$(R.id.select_sign_up_type);
        type_choser.setClickable(true);
        type_choser.setOnClickListener(this);
        $(R.id.btn_sign_up).setOnClickListener(this);
        $(R.id.tv_link_sign_in).setOnClickListener(this);

    }
    private boolean checkForm() {
        final String name = mName.getText().toString();
        final String code = mCode.getText().toString().trim();
        final String phone = mPhone.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();
        final String rePassword = mRePassword.getText().toString().trim();

        boolean isPass = true;

        if (name.isEmpty()||name.length()<2) {
            mName.setError("请输入姓名");
            isPass = false;
        } else {
            mName.setError(null);
        }

        if (code.isEmpty() || code.length()>12) {
            mCode.setError("错误的学工号");
            isPass = false;
        } else {
            mCode.setError(null);
        }

        if (phone.isEmpty() || phone.length() != 11) {
            mPhone.setError("手机号码错误");
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

        if (rePassword.isEmpty() || rePassword.length() < 6 || !(rePassword.equals(password))) {
            mRePassword.setError("密码验证错误");
            isPass = false;
        } else {
            mRePassword.setError(null);
        }

        return isPass;
    }
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_sign_up) {
            try {
                onClickSignUp();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (i == R.id.tv_link_sign_in) {
            onClickLink();
        }
        else if(i==R.id.select_sign_up_type){
            onClickSelectType();
        }
    }
    private void onClickLink() {
        getSupportDelegate().startWithPop(new SignInDelegate());
    }
    private void onClickSelectType(){
        getTypeDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String type=mTypes[which];
                type_choser.setText("注册类型："+type);
                selectedTypeIndex=which+"";
                dialog.cancel();
            }
        });
    }
    private void onClickSignUp() throws IOException {
        final String phone= mPhone.getText().toString();
        final String pwd=mPassword.getText().toString();
        if (checkForm()) {
            RestClient.builder()
                    .url("Register/createNewUser")
                    .params("phoneNum", mPhone.getText().toString().trim())
                    .params("realName", mName.getText().toString())
                    .params("passWord", mPassword.getText().toString().trim())
                    .params("userType",selectedTypeIndex)
                    .params("userId",mCode.getText().toString().trim())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            System.out.println(response);
                            SignHandler.onSignUp(response, mISignListener);
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            Toast.makeText(getContext(),"网络错误",Toast.LENGTH_LONG).show();
                        }
                    })
                    .loader(getContext())
                    .build()
                    .post();
        }
    }

    private void getTypeDialog(DialogInterface.OnClickListener listener){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setSingleChoiceItems(mTypes,0, listener);
        builder.show();
    }

}

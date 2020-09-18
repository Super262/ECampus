package com.qilu.qilu.ec.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.qilu.qilu.delegates.QiluDelegate;
import com.qilu.qilu.ec.R;
import com.qilu.qilu.net.RestClient;
import com.qilu.qilu.net.callback.ISuccess;
import com.qilu.qilu.util.storage.QiluPreference;

public class PhoneDelegate extends QiluDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_phone;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        ((AppCompatButton)$(R.id.btn_phone_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone_now=((AppCompatEditText)$(R.id.text_phone_submit)).getText().toString();
                final String phone_original= QiluPreference.getCustomAppProfile("phone");
                if(phone_now.length()!=11){
                    ((AppCompatEditText)$(R.id.text_phone_submit)).setError("无效的手机号");
                }else{
                    if(phone_now.equals(phone_original)){
                        ((AppCompatEditText)$(R.id.text_phone_submit)).setError("手机号重复");
                    }
                    else{
                        final String id=QiluPreference.getCustomAppProfile("userId");
                        RestClient.builder()
                                .url("AboutUser/updateUserInfo")
                                .params("columnName","userName")
                                .params("columnValue",phone_now)
                                .params("referredName","userId")
                                .params("referredValue",id)
                                .loader(getContext())
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        Toast.makeText(getContext(),"更新成功",Toast.LENGTH_LONG).show();
                                    }
                                })
                                .build()
                                .post();
                    }
                }
            }
        });
    }
}

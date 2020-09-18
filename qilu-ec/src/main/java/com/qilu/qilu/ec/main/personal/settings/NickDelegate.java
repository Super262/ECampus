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

public class NickDelegate extends QiluDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_nick;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        ((AppCompatButton)$(R.id.btn_nick_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nick_now=((AppCompatEditText)$(R.id.text_nick_submit)).getText().toString();
                final String nick_original= QiluPreference.getCustomAppProfile("nick");
                if(nick_now.equals(nick_original)){
                    ((AppCompatEditText)$(R.id.text_nick_submit)).setError("昵称重复");
                }
                else{
                    final String phone=QiluPreference.getCustomAppProfile("phone");
                        RestClient.builder()
                                .url("AboutUser/updateUserInfo")
                                .params("columnName","userName")
                                .params("columnValue",nick_now)
                                .params("referredName","phoneNum")
                                .params("referredValue",phone)
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
        });
    }
}

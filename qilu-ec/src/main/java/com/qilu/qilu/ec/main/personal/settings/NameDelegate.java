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

public class NameDelegate extends QiluDelegate {


    @Override
    public Object setLayout() {
        return R.layout.delegate_name;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        ((AppCompatButton)$(R.id.btn_name_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name_original= QiluPreference.getCustomAppProfile("name");
                final String name_now=((AppCompatEditText)$(R.id.edit_name_submit)).getText().toString();
                if(name_now.length()<2){
                    ((AppCompatEditText)$(R.id.edit_name_submit)).setError("姓名过短");
                }
                else {
                    if(name_now.equals(name_original)){
                        ((AppCompatEditText)$(R.id.edit_name_submit)).setError("姓名重复");
                    }
                    else{
                        final String phone=QiluPreference.getCustomAppProfile("phone");
                        RestClient.builder()
                                .url("AboutUser/updateUserInfo")
                                .params("columnName","realName")
                                .params("columnValue",name_now)
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
            }
        });
    }
}

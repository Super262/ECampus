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

public class CodeDelegate extends QiluDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_code;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        ((AppCompatButton)$(R.id.btn_code_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String code_now=((AppCompatEditText)$(R.id.text_code_submit)).getText().toString();
                final String code_original= QiluPreference.getCustomAppProfile("id");
                if(code_now.length()!=12){
                    ((AppCompatEditText)$(R.id.text_code_submit)).setError("不正确的学工号");
                }
                else {
                    if(code_now.equals(code_original)){
                        ((AppCompatEditText)$(R.id.text_code_submit)).setError("学工号重复");
                    }
                    else{
                        final String phone=QiluPreference.getCustomAppProfile("phone");
                        RestClient.builder()
                                .url("AboutUser/updateUserInfo")
                                .params("columnName","userId")
                                .params("columnValue",code_now)
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

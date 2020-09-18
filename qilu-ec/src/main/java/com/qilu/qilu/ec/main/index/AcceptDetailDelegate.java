package com.qilu.qilu.ec.main.index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.qilu.qilu.delegates.QiluDelegate;
import com.qilu.qilu.ec.R;
import com.qilu.qilu.net.RestClient;
import com.qilu.qilu.net.callback.ISuccess;
import com.qilu.qilu.util.storage.QiluPreference;

public class AcceptDetailDelegate extends QiluDelegate {
    final String state=TempDetailValue.getProValue().get("isAccepted");
    final String USER_TYPE= QiluPreference.getCustomAppProfile("type");
    final String pro_id=TempDetailValue.getProValue().get("pro_id");
    final String user_phone= QiluPreference.getCustomAppProfile("phone");
    final String user_name=QiluPreference.getCustomAppProfile("name");
    @Override
    public Object setLayout() {
        if(state.equals("true")){
            return R.layout.delegate_accept_detail_yes;
        }else{
            return R.layout.delegate_accept_detail_no;
        }

    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        if(state.equals("true")){
            ((AppCompatTextView)$(R.id.accept_detail_accept_name)).setText(TempDetailValue.getProValue().get("acceptedName"));
            ((AppCompatTextView)$(R.id.accept_detail_accept_phone)).setText(TempDetailValue.getProValue().get("acceptedPhoneNum"));
            ((AppCompatTextView)$(R.id.accept_detail_accept_type)).setText(TempDetailValue.getProValue().get("acceptedType"));
        }
        else{
            ((AppCompatButton)$(R.id.accept_detail_no_accept_btn)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String type=((TextInputEditText)$(R.id.accept_detail_no_type_text)).getText().toString();
                    if(!USER_TYPE.equals("0")){
                        if(type.isEmpty()){
                            ((TextInputEditText)$(R.id.accept_detail_no_type_text)).setError("受理类型为空");
                        }
                        else{
                            RestClient.builder()
                                    .url("Project/accept")
                                    .success(new ISuccess() {
                                        @Override
                                        public void onSuccess(String response) {
                                            Toast.makeText(getContext(),"受理成功",Toast.LENGTH_SHORT).show();
                                            onDestroy();
                                        }
                                    })
                                    .params("proId",pro_id)
                                    .params("phoneNum",user_phone)
                                    .params("type",type)
                                    .params("realName",user_name)
                                    .loader(getContext())
                                    .build()
                                    .post();
                        }
                    }
                    else{
                        Toast.makeText(getContext(),"无权受理",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}

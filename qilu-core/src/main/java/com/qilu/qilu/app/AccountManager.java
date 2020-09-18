package com.qilu.qilu.app;


import com.qilu.qilu.net.RestClient;
import com.qilu.qilu.net.callback.ISuccess;
import com.qilu.qilu.util.storage.QiluPreference;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 傅令杰 on 2017/4/22
 */

public class AccountManager {

    private enum SignTag {
        SIGN_TAG
    }

    //保存用户登录状态，登录后调用
    public static void setSignState(String phone,String pwd,boolean state) {
        if(state){
            QiluPreference.addCustomAppProfile("pwd",pwd);
            QiluPreference.addCustomAppProfile("phone",phone);
            QiluPreference.addCustomAppProfile("auto_play_audio","0");
            RestClient.builder().url("AboutUser/getUserInfo")
                    .params("phoneNum",phone)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                QiluPreference.addCustomAppProfile("id",jsonObject.getString("userId"));
                                QiluPreference.addCustomAppProfile("nick",jsonObject.getString("userName"));
                                QiluPreference.addCustomAppProfile("type",jsonObject.getString("userType"));
                                QiluPreference.addCustomAppProfile("name",jsonObject.getString("realName"));
                                QiluPreference.setAppFlag(SignTag.SIGN_TAG.name(), true);
                                System.out.println("id: "+jsonObject.getString("userId")
                                        +"; nick: "+jsonObject.getString("userName")
                                        +"; type: "+jsonObject.getString("userType")
                                        +"; name: "+jsonObject.getString("realName"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    })
                    .build()
                    .post();
        }
        else {
            QiluPreference.setAppFlag(SignTag.SIGN_TAG.name(), false);
        }
    }

    private static boolean isSignIn() {
        return QiluPreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker checker) {
        if (isSignIn()) {
            checker.onSignIn();
        } else {
            checker.onNotSignIn();
        }
    }
}

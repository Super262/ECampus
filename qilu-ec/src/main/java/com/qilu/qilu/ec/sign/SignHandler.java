package com.qilu.qilu.ec.sign;

import com.qilu.qilu.app.AccountManager;

public class SignHandler {

    public static void onSignIn(String phone,String pwd,String response, ISignListener signListener) {
        if(response.equals("0")){
            //已经注册并登录成功了
            AccountManager.setSignState(phone,pwd,true);
            signListener.onSignInSuccess();
        }else{
            AccountManager.setSignState(phone,pwd,false);
            signListener.onSignInFailure(response);
        }
    }


    public static void onSignUp(String response, ISignListener signListener) {
        if(response.equals("0")){
            signListener.onSignUpSuccess();
        }else{
            signListener.onSignUpFailure(response);
        }
    }
}
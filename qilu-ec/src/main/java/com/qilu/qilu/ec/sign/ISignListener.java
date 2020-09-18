package com.qilu.qilu.ec.sign;

public interface ISignListener {

    void onSignInSuccess();

    void onSignUpSuccess();
    void onSignUpFailure(String response);
    void onSignInFailure(String response);
}
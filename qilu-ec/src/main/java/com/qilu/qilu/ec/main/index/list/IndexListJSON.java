package com.qilu.qilu.ec.main.index.list;

import android.provider.Settings;

public class IndexListJSON {
    private static String value=" ";
    public static void setProValue(String response){
        value=response;
    }
    public static String getProValue(){
        return value;
    }
}

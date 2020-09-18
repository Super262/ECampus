package com.qilu.qilu.ec.main.index;

import java.util.HashMap;

public class TempDetailValue {
    private static HashMap<String,String> value;
    public static void setProValue(HashMap<String,String> params){
        value=params;
    }
    public static HashMap<String,String> getProValue(){
        return value;
    }
}

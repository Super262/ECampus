package com.qilu.qilu.util.image;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;

public class ImageStringTools {
    public static String getString(byte[] bytes) throws IOException {
        return new Gson().toJson(bytes);
    }
    public static byte[] getBytes(String data){
        Gson gson=new GsonBuilder().enableComplexMapKeySerialization().create();
        Type type=new TypeToken<byte[]>(){}.getType();
        return gson.fromJson(data,type);
    }
}

package com.qilu.qilu.ec.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qilu.qilu.delegates.bottom.BaseBottomDelegate;
import com.qilu.qilu.delegates.bottom.BottomItemDelegate;
import com.qilu.qilu.delegates.bottom.BottomTabBean;
import com.qilu.qilu.delegates.bottom.ItemBuilder;
import com.qilu.qilu.ec.main.discover.DiscoverDelegate;
import com.qilu.qilu.ec.main.index.IndexDelegate;
import com.qilu.qilu.ec.main.index.list.IndexListJSON;
import com.qilu.qilu.ec.main.personal.PersonalDelegate;
import com.qilu.qilu.net.RestClient;
import com.qilu.qilu.net.RestClientBuilder;
import com.qilu.qilu.net.callback.ISuccess;
import com.qilu.qilu.util.storage.QiluPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

public class EcBottomDelegate extends BaseBottomDelegate {
    private Timer timer1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timer1 = new Timer();
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                final String phone= QiluPreference.getCustomAppProfile("phone");
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
        }, 0,1000);
    }

    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-commenting}", "消息"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-compass}", "发现"),new DiscoverDelegate());
        items.put(new BottomTabBean("{fa-user}", "我的"), new PersonalDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#1E90FF");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer1.cancel();
        System.out.println("EcBottomDelegate的Timer已终止！");
    }
}

package com.qilu.qilu.ec.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;

import com.qilu.qilu.delegates.QiluDelegate;
import com.qilu.qilu.ec.R;
import com.qilu.qilu.ec.main.personal.list.PersonalListAdapter;
import com.qilu.qilu.ec.main.personal.list.PersonalListBean;
import com.qilu.qilu.ec.main.personal.list.PersonalListItemType;
import com.qilu.qilu.util.storage.QiluPreference;

import java.util.ArrayList;
import java.util.List;

public class SettingsDelegate extends QiluDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_settings;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        final RecyclerView recyclerView = $(R.id.rv_settings);
        final PersonalListBean about = new PersonalListBean.Builder()
                .setItemType(PersonalListItemType.ITEM_NORMAL)
                .setId(1)
                .setDelegate(new AboutDelegate())
                .setText("关于")
                .build();

        final List<PersonalListBean> data = new ArrayList<>();
        data.add(about);
        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        final PersonalListAdapter adapter = new PersonalListAdapter(data);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new SettingsClickListener(this));
    }
}

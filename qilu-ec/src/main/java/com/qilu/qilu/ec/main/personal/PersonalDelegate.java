package com.qilu.qilu.ec.main.personal;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.qilu.qilu.delegates.bottom.BottomItemDelegate;
import com.qilu.qilu.ec.R;
import com.qilu.qilu.ec.main.personal.list.PersonalListAdapter;
import com.qilu.qilu.ec.main.personal.list.PersonalListBean;
import com.qilu.qilu.ec.main.personal.list.PersonalListItemType;
import com.qilu.qilu.ec.main.personal.profile.UserProfileDelegate;
import com.qilu.qilu.ec.main.personal.settings.SettingsDelegate;
import com.qilu.qilu.util.storage.QiluPreference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PersonalDelegate extends BottomItemDelegate {

    private final String DEFAULT_AVATAR_URL="http://123.207.13.112:8080/qilu/AboutUser/getImg?phoneNum=";
    private static final RequestOptions OPTIONS = new RequestOptions()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .signature(new ObjectKey(System.currentTimeMillis())) //
            .centerCrop();
    @Override
    public Object setLayout() {
        return R.layout.delegate_personal;
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        final RecyclerView rvSettings = $(R.id.rv_personal_setting);
        initData();
        ((SwipeRefreshLayout)$(R.id.srl_personal_delegate)).setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                ((SwipeRefreshLayout)$(R.id.srl_personal_delegate)).setRefreshing(false);
            }
        });
        $(R.id.img_user_avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAvatar();
            }
        });

        final PersonalListBean system = new PersonalListBean.Builder()
                .setItemType(PersonalListItemType.ITEM_NORMAL)
                .setId(1)
                .setDelegate(new SettingsDelegate())
                .setText("系统设置")
                .build();

        final PersonalListBean exit = new PersonalListBean.Builder()
                .setItemType(PersonalListItemType.ITEM_NORMAL)
                .setId(2)
                .setText("退出登录")
                .build();

        final List<PersonalListBean> data = new ArrayList<>();
        data.add(system);
        data.add(exit);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvSettings.setLayoutManager(manager);
        final PersonalListAdapter adapter = new PersonalListAdapter(data);
        rvSettings.setAdapter(adapter);
        rvSettings.addOnItemTouchListener(new PersonalClickListener(this));
    }
    private void onClickAvatar() {
        getParentDelegate().getSupportDelegate().start(new UserProfileDelegate());
    }
    private void initData(){
        final String phoneNum= QiluPreference.getCustomAppProfile("phone");
        Glide.with(this)
                .load(DEFAULT_AVATAR_URL+phoneNum)
                .apply(OPTIONS)
                .into((ImageView)$(R.id.img_user_avatar));
        final String nickName=QiluPreference.getCustomAppProfile("nick");
        ((TextView)$(R.id.txt_user_name)).setText(nickName);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

package com.qilu.qilu.ec.main.personal.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qilu.qilu.delegates.QiluDelegate;
import com.qilu.qilu.ec.R;
import com.qilu.qilu.ec.main.personal.list.PersonalListAdapter;
import com.qilu.qilu.ec.main.personal.list.PersonalListBean;
import com.qilu.qilu.ec.main.personal.list.PersonalListItemType;
import com.qilu.qilu.ec.main.personal.settings.CodeDelegate;
import com.qilu.qilu.ec.main.personal.settings.NameDelegate;
import com.qilu.qilu.ec.main.personal.settings.NickDelegate;
import com.qilu.qilu.ec.main.personal.settings.PhoneDelegate;
import com.qilu.qilu.util.storage.QiluPreference;
import java.util.ArrayList;
import java.util.List;

public class UserProfileDelegate extends QiluDelegate{
    private final String DEFAULT_AVATAR_URL="http://123.207.13.112:8080/qilu/AboutUser/getImg?phoneNum=";
    private final String[] mTypes=new String[]{"学生","其他教工","教室管理员","设备维护负责人"};
    private List<PersonalListBean> data = new ArrayList<>();
    private PersonalListAdapter adapter = null;
    @Override
    public Object setLayout() {
        return R.layout.delegate_user_profile;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        final RecyclerView recyclerView = $(R.id.rv_user_profile);
        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        adapter = new PersonalListAdapter(data);
        initData();
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new UserProfileClickListener(this));

    }
    public void initData(){
        final String mName= QiluPreference.getCustomAppProfile("name");
        final String mNick= QiluPreference.getCustomAppProfile("nick");
        final String mPhone= QiluPreference.getCustomAppProfile("phone");
        final String mId= QiluPreference.getCustomAppProfile("id");
        final String mTypeId=QiluPreference.getCustomAppProfile("type");
        final String mType=mTypes[Integer.parseInt(mTypeId)];
        data.clear();
        final String phoneNum= QiluPreference.getCustomAppProfile("phone");
        final PersonalListBean image = new PersonalListBean.Builder()
                .setItemType(PersonalListItemType.ITEM_AVATAR)
                .setId(1)
                .setImageUrl(DEFAULT_AVATAR_URL+phoneNum)
                .build();

        final PersonalListBean name = new PersonalListBean.Builder()
                .setItemType(PersonalListItemType.ITEM_NORMAL)
                .setId(2)
                .setText("姓名")
                .setDelegate(new NameDelegate())
                .setValue(mName)
                .build();
        final PersonalListBean nick = new PersonalListBean.Builder()
                .setItemType(PersonalListItemType.ITEM_NORMAL)
                .setId(3)
                .setText("昵称")
                .setDelegate(new NickDelegate())
                .setValue(mNick)
                .build();

        final PersonalListBean phone = new PersonalListBean.Builder()
                .setItemType(PersonalListItemType.ITEM_NORMAL)
                .setId(4)
                .setText("手机")
                .setDelegate(new PhoneDelegate())
                .setValue(mPhone)
                .build();

        final PersonalListBean code = new PersonalListBean.Builder()
                .setItemType(PersonalListItemType.ITEM_NORMAL)
                .setId(5)
                .setText("学工号")
                .setDelegate(new CodeDelegate())
                .setValue(mId)
                .build();

        final PersonalListBean type = new PersonalListBean.Builder()
                .setItemType(PersonalListItemType.ITEM_NORMAL)
                .setId(6)
                .setText("类型")
                .setValue(mType)
                .build();

        data.add(image);
        data.add(name);
        data.add(nick);
        data.add(phone);
        data.add(code);
        data.add(type);
        adapter.notifyDataSetChanged();
    }
}

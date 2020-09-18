package com.qilu.qilu.ec.main.personal;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.qilu.qilu.app.AccountManager;
import com.qilu.qilu.delegates.QiluDelegate;
import com.qilu.qilu.ec.main.personal.list.PersonalListBean;
import com.qilu.qilu.ec.sign.SignInDelegate;

public class PersonalClickListener extends SimpleClickListener {
    private final QiluDelegate DELEGATE;

    public PersonalClickListener(QiluDelegate delegate) {
        DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final PersonalListBean bean = (PersonalListBean) baseQuickAdapter.getData().get(position);
        int id = bean.getId();
        switch (id) {
            case 1:
                DELEGATE.getParentDelegate().getSupportDelegate().start(bean.getDelegate());
                break;
            case 2:
                AccountManager.setSignState("","",false);
                DELEGATE.getParentDelegate()
                        .getProxyActivity()
                        .getSupportDelegate()
                        .startWithPop(new SignInDelegate());
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}

package com.qilu.qilu.ec.main.index;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.qilu.qilu.delegates.QiluDelegate;
import com.qilu.qilu.ec.main.index.list.OrderListBean;

public class IndexClickListener extends SimpleClickListener {
    private final QiluDelegate DELEGATE;
    public IndexClickListener(QiluDelegate delegate) {
        this.DELEGATE=delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final OrderListBean bean = (OrderListBean) baseQuickAdapter.getData().get(position);
        DELEGATE.getParentDelegate().getSupportDelegate().start(bean.getDelegate());
        TempDetailValue.setProValue(bean.getProValue());
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

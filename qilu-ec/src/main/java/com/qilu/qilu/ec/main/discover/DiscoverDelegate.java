package com.qilu.qilu.ec.main.discover;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;

import com.qilu.qilu.delegates.bottom.BottomItemDelegate;
import com.qilu.qilu.ec.R;
import com.qilu.qilu.ec.main.discover.OrderRepairSystem.OrderRepairDelegate;
import com.qilu.qilu.util.storage.QiluPreference;

public class DiscoverDelegate extends BottomItemDelegate {
    final String type= QiluPreference.getCustomAppProfile("type");
    @Override
    public Object setLayout() {
            return R.layout.delegate_discover;

    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        ((CardView)$(R.id.btn_start_repair_order)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentDelegate().getSupportDelegate().start(new OrderRepairDelegate());
            }
        });
    }
}

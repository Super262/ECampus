package com.qilu.qilu.ec.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.qilu.qilu.delegates.QiluDelegate;
import com.qilu.qilu.ec.R;
import com.qilu.qilu.util.storage.QiluPreference;

public class AboutDelegate extends QiluDelegate {
    final String version= QiluPreference.getCustomAppProfile("version");
    @Override
    public Object setLayout() {
        return R.layout.delegate_about;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        ((AppCompatTextView)$(R.id.tv_info)).setText(version);
    }
}

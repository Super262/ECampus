package com.qilu.qilu.ec.launcher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.qilu.qilu.app.AccountManager;
import com.qilu.qilu.app.IUserChecker;
import com.qilu.qilu.delegates.QiluDelegate;
import com.qilu.qilu.ec.R;
import com.qilu.qilu.util.storage.QiluPreference;
import com.qilu.qilu_ui.launcher.ILauncherListener;
import com.qilu.qilu_ui.launcher.LauncherHolderCreator;
import com.qilu.qilu_ui.launcher.OnLauncherFinishTag;
import com.qilu.qilu_ui.launcher.ScrollLauncherTag;
import android.content.Context;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.util.ArrayList;

/**
 * Created by 傅令杰 on 2017/4/22
 */

public class LauncherScrollDelegate extends QiluDelegate implements OnItemClickListener {

    private ConvenientBanner<Integer> mConvenientBanner = null;
    private final ArrayList<Integer> INTEGERS = new ArrayList<>();
    private ILauncherListener mILauncherListener = null;

    private void initBanner() {
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        INTEGERS.add(R.mipmap.launcher_05);
        mConvenientBanner
                .setPages(new LauncherHolderCreator(), INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ILauncherListener) {
            mILauncherListener = (ILauncherListener) context;
        }
    }

    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner<>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initBanner();
    }

    @Override
    public void onItemClick(int position) {
        //如果点击的是最后一个
        if (position == INTEGERS.size() - 1) {
            QiluPreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(), true);
            //检查用户是否已经登录
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    if (mILauncherListener != null) {
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                }

                @Override
                public void onNotSignIn() {
                    if (mILauncherListener != null) {
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }
                }
            });
        }
    }
}
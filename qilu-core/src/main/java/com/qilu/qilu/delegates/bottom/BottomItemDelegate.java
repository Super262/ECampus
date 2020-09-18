package com.qilu.qilu.delegates.bottom;

import android.widget.Toast;

import com.qilu.qilu.R;
import com.qilu.qilu.app.Qilu;
import com.qilu.qilu.delegates.QiluDelegate;

public abstract class BottomItemDelegate extends QiluDelegate {
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, "再按一次退出" + Qilu.getApplicationContext().getString(R.string.app_name), Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}

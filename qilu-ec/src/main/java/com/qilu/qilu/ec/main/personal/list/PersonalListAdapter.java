package com.qilu.qilu.ec.main.personal.list;

import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qilu.qilu.ec.R;
import com.qilu.qilu.util.storage.QiluPreference;

import java.util.List;

public class PersonalListAdapter extends BaseMultiItemQuickAdapter<PersonalListBean, BaseViewHolder> {
    final String state= QiluPreference.getCustomAppProfile("auto_play_audio");

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .signature(new ObjectKey(System.currentTimeMillis()))
            .skipMemoryCache(true)
            .dontAnimate()
            .centerCrop();

    public PersonalListAdapter(List<PersonalListBean> data) {
        super(data);
        addItemType(PersonalListItemType.ITEM_NORMAL, R.layout.arrow_item_layout);
        addItemType(PersonalListItemType.ITEM_AVATAR, R.layout.arrow_item_avatar);
        addItemType(PersonalListItemType.ITEM_SWITCH,R.layout.arrow_switch_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonalListBean item) {
        switch (helper.getItemViewType()) {
            case PersonalListItemType.ITEM_NORMAL:
                helper.setText(R.id.tv_arrow_text, item.getText());
                helper.setText(R.id.tv_arrow_value, item.getValue());
                break;
            case PersonalListItemType.ITEM_AVATAR:
                Glide.with(mContext)
                        .load(item.getImageUrl())
                        .apply(OPTIONS)
                        .into((ImageView) helper.getView(R.id.img_arrow_avatar));
                break;
            case PersonalListItemType.ITEM_SWITCH:
                helper.setText(R.id.tv_arrow_switch_text,item.getText());
                final SwitchCompat switchCompat = helper.getView(R.id.list_item_switch);
                if(state.equals("0")){
                    switchCompat.setChecked(true);
                }
                else{
                    switchCompat.setChecked(false);
                }
                switchCompat.setOnCheckedChangeListener(item.getmOnCheckedChangeListener());
                break;
            default:
                break;
        }
    }
}

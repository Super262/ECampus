package com.qilu.qilu.ec.main.index.list;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.joanzapata.iconify.widget.IconTextView;
import com.qilu.qilu.ec.R;

import java.util.List;

public class OrderListAdapter extends BaseMultiItemQuickAdapter<OrderListBean, BaseViewHolder> {
    private final String[] STATE_TEXT={"已受理，已评价","已受理，未评价","未受理，未评价"};
    public OrderListAdapter(List<OrderListBean> data) {
        super(data);
        addItemType(OrderListItemType.ITEM_INDEX_DELEGATE, R.layout.message_item_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListBean item) {
        IconTextView finished=(IconTextView)helper.itemView.findViewById(R.id.index_order_state_icon_finish);
        IconTextView need_assess=(IconTextView)helper.itemView.findViewById(R.id.index_order_state_icon_need_assess);
        IconTextView need_accept=(IconTextView)helper.itemView.findViewById(R.id.index_order_state_icon_need_accept);
        switch(helper.getItemViewType()){
            case OrderListItemType.ITEM_INDEX_DELEGATE:
                helper.setText(R.id.index_order_type_text,item.getPro_type());
                helper.setText(R.id.index_order_location_text,item.getPro_pos());
                helper.setText(R.id.index_order_content_text,item.getPro_content());
                if(item.getIsAccepted().equals("true")){
                    if(item.getIsAssessed().equals("true")){
                        helper.setText(R.id.index_order_state_text,STATE_TEXT[0]);
                        finished.setVisibility(View.VISIBLE);
                        need_assess.setVisibility(View.GONE);
                        need_accept.setVisibility(View.GONE);
                    }else{
                        helper.setText(R.id.index_order_state_text,STATE_TEXT[1]);
                        finished.setVisibility(View.GONE);
                        need_assess.setVisibility(View.VISIBLE);
                        need_accept.setVisibility(View.GONE);
                    }
                }
                else{
                    helper.setText(R.id.index_order_state_text,STATE_TEXT[2]);
                    finished.setVisibility(View.GONE);
                    need_assess.setVisibility(View.GONE);
                    need_accept.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }
}

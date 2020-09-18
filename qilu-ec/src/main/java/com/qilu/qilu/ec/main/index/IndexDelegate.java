package com.qilu.qilu.ec.main.index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.qilu.qilu.app.Qilu;
import com.qilu.qilu.delegates.bottom.BottomItemDelegate;
import com.qilu.qilu.ec.R;
import com.qilu.qilu.ec.main.index.list.IndexListJSON;
import com.qilu.qilu.ec.main.index.list.OrderListAdapter;
import com.qilu.qilu.ec.main.index.list.OrderListBean;
import com.qilu.qilu.net.RestClient;
import com.qilu.qilu.net.callback.IError;
import com.qilu.qilu.net.callback.IFailure;
import com.qilu.qilu.net.callback.ISuccess;
import com.qilu.qilu.util.storage.QiluPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class IndexDelegate extends BottomItemDelegate {
    private RecyclerView mRecyclerView = null;
    private SwipeRefreshLayout mRefreshLayout = null;
    private List<OrderListBean> data = new ArrayList<>();
    private final String phone = QiluPreference.getCustomAppProfile("phone");

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        RestClient.builder()
                .url("List/showList")
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(getContext(),"获取列表失败",Toast.LENGTH_SHORT).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(getContext(),"未知错误",Toast.LENGTH_SHORT).show();
                    }
                })
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        IndexListJSON.setProValue(response);
                        System.out.println(response);
                        initData();
                    }
                })
                .loader(getContext())
                .build()
                .get_no_params();
        mRecyclerView = $(R.id.rv_index);
        mRefreshLayout = $(R.id.srl_index);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        OrderListAdapter adapter = new OrderListAdapter(data);
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new IndexClickListener(this));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RestClient.builder()
                        .url("List/showList")
                        .success(new ISuccess() {
                            @Override
                            public void onSuccess(String response) {
                                IndexListJSON.setProValue(response);
                                System.out.println(response);
                                initData();

                            }
                        })
                        .loader(getContext())
                        .build()
                        .get_no_params();

                mRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initData() {
        data.clear();
        final String USER_TYPE = QiluPreference.getCustomAppProfile("type");
        final String LIST_JSON = IndexListJSON.getProValue();
        if (LIST_JSON != null) {
            if (USER_TYPE.equals("0")) {
                try {
                    JSONArray array = new JSONArray(LIST_JSON);
                    for (int i = 0; i < array.length(); i++) {
                        final JSONObject now = (JSONObject) array.get(i);
                        final String proId = now.getString("proId");
                        final String proType = now.getString("proType");
                        final String proContent = now.getString("proContent");
                        final String proPos = now.getString("proPos");
                        final String providerPhoneNum = now.getString("providerPhoneNum");
                        final String providerName = now.getString("providerName");
                        final String isAccepted = now.getString("isAccepted");
                        final String isAssessed = now.getString("isAssessed");
                        final String proPhotoNum = now.getString("proPhotoNum");
                        final String proAudioNum = now.getString("proAudioNum");
                        if (providerPhoneNum.equals(phone)) {
                            if (isAccepted.equals("true")) {
                                if (isAssessed.equals("true")) {
                                    final String acceptedPhoneNum = now.getString("acceptedPhoneNum");
                                    final String acceptedName = now.getString("acceptedName");
                                    final String acceptedType = now.getString("acceptedType");
                                    final String assessStar = now.getString("assessStar");
                                    final String assessContent = now.getString("assessContent");
                                    final String assessPhotoNum = now.getString("assessPhotoNum");
                                    final OrderListBean bean = new OrderListBean.Builder()
                                            .setPro_id(proId)
                                            .setPro_type(proType)
                                            .setPro_content(proContent)
                                            .setPro_pos(proPos)
                                            .setProvider_phone_num(providerPhoneNum)
                                            .setProvider_name(providerName)
                                            .setIsAccepted(isAccepted)
                                            .setIsAssessed(isAssessed)
                                            .setAcceptedPhoneNum(acceptedPhoneNum)
                                            .setAcceptedName(acceptedName)
                                            .setAcceptedType(acceptedType)
                                            .setAssessStar(assessStar)
                                            .setAssessContent(assessContent)
                                            .setProPhotoNum(proPhotoNum)
                                            .setAssessPhotoNum(assessPhotoNum)
                                            .setProAudioNum(proAudioNum)
                                            .setDelegate(new OrderDetailDelegate())
                                            .build();
                                    data.add(bean);
                                } else {
                                    final String acceptedPhoneNum = now.getString("acceptedPhoneNum");
                                    final String acceptedName = now.getString("acceptedName");
                                    final String acceptedType = now.getString("acceptedType");
                                    final OrderListBean bean = new OrderListBean.Builder()
                                            .setPro_id(proId)
                                            .setPro_type(proType)
                                            .setPro_content(proContent)
                                            .setPro_pos(proPos)
                                            .setProvider_phone_num(providerPhoneNum)
                                            .setProvider_name(providerName)
                                            .setIsAccepted(isAccepted)
                                            .setIsAssessed(isAssessed)
                                            .setAcceptedPhoneNum(acceptedPhoneNum)
                                            .setAcceptedName(acceptedName)
                                            .setAcceptedType(acceptedType)
                                            .setProPhotoNum(proPhotoNum)
                                            .setProAudioNum(proAudioNum)
                                            .setDelegate(new OrderDetailDelegate())
                                            .build();
                                    data.add(bean);
                                }
                            } else {
                                final OrderListBean bean = new OrderListBean.Builder()
                                        .setPro_id(proId)
                                        .setPro_type(proType)
                                        .setPro_content(proContent)
                                        .setPro_pos(proPos)
                                        .setProvider_phone_num(providerPhoneNum)
                                        .setProvider_name(providerName)
                                        .setIsAccepted(isAccepted)
                                        .setIsAssessed(isAssessed)
                                        .setProPhotoNum(proPhotoNum)
                                        .setProAudioNum(proAudioNum)
                                        .setDelegate(new OrderDetailDelegate())
                                        .build();
                                data.add(bean);
                            }
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                //此用户不是学生时的操作
                try {
                    JSONArray array = new JSONArray(LIST_JSON);
                    for (int i = 0; i < array.length(); i++) {
                        final JSONObject now = (JSONObject) array.get(i);
                        final String proId = now.getString("proId");
                        final String proType = now.getString("proType");
                        final String proContent = now.getString("proContent");
                        final String proPos = now.getString("proPos");
                        final String providerPhoneNum = now.getString("providerPhoneNum");
                        final String providerName = now.getString("providerName");
                        final String isAccepted = now.getString("isAccepted");
                        final String isAssessed = now.getString("isAssessed");
                        final String proPhotoNum = now.getString("proPhotoNum");
                        final String proAudioNum = now.getString("proAudioNum");
                        if (isAccepted.equals("true")) {
                            if (isAssessed.equals("true")) {
                                final String acceptedPhoneNum = now.getString("acceptedPhoneNum");
                                final String acceptedName = now.getString("acceptedName");
                                final String acceptedType = now.getString("acceptedType");
                                final String assessStar = now.getString("assessStar");
                                final String assessContent = now.getString("assessContent");
                                final String assessPhotoNum = now.getString("assessPhotoNum");
                                final OrderListBean bean = new OrderListBean.Builder()
                                        .setPro_id(proId)
                                        .setPro_type(proType)
                                        .setPro_content(proContent)
                                        .setPro_pos(proPos)
                                        .setProvider_phone_num(providerPhoneNum)
                                        .setProvider_name(providerName)
                                        .setIsAccepted(isAccepted)
                                        .setIsAssessed(isAssessed)
                                        .setAcceptedPhoneNum(acceptedPhoneNum)
                                        .setAcceptedName(acceptedName)
                                        .setAcceptedType(acceptedType)
                                        .setAssessStar(assessStar)
                                        .setAssessContent(assessContent)
                                        .setProPhotoNum(proPhotoNum)
                                        .setAssessPhotoNum(assessPhotoNum)
                                        .setProAudioNum(proAudioNum)
                                        .setDelegate(new OrderDetailDelegate())
                                        .build();
                                data.add(bean);
                            } else {
                                final String acceptedPhoneNum = now.getString("acceptedPhoneNum");
                                final String acceptedName = now.getString("acceptedName");
                                final String acceptedType = now.getString("acceptedType");
                                final OrderListBean bean = new OrderListBean.Builder()
                                        .setPro_id(proId)
                                        .setPro_type(proType)
                                        .setPro_content(proContent)
                                        .setPro_pos(proPos)
                                        .setProvider_phone_num(providerPhoneNum)
                                        .setProvider_name(providerName)
                                        .setIsAccepted(isAccepted)
                                        .setIsAssessed(isAssessed)
                                        .setAcceptedPhoneNum(acceptedPhoneNum)
                                        .setAcceptedName(acceptedName)
                                        .setAcceptedType(acceptedType)
                                        .setProPhotoNum(proPhotoNum)
                                        .setProAudioNum(proAudioNum)
                                        .setDelegate(new OrderDetailDelegate())
                                        .build();
                                data.add(bean);
                            }
                        } else {
                            final OrderListBean bean = new OrderListBean.Builder()
                                    .setPro_id(proId)
                                    .setPro_type(proType)
                                    .setPro_content(proContent)
                                    .setPro_pos(proPos)
                                    .setProvider_phone_num(providerPhoneNum)
                                    .setProvider_name(providerName)
                                    .setIsAccepted(isAccepted)
                                    .setIsAssessed(isAssessed)
                                    .setProPhotoNum(proPhotoNum)
                                    .setProAudioNum(proAudioNum)
                                    .setDelegate(new OrderDetailDelegate())
                                    .build();
                            data.add(bean);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

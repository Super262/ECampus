package com.qilu.qilu.ec.main.index;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.qilu.qilu.delegates.QiluDelegate;
import com.qilu.qilu.ec.R;
import com.qilu.qilu.net.RestClient;
import com.qilu.qilu.net.callback.ISuccess;
import com.qilu.qilu.util.callback.CallbackManager;
import com.qilu.qilu.util.callback.CallbackType;
import com.qilu.qilu.util.callback.IGlobalCallback;
import com.qilu.qilu.util.storage.QiluPreference;
import com.qilu.qilu_ui.widget.AutoPhotoLayout;

import java.io.File;
import java.util.List;

public class AssessDetailDelegate extends QiluDelegate {
    final String state=TempDetailValue.getProValue().get("isAssessed");
    final String USER_TYPE= QiluPreference.getCustomAppProfile("type");
    private AutoPhotoLayout mAutoPhotoLayout = null;
    private static final RequestOptions OPTIONS = new RequestOptions()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .signature(new ObjectKey(System.currentTimeMillis()))
            .fitCenter();
    @Override
    public Object setLayout() {
        if(state.equals("true")){
            return R.layout.delegate_assess_detail_yes;
        }
        else{
            return R.layout.delegate_assess_detail_no;
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        if(state.equals("true")){
            final String score=TempDetailValue.getProValue().get("assessStar");
            final String content=TempDetailValue.getProValue().get("assessContent");
            AppCompatTextView contentText=$(R.id.assess_detail_assess_content);
            AppCompatTextView scoreText=$(R.id.assess_detail_assess_score);
            LinearLayoutCompat list=$(R.id.lv_assess_detail_photos);
            int assessPhotoNum=Integer.parseInt(TempDetailValue.getProValue().get("assessPhotoNum").trim());
            scoreText.setText(score);
            contentText.setText(content);
            if(assessPhotoNum>0){
                initPhotos(assessPhotoNum,list);
            }
        }
        else{
            mAutoPhotoLayout = $(R.id.assess_detail_no_photo_layout);
            TextInputEditText mContent=$(R.id.assess_detail_no_content);
            TextInputEditText mStar=$(R.id.assess_detail_no_score);
            mAutoPhotoLayout.setDelegate(this);
            CallbackManager.getInstance()
                    .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                        @Override
                        public void executeCallback(@Nullable Uri args) {
                            mAutoPhotoLayout.onCropTarget(args);
                        }
                    });
            ((AppCompatButton)$(R.id.assess_detail_no_assess_btn)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(USER_TYPE.equals("0")){
                        final String content=mContent.getText().toString();
                        final String star=mStar.getText().toString().trim();
                        boolean check=true;
                        if(content.isEmpty()){
                            check=false;
                            mContent.setError("不能为空！");
                        }
                        if(star.isEmpty()){
                            check=false;
                            mStar.setError("不能为空！");
                        }
                        if(check){
                            final List<File> photos=mAutoPhotoLayout.getPhotoList();
                            final int count=photos.size();
                            final String pro_id=TempDetailValue.getProValue().get("pro_id");
                            if(count>0){
                                RestClient.builder()
                                        .url("Assess/update")
                                        .loader(getContext())
                                        .success(new ISuccess() {
                                            @Override
                                            public void onSuccess(String response) {
                                                Toast.makeText(getContext(),"提交成功，包含"+count+"张照片",Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .proId(pro_id)
                                        .assessContent(content)
                                        .assessStar(star)
                                        .photos(photos)
                                        .build()
                                        .postAssessmentWithPhotos();
                            }
                            else{
                                RestClient.builder()
                                        .url("Assess/update2")
                                        .params("proId",pro_id)
                                        .params("assessStar",star)
                                        .params("assessContent",content)
                                        .build()
                                        .post();
                            }
                        }
                    }else{
                        Toast.makeText(getContext(),"权限不足",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void initPhotos(int photoNum, LinearLayoutCompat list){
        final String pro_id=TempDetailValue.getProValue().get("pro_id");
        for(int i=0;i<photoNum;i++){
            ImageView imageview=new ImageView(getContext());
            LinearLayoutCompat.LayoutParams lp=new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10,10,10,10);
            imageview.setLayoutParams(lp);
            list.addView(imageview);
            Glide.with(getContext())
                    .load("http://123.207.13.112:8080/qilu/Project/getAssessPhoto?"+"proId="+pro_id+"&"+"index="+i)
                    .apply(OPTIONS)
                    .into(imageview);
            imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
    }
}

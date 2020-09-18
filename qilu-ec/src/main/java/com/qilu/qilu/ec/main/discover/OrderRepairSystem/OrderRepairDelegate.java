package com.qilu.qilu.ec.main.discover.OrderRepairSystem;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;
import com.qilu.qilu.delegates.QiluDelegate;
import com.qilu.qilu.ec.R;
import com.qilu.qilu.net.RestClient;
import com.qilu.qilu.net.callback.IError;
import com.qilu.qilu.net.callback.IFailure;
import com.qilu.qilu.net.callback.ISuccess;
import com.qilu.qilu.util.callback.CallbackManager;
import com.qilu.qilu.util.callback.CallbackType;
import com.qilu.qilu.util.callback.IGlobalCallback;
import com.qilu.qilu.util.storage.QiluPreference;
import com.qilu.qilu_ui.widget.AutoPhotoLayout;
import java.io.File;
import java.util.List;

import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;

public class OrderRepairDelegate extends QiluDelegate {
    private final String user_realname= QiluPreference.getCustomAppProfile("name");
    private final String user_phone= QiluPreference.getCustomAppProfile("phone");
    private static final String AUDIO_FILE_PATH =
            Environment.getExternalStorageDirectory().getPath() + "/recorded_audio.wav";
    public static boolean has_voice=false;
    private AutoPhotoLayout mAutoPhotoLayout = null;
    public static AppCompatButton record_btn=null;
    @Override
    public Object setLayout() {
        return R.layout.delegate_order_repair;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        ((AppCompatTextView)$(R.id.this_name_text)).setText(user_realname);
        ((AppCompatTextView)$(R.id.this_phone_text)).setText(user_phone);
        TextInputEditText mOrderType=$(R.id.order_type_text);
        TextInputEditText mOrderLocation=$(R.id.order_location_text);
        AppCompatEditText mOrderDetail=$(R.id.order_detail_text);
        record_btn=$(R.id.btn_add_voice_order);
        mAutoPhotoLayout = $(R.id.custom_auto_photo_layout);
        mAutoPhotoLayout.setDelegate(this);
        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                    @Override
                    public void executeCallback(@Nullable Uri args) {
                        mAutoPhotoLayout.onCropTarget(args);
                    }
                });
       record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(has_voice){
                    record_btn.setText("添加录音");
                    has_voice=false;
                }else{
                    AndroidAudioRecorder.with(getActivity())
                            // Required
                            .setFilePath(AUDIO_FILE_PATH)
                            .setColor(ContextCompat.getColor(getContext(), R.color.colorECampus))
                            .setRequestCode(900)

                            // Optional
                            .setSource(AudioSource.MIC)
                            .setChannel(AudioChannel.STEREO)
                            .setSampleRate(AudioSampleRate.HZ_16000)
                            .setAutoStart(false)
                            .setKeepDisplayOn(true)

                            // Start recording
                            .record();
                }
            }
        });
        ((AppCompatButton)$(R.id.btn_submit_order)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String order_type=mOrderType.getText().toString();
                final String order_location=mOrderLocation.getText().toString();
                final String order_detail=mOrderDetail.getText().toString();
                boolean check=true;
                if(order_type.isEmpty()){
                    check=false;
                    mOrderType.setError("故障类型为空!");
                }
                if(order_detail.isEmpty()){
                    check=false;
                    mOrderDetail.setError("故障描述为空!");
                }
                if(order_location.isEmpty()){
                    check=false;
                    mOrderLocation.setError("故障所在地为空!");
                }
                if(check){
                    final List<File> photos=mAutoPhotoLayout.getPhotoList();
                    final int count=photos.size();
                    if(count>0){
                        if(has_voice){
                            RestClient.builder()
                                    .url("Post/newPro")
                                    .failure(new IFailure() {
                                        @Override
                                        public void onFailure() {
                                            Toast.makeText(getContext(),"提交失败",Toast.LENGTH_SHORT).show();
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
                                            switch (response) {
                                                case "0":
                                                    Toast.makeText(getContext(), "提交成功，包含"+count+"张照片", Toast.LENGTH_LONG).show();
                                                    break;
                                                case "1":
                                                    Toast.makeText(getContext(), "数据库更新出错", Toast.LENGTH_LONG).show();
                                                    break;
                                                case "2":
                                                    Toast.makeText(getContext(), "照片写入异常", Toast.LENGTH_LONG).show();
                                                    break;
                                            }
                                        }
                                    })
                                    .loader(getContext())
                                    .photos(photos)
                                    .realName(user_realname)
                                    .voice(AUDIO_FILE_PATH)
                                    .proType(order_type)
                                    .proPos(order_location)
                                    .proContent(order_detail)
                                    .build()
                                    .postRepairOrderWithAll();
                        }
                        else{
                            RestClient.builder()
                                    .url("Post/newPro2")
                                    .failure(new IFailure() {
                                        @Override
                                        public void onFailure() {
                                            Toast.makeText(getContext(),"提交失败",Toast.LENGTH_SHORT).show();
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
                                            switch (response) {
                                                case "0":
                                                    Toast.makeText(getContext(), "提交成功，包含"+count+"张照片", Toast.LENGTH_LONG).show();
                                                    break;
                                                case "1":
                                                    Toast.makeText(getContext(), "数据库更新出错", Toast.LENGTH_LONG).show();
                                                    break;
                                                case "2":
                                                    Toast.makeText(getContext(), "照片写入异常", Toast.LENGTH_LONG).show();
                                                    break;
                                            }
                                        }
                                    })
                                    .loader(getContext())
                                    .photos(photos)
                                    .realName(user_realname)
                                    .proType(order_type)
                                    .proPos(order_location)
                                    .proContent(order_detail)
                                    .build()
                                    .postRepairOrderWithPhotos();
                        }
                    }else{
                        if(has_voice){
                            RestClient.builder()
                                    .url("Post/newPro3")
                                    .failure(new IFailure() {
                                        @Override
                                        public void onFailure() {
                                            Toast.makeText(getContext(),"提交失败",Toast.LENGTH_SHORT).show();
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
                                            switch (response) {
                                                case "0":
                                                    Toast.makeText(getContext(), "提交成功，包含"+count+"张照片", Toast.LENGTH_LONG).show();
                                                    break;
                                                case "1":
                                                    Toast.makeText(getContext(), "数据库更新出错", Toast.LENGTH_LONG).show();
                                                    break;
                                                case "2":
                                                    Toast.makeText(getContext(), "照片写入异常", Toast.LENGTH_LONG).show();
                                                    break;
                                            }
                                        }
                                    })
                                    .loader(getContext())
                                    .voice(AUDIO_FILE_PATH)
                                    .proType(order_type)
                                    .proPos(order_location)
                                    .proContent(order_detail)
                                    .realName(user_realname)
                                    .build()
                                    .postRepairOrderWithVoice();
                        }else{
                            RestClient.builder()
                                    .url("Post/newPro4")
                                    .failure(new IFailure() {
                                        @Override
                                        public void onFailure() {
                                            Toast.makeText(getContext(),"提交失败",Toast.LENGTH_SHORT).show();
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
                                            switch (response) {
                                                case "0":
                                                    Toast.makeText(getContext(), "提交成功，包含"+count+"张照片", Toast.LENGTH_LONG).show();
                                                    break;
                                                case "1":
                                                    Toast.makeText(getContext(), "数据库更新出错", Toast.LENGTH_LONG).show();
                                                    break;
                                                case "2":
                                                    Toast.makeText(getContext(), "照片写入异常", Toast.LENGTH_LONG).show();
                                                    break;
                                            }
                                        }
                                    })
                                    .loader(getContext())
                                    .params("phoneNum",user_phone)
                                    .params("proType",order_type)
                                    .params("proPos",order_location)
                                    .params("proContent",order_detail)
                                    .params("realName",user_realname)
                                    .build()
                                    .post();
                        }
                    }
                }
            }
        });
    }
}

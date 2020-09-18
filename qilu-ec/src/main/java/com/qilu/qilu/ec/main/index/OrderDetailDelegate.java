package com.qilu.qilu.ec.main.index;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.qilu.qilu.util.storage.QiluPreference;
import java.io.IOException;

public class OrderDetailDelegate extends QiluDelegate {
    private static final RequestOptions OPTIONS = new RequestOptions()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .signature(new ObjectKey(System.currentTimeMillis()))
            .fitCenter();
    @Override
    public Object setLayout() {
        return R.layout.delegate_order_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        final String pro_id=TempDetailValue.getProValue().get("pro_id");
        final String pro_type=TempDetailValue.getProValue().get("pro_type");
        final String pro_content=TempDetailValue.getProValue().get("pro_content");
        final String pro_pos=TempDetailValue.getProValue().get("pro_pos");
        final String provider_phone_num=TempDetailValue.getProValue().get("provider_phone_num");
        final String provider_name=TempDetailValue.getProValue().get("provider_name");
        final String proPhotoNum=TempDetailValue.getProValue().get("proPhotoNum");
        final String proAudioNum=TempDetailValue.getProValue().get("proAudioNum");
        final String isAccepted=TempDetailValue.getProValue().get("isAccepted");
        final String isAssessed=TempDetailValue.getProValue().get("isAssessed");
        final String acceptedPhoneNum,acceptedName,acceptedType,assessContent,assessPhotoNum,assessStar;

        AppCompatButton btn_play_audio=(AppCompatButton)$(R.id.btn_order_detail_play_audio);
        AppCompatButton btn_accepted=(AppCompatButton)$(R.id.btn_order_detail_start_acceptation);
        AppCompatButton btn_assessed=(AppCompatButton)$(R.id.btn_order_detail_start_assessment);
        LinearLayoutCompat photo_list_view=(LinearLayoutCompat)$(R.id.lv_order_detail_photos);

        btn_accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportDelegate().startWithPop(new AcceptDetailDelegate());
            }
        });
        btn_assessed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportDelegate().startWithPop(new AssessDetailDelegate());
            }
        });

        initPhotos(proPhotoNum,photo_list_view);
//        adapter=new PhotoAdapter(this.getActivity(),R.layout.photo_item_layout,photo_list);
//        photo_list_view.setAdapter(adapter);

        ((AppCompatTextView)$(R.id.order_detail_type_text)).setText(pro_type);
        ((AppCompatTextView)$(R.id.order_detail_pos_text)).setText(pro_pos);
        ((AppCompatTextView)$(R.id.order_detail_content_text)).setText(pro_content);
        ((AppCompatTextView)$(R.id.order_detail_name_text)).setText(provider_name);
        ((AppCompatTextView)$(R.id.order_detail_phone_text)).setText(provider_phone_num);
        if(isAccepted.equals("true")){
            ((AppCompatTextView)$(R.id.order_detail_accepted_state_text)).setText("已受理");
            acceptedName=TempDetailValue.getProValue().get("acceptedName");
            acceptedPhoneNum=TempDetailValue.getProValue().get("acceptedPhoneNum");
            acceptedType=TempDetailValue.getProValue().get("acceptedType");
            if(isAssessed.equals("true")){
                ((AppCompatTextView)$(R.id.order_detail_assessed_state_text)).setText("已评价");
                assessContent=TempDetailValue.getProValue().get("assessContent");
                assessPhotoNum=TempDetailValue.getProValue().get("assessPhotoNum");
                assessStar=TempDetailValue.getProValue().get("assessStar");
            }
            else{
                ((AppCompatTextView)$(R.id.order_detail_assessed_state_text)).setText("未评价");
            }
        }
        else{
            ((AppCompatTextView)$(R.id.order_detail_accepted_state_text)).setText("未受理");
            ((AppCompatTextView)$(R.id.order_detail_assessed_state_text)).setText("未评价");
        }
        if(proAudioNum.equals("0")){
            btn_play_audio.setVisibility(View.GONE);
        }
        else{
            MediaPlayer mPlayer = new MediaPlayer();
            try {
                mPlayer.setDataSource("http://123.207.13.112:8080/qilu/Project/getAudio?proId="+pro_id);
                mPlayer.prepareAsync();
            } catch (IOException e) {
                Toast.makeText(getContext(),"获取音频失败",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            @SuppressLint("HandlerLeak")
            Handler handler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if(msg.what==1){
                        btn_play_audio.setText("播放录音");
                    }
                }
            };
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Message msg=Message.obtain();
                    msg.what=1;
                    msg.arg1=0;
                    handler.sendMessage(msg);
                }
            });
            btn_play_audio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mPlayer.isPlaying()){
                        btn_play_audio.setText("播放录音");
                        Toast.makeText(getContext(),"已暂停播放",Toast.LENGTH_SHORT).show();
                        mPlayer.pause();
                    }
                    else{
                        btn_play_audio.setText("暂停播放");
                        Toast.makeText(getContext(),"已播放录音",Toast.LENGTH_SHORT).show();
                        mPlayer.start();
                    }
                }
            });
        }
    }
    private void initPhotos(String photoNum, LinearLayoutCompat list){
        final String pro_id=TempDetailValue.getProValue().get("pro_id");
        final int count=Integer.parseInt(photoNum.trim());
        for(int i=0;i<count;i++){
            ImageView imageview=new ImageView(getContext());
            LinearLayoutCompat.LayoutParams lp=new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10,10,10,10);
            imageview.setLayoutParams(lp);
            list.addView(imageview);
            Glide.with(getContext())
                    .load("http://123.207.13.112:8080/qilu/Project/getPhoto?"+"proId="+pro_id+"&"+"index="+i)
                    .apply(OPTIONS)
                    .into(imageview);
            imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

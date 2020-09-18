package com.qilu.qilu.net;

import android.content.Context;

import com.qilu.qilu.net.callback.IError;
import com.qilu.qilu.net.callback.IFailure;
import com.qilu.qilu.net.callback.IRequest;
import com.qilu.qilu.net.callback.ISuccess;
import com.qilu.qilu.ui.loader.LoaderStyle;

import java.io.File;
import java.util.List;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public final class RestClientBuilder {

    private final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    private String mUrl = null;
    private IRequest mIRequest = null;
    private ISuccess mISuccess = null;
    private IFailure mIFailure = null;
    private IError mIError = null;
    private RequestBody mBody = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;
    private File mFile = null;
    private String mDownloadDir = null;
    private String mExtension = null;
    private String mName = null;
    private List<File> mPhotos=null;
    private String mProType=null;
    private String mProPos=null;
    private String mProConent=null;
    private File mVoice;
    private String mRealName=null;
    private String mAssessContent,mAssessStar,mProId;

    RestClientBuilder() {
    }
    public final RestClientBuilder assessContent(String mAssessContent){
        this.mAssessContent=mAssessContent;
        return this;
    }
    public final RestClientBuilder proId(String mProId){
        this.mProId=mProId;
        return this;
    }
    public final RestClientBuilder assessStar (String mAssessStar){
        this.mAssessStar=mAssessStar;
        return this;
    }
    public final RestClientBuilder realName(String realName){
        this.mRealName=realName;
        return this;
    }

    public final RestClientBuilder proType(String proType) {
        this.mProType=proType;
        return this;
    }

    public final RestClientBuilder proPos(String proPos) {
        this.mProPos=proPos;
        return this;
    }

    public final RestClientBuilder proContent(String proContent) {
        this.mProConent=proContent;
        return this;
    }

    public final RestClientBuilder voice(String voice) {
        this.mVoice=new File(voice);
        return this;
    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }


    public final RestClientBuilder file(String file) {
        this.mFile = new File(file);
        return this;
    }

    public final RestClientBuilder photos(List<File> photos) {
        this.mPhotos=photos;
        return this;
    }

    public final RestClientBuilder name(String name) {
        this.mName = name;
        return this;
    }

    public final RestClientBuilder dir(String dir) {
        this.mDownloadDir = dir;
        return this;
    }

    public final RestClientBuilder extension(String extension) {
        this.mExtension = extension;
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder onRequest(IRequest iRequest) {
        this.mIRequest = iRequest;
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess) {
        this.mISuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure) {
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder error(IError iError) {
        this.mIError = iError;
        return this;
    }

    public final RestClientBuilder loader(Context context, LoaderStyle style) {
        this.mContext = context;
        this.mLoaderStyle = style;
        return this;
    }

    public final RestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mUrl, PARAMS,
                mDownloadDir, mExtension, mName,
                mIRequest, mISuccess, mIFailure,
                mIError, mBody, mFile, mContext,
                mLoaderStyle,mPhotos,mProType,mProPos,mProConent,mVoice,mRealName, mAssessContent, mAssessStar, mProId);
    }
}

package com.qilu.qilu.net;

import android.content.Context;

import com.qilu.qilu.net.callback.IError;
import com.qilu.qilu.net.callback.IFailure;
import com.qilu.qilu.net.callback.IRequest;
import com.qilu.qilu.net.callback.ISuccess;
import com.qilu.qilu.net.callback.RequestCallbacks;
import com.qilu.qilu.net.download.DownloadHandler;
import com.qilu.qilu.ui.loader.LoaderStyle;
import com.qilu.qilu.ui.loader.QiluLoader;
import com.qilu.qilu.util.storage.QiluPreference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public final class RestClient {

    private final WeakHashMap<String, Object> PARAMS;
    private final String URL;
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;
    private final Context CONTEXT;
    private final String phoneNum;
    private final List<File> mPhotos;
    private final String mProType;
    private final String mProPos;
    private final String mProConent;
    private final File mVoice;
    private final String realName;
    private final String mAssessContent,mAssessStar,mProId;
    RestClient(String url,
               WeakHashMap<String, Object> params,
               String downloadDir,
               String extension,
               String name,
               IRequest request,
               ISuccess success,
               IFailure failure,
               IError error,
               RequestBody body,
               File file,
               Context context,
               LoaderStyle loaderStyle, List<File> photos, String mProType, String mProPos, String mProConent, File mVoice, String mRealName, String mAssessContent, String mAssessStar, String mProId) {
        this.URL = url;
        this.PARAMS = params;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.FILE = file;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
        this.mPhotos=photos;
        this.mProType = mProType;
        this.mProPos = mProPos;
        this.mProConent = mProConent;
        this.mVoice = mVoice;
        this.realName=mRealName;
        this.mAssessContent = mAssessContent;
        this.mAssessStar = mAssessStar;
        this.mProId = mProId;
        this.phoneNum= QiluPreference.getCustomAppProfile("phone");
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMethod method) {
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;

        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        if (LOADER_STYLE != null) {
            QiluLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;

            case GET_NO_PARAMS:
                call = service.get_no_params(URL);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL, BODY);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL, BODY);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestFile1 =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body1 =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestFile1);
                call = service.upload(URL,body1);
                break;
            case POST_FILE:
                final RequestBody requestFile2 =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                RequestBody requestPhone =
                        RequestBody.create(MediaType.parse("multipart/form-data"), phoneNum);
                final MultipartBody.Part body2 =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestFile2);
                call = service.postFile(URL,requestPhone,body2);
                break;
            case POST_ASSESSMENT_WITH_PHOTOS:
                RequestBody assessContent =
                        RequestBody.create(MediaType.parse("multipart/form-data"), mAssessContent);
                RequestBody assessStar =
                        RequestBody.create(MediaType.parse("multipart/form-data"),mAssessStar);
                RequestBody proId =
                        RequestBody.create(MediaType.parse("multipart/form-data"), mProId);
                List<MultipartBody.Part> assess_photos = new ArrayList<>(mPhotos.size());
                for (File file : mPhotos) {
                    RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), file);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("photos", file.getName(), requestBody);
                    assess_photos.add(part);
                }
                call=service.postAssessmentWithPhotos(URL,proId,assessStar,assessContent,assess_photos);
                break;
            case POST_REPAIR_ORDER_WITH_PHOTOS:
                RequestBody requestPhone1 =
                        RequestBody.create(MediaType.parse("multipart/form-data"), phoneNum);
                RequestBody proType1 =
                        RequestBody.create(MediaType.parse("multipart/form-data"), mProType);
                RequestBody proPos1 =
                        RequestBody.create(MediaType.parse("multipart/form-data"), mProPos);
                RequestBody requestName1 =
                        RequestBody.create(MediaType.parse("multipart/form-data"), realName);
                RequestBody proContent1 =
                        RequestBody.create(MediaType.parse("multipart/form-data"), mProConent);

                List<MultipartBody.Part> parts1 = new ArrayList<>(mPhotos.size());
                for (File file : mPhotos) {
                    RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), file);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("photos", file.getName(), requestBody);
                    parts1.add(part);
                }
                call=service.postRepairOrderWithPhotos(URL,requestPhone1,proType1,proPos1,proContent1,requestName1,parts1);
                break;
            case POST_REPAIR_ORDER_WITH_VOICE:
                RequestBody requestPhone2 =
                        RequestBody.create(MediaType.parse("multipart/form-data"), phoneNum);
                RequestBody proType2 =
                        RequestBody.create(MediaType.parse("multipart/form-data"), mProType);
                RequestBody proPos2 =
                        RequestBody.create(MediaType.parse("multipart/form-data"), mProPos);
                RequestBody proContent2 =
                        RequestBody.create(MediaType.parse("multipart/form-data"), mProConent);
                RequestBody requestName2 =
                        RequestBody.create(MediaType.parse("multipart/form-data"), realName);
                final RequestBody requestVoiceBody2 =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), mVoice);
                final MultipartBody.Part requestVoicePart2 =
                        MultipartBody.Part.createFormData("voice", mVoice.getName(), requestVoiceBody2);
                call=service.postRepairOrderWithVoice(URL,requestPhone2,proType2,proPos2,proContent2,requestName2,requestVoicePart2);
                break;
            case POST_REPAIR_ORDER_WITH_ALL:
                RequestBody requestPhone3 =
                        RequestBody.create(MediaType.parse("multipart/form-data"), phoneNum);
                RequestBody proType3 =
                        RequestBody.create(MediaType.parse("multipart/form-data"), mProType);
                RequestBody proPos3 =
                        RequestBody.create(MediaType.parse("multipart/form-data"), mProPos);
                RequestBody proContent3 =
                        RequestBody.create(MediaType.parse("multipart/form-data"), mProConent);
                RequestBody requestName3 =
                        RequestBody.create(MediaType.parse("multipart/form-data"), realName);
                List<MultipartBody.Part> parts3 = new ArrayList<>(mPhotos.size());
                for (File file : mPhotos) {
                    RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), file);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("photos", file.getName(), requestBody);
                    parts3.add(part);
                }
                final RequestBody requestVoiceBody3 =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), mVoice);
                final MultipartBody.Part requestVoicePart3 =
                        MultipartBody.Part.createFormData("voice", mVoice.getName(), requestVoiceBody3);
                call=service.postRepairOrderWithAll(URL,requestPhone3,proType3,proPos3,proContent3,requestName3,requestVoicePart3,parts3);
                break;
            default:
                break;
        }

        if (call != null) {
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<String> getRequestCallback() {
        return new RequestCallbacks(
                REQUEST,
                SUCCESS,
                FAILURE,
                ERROR,
                LOADER_STYLE
        );
    }

    public final void get() {
        request(HttpMethod.GET);
    }
    public final void get_no_params() {
        request(HttpMethod.GET_NO_PARAMS);
    }

    public final void post() {
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void upload() {
        request(HttpMethod.UPLOAD);
    }
    public final void postFile() {
        request(HttpMethod.POST_FILE);
    }
    public final void postRepairOrderWithPhotos() {
        request(HttpMethod.POST_REPAIR_ORDER_WITH_PHOTOS);
    }
    public final void postRepairOrderWithVoice() {
        request(HttpMethod.POST_REPAIR_ORDER_WITH_VOICE);
    }
    public final void postAssessmentWithPhotos() {
        request(HttpMethod.POST_ASSESSMENT_WITH_PHOTOS);
    }
    public final void postRepairOrderWithAll() {
        request(HttpMethod.POST_REPAIR_ORDER_WITH_ALL);
    }
    public final void download() {
        new DownloadHandler(URL, PARAMS,REQUEST, DOWNLOAD_DIR, EXTENSION, NAME,
                SUCCESS, FAILURE, ERROR)
                .handleDownload();
    }
}

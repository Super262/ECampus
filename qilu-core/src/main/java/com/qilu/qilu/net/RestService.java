package com.qilu.qilu.net;

import java.util.List;
import java.util.WeakHashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface RestService {
    @GET
    Call<String> get(@Url String url, @QueryMap WeakHashMap<String, Object> params);

    @GET
    Call<String> get_no_params(@Url String url);

    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @FieldMap WeakHashMap<String, Object> params);

    @Multipart
    @POST
    Call<String> postFile(@Url String url, @Part("phoneNum") RequestBody phoneNum,@Part MultipartBody.Part file);

    @POST
    @Multipart
    Call<String> postAssessmentWithPhotos(@Url String url,
                                           @Part("proId") RequestBody proId,
                                           @Part("assessStar") RequestBody score,
                                           @Part("assessContent") RequestBody content,
                                           @Part List<MultipartBody.Part> parts);

    @Multipart
    @POST
    Call<String> postRepairOrderWithPhotos(@Url String url,
                                           @Part("phoneNum") RequestBody phoneNum,
                                           @Part("proType") RequestBody proType,
                                           @Part("proPos") RequestBody proPos,
                                           @Part("proContent")RequestBody proContent,
                                           @Part("realName") RequestBody realName,
                                           @Part List<MultipartBody.Part> parts);
    @Multipart
    @POST
    Call<String> postRepairOrderWithVoice(@Url String url,
                                          @Part("phoneNum") RequestBody phoneNum,
                                          @Part("proType") RequestBody proType,
                                          @Part("proPos") RequestBody proPos,
                                          @Part("proContent")RequestBody proContent,
                                          @Part("realName") RequestBody realName,
                                          @Part MultipartBody.Part file);
    @Multipart
    @POST
    Call<String> postRepairOrderWithAll(@Url String url,
                                        @Part("phoneNum") RequestBody phoneNum,
                                        @Part("proType") RequestBody proType,
                                        @Part("proPos") RequestBody proPos,
                                        @Part("proContent")RequestBody proContent,
                                        @Part("realName") RequestBody realName,
                                        @Part MultipartBody.Part file,
                                        @Part List<MultipartBody.Part> parts);

    @POST
    Call<String> postRaw(@Url String url, @Body RequestBody body);

    @FormUrlEncoded
    @PUT
    Call<String> put(@Url String url, @FieldMap WeakHashMap<String, Object> params);

    @PUT
    Call<String> putRaw(@Url String url, @Body RequestBody body);

    @DELETE
    Call<String> delete(@Url String url, @QueryMap WeakHashMap<String, Object> params);

    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url, @QueryMap WeakHashMap<String, Object> params);

    @Multipart
    @POST
    Call<String> upload(@Url String url, @Part MultipartBody.Part file);
}

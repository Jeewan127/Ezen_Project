package com.example.rkswl.ezen_project.retrofit;

import com.example.rkswl.ezen_project.model.user;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by rkswl on 2018-02-24.
 */

public interface RetrofitRequest {
    @GET("login")
    Call<ArrayList<user>> getuserList(@Query("id") String id, @Query("pass") String pass);

    @Multipart
    @POST("join")
    Call<String> insertuser(@Part("id") RequestBody id, @Part("pass") RequestBody pass, @Part("name") RequestBody name, @Part("date") RequestBody date, @Part("gender") RequestBody gender, @Part("number") RequestBody number, @Part MultipartBody.Part photo);

    @GET("overlap")
    Call<String> over(@Query("id") String id);

    @GET("number")
    Call<String> update_number(@Query("id") String id, @Query("number") String number);

    @GET("lock")
    Call<String> update_lock(@Query("id") String id, @Query("password") String pass);

    @GET("group")
    Call<String> serch_group(@Query("phone") String phone, @Query("date") String date, @Query("id") String id);

}

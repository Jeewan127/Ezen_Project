package com.lwan.rkswl.ezen_project.retrofit;

import com.lwan.rkswl.ezen_project.StroyImageFIle;
import com.lwan.rkswl.ezen_project.model.information;
import com.lwan.rkswl.ezen_project.model.user;

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
    @GET("get_user")
    Call<ArrayList<user>> get_user(@Query("id") String id);

    @GET("user_ch")
    Call<String> user_ch(@Query("name") String name , @Query("number") String number , @Query("pass") String password , @Query("op_number") String op_number , @Query("date") String date ,@Query("id") String id);

    @GET("lock_password")
    Call<String> lock_password(@Query("id") String id ,@Query("pass") String password);

    @GET("get_id")
    Call<String> get_id(@Query("id") String id);

    @GET("user_information")
    Call<ArrayList<information>> getuser_information(@Query("id") String id);

    @GET("login")
    Call<ArrayList<user>> getuserList(@Query("id") String id, @Query("pass") String pass);

    @GET("get_story")
    Call<ArrayList<StroyImageFIle>> get_story(@Query("id") String id);

    @GET("get_picture")
    Call<ArrayList<StroyImageFIle>> get_picture(@Query("id") String id);

    @GET("get_story_list")
    Call<ArrayList<StroyImageFIle>> get_story_list(@Query("id") String id , @Query("title") String title , @Query("date") String date);

    @GET("delete_list")
    Call<String> delete_list(@Query("id") String id , @Query("title") String title , @Query("date") String date);

    @Multipart
    @POST("join")
    Call<String> insertuser(@Part("id") RequestBody id,             //user_id
                            @Part("pass") RequestBody pass,         //user_pass
                            @Part("name") RequestBody name,         //user_name
                            @Part("date") RequestBody date,         //Birthday
                            @Part("gender") RequestBody gender,     //gender
                            @Part("number") RequestBody number,     //p_number
                            @Part MultipartBody.Part photo,           //이미지
                            @Part("op_number") RequestBody op_number,   //상대방 연락처
                            @Part("lock_pass") RequestBody lock_pass,  //lock_pass
                            @Part("FIRST_DAY") RequestBody FIRST_DAY);
    @Multipart
    @POST("addFile")
    Call<String> insert_plus(@Part("id") RequestBody group_id,        //그룹의 아이디
                             @Part("title") RequestBody Title,          //타이틀
                             @Part("date") RequestBody date,            //날짜
                             @Part MultipartBody.Part photo);           //이미지)



    @GET("overlap")
    Call<String> over(@Query("id") String id);

    @GET("password")
    Call<String> password(@Query("id") String id, @Query("phone") String phone , @Query("date") String date);

    @GET("updatepass")
    Call<String> up_pass(@Query("id") String id, @Query("password") String pass);

    @GET("number")
    Call<String> update_number(@Query("id") String id, @Query("number") String number);

    @GET("lock")
    Call<String> update_lock(@Query("id") String id, @Query("password") String pass);

    @GET("group")
    Call<String> serch_group(@Query("phone") String phone, @Query("date") String date, @Query("id") String id);



}

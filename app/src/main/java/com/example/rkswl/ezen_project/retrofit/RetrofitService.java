package com.example.rkswl.ezen_project.retrofit;

import com.example.rkswl.ezen_project.system_address;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rkswl on 2018-02-24.
 */

public class RetrofitService {
    public static RetrofitService curr =null;
    private RetrofitRequest retrofitRequest;

    public static RetrofitService getInstance(){
        if(curr == null){
            curr = new RetrofitService();
        }

        return curr;
    }

    private RetrofitService(){
        retrofitRequest = init();
    }

    public RetrofitRequest init(){

        //Retrofit retrofit = new Retrofit.Builder().baseUrl("http://172.16.141.233:8090/addwins/").addConverterFactory(GsonConverterFactory.create()).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(system_address.addre).addConverterFactory(GsonConverterFactory.create()).build();


        return retrofit.create(RetrofitRequest.class);
    }

    public static RetrofitService getCurr() {
        return curr;
    }

    public static void setCurr(RetrofitService curr) {
        RetrofitService.curr = curr;
    }

    public RetrofitRequest getRetrofitRequest() {
        return retrofitRequest;
    }

    public void setRetrofitRequest(RetrofitRequest retrofitRequest) {
        this.retrofitRequest = retrofitRequest;
    }
}

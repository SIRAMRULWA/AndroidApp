package com.example.myapplicationemptyview;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {
    @POST("register")
    Call<ResponseBody> createUser (
            @Body user user
    );

    @POST("login")
    Call<ResponseBody> checkUser (
            @Body user user
    );

}

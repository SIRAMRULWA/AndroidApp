package com.example.myapplicationemptyview;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetroFitClient {

    public static class RetrofitClient {
        private static  final String BASE_URL = "http://10.1.31.155/api/";
        private static RetrofitClient mInstance;
        private final Retrofit retrofit;

        private RetrofitClient () {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        public static synchronized RetrofitClient getInstance() {
            if (mInstance == null) {
                mInstance = new RetrofitClient();
            }
            return mInstance;
        }

        public API getAPI () {
            return retrofit.create(API.class);
        }

    }

}

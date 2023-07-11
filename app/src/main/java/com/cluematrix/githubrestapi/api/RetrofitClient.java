package com.cluematrix.githubrestapi.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://api.github.com/";
    private static final String AUTH_USERNAME = "adesh-bhoyar";
    private static final String AUTH_TOKEN = "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";

    private static Retrofit getRetrofitInstance() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor(AUTH_USERNAME, AUTH_TOKEN))
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static GitHubApiService getApiService() {
        return getRetrofitInstance().create(GitHubApiService.class);
    }

}


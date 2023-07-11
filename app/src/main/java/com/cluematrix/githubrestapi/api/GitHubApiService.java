package com.cluematrix.githubrestapi.api;

import com.cluematrix.githubrestapi.model.GitHubRepo;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GitHubApiService {
    @GET("/user/repos")
    Call<List<GitHubRepo>> getUserRepos();

    @POST("/user/repos")
    Call<GitHubRepo> createRepo(@Body JsonObject jsonObject);
}


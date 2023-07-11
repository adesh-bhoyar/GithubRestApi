package com.cluematrix.githubrestapi.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.cluematrix.githubrestapi.api.GitHubApiService;
import com.cluematrix.githubrestapi.api.RetrofitClient;
import com.cluematrix.githubrestapi.model.GitHubRepo;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubRepository {
    private final MutableLiveData<List<GitHubRepo>> repoListLiveData = new MutableLiveData<>();
    private final MutableLiveData<GitHubRepo> createdRepoLiveData = new MutableLiveData<>();

    public MutableLiveData<List<GitHubRepo>> getUserRepos() {
        GitHubApiService apiService = RetrofitClient.getApiService();
        Call<List<GitHubRepo>> call = apiService.getUserRepos();
        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(@NonNull Call<List<GitHubRepo>> call, @NonNull Response<List<GitHubRepo>> response) {
                if (response.isSuccessful()) {
                    repoListLiveData.setValue(response.body());
                } else {
                    repoListLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GitHubRepo>> call, @NonNull Throwable t) {
                repoListLiveData.setValue(null);
            }
        });
        return repoListLiveData;
    }

    public MutableLiveData<GitHubRepo> createUserRepo(String owner, String repoName, String repoDescription) {
        GitHubApiService apiService = RetrofitClient.getApiService();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", repoName);
        jsonObject.addProperty("description", repoDescription);
        jsonObject.addProperty("private", false);

        Call<GitHubRepo> call = apiService.createRepo(jsonObject);
        call.enqueue(new Callback<GitHubRepo>() {
            @Override
            public void onResponse(@NonNull Call<GitHubRepo> call, @NonNull Response<GitHubRepo> response) {
                if (response.isSuccessful()) {
                    createdRepoLiveData.setValue(response.body());
                } else {
                    createdRepoLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GitHubRepo> call, @NonNull Throwable t) {
                createdRepoLiveData.setValue(null);
            }
        });
        return createdRepoLiveData;
    }
}

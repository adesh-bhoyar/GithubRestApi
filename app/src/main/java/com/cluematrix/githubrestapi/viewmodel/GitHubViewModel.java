package com.cluematrix.githubrestapi.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cluematrix.githubrestapi.model.GitHubRepo;
import com.cluematrix.githubrestapi.repository.GithubRepository;

import java.util.List;

public class GitHubViewModel extends ViewModel {
    private GithubRepository repository;

    public LiveData<List<GitHubRepo>> getUserRepos() {
        if (repository == null) {
            repository = new GithubRepository();
        }
        return repository.getUserRepos();
    }

    public LiveData<GitHubRepo> createUserRepos(String owner, String repoName, String repoDescription) {
        if (repository == null) {
            repository = new GithubRepository();
        }
        return repository.createUserRepo(owner, repoName, repoDescription);
    }
}


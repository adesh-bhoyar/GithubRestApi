package com.cluematrix.githubrestapi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.cluematrix.githubrestapi.R;
import com.cluematrix.githubrestapi.databinding.ActivityAddRepoBinding;
import com.cluematrix.githubrestapi.model.GitHubRepo;
import com.cluematrix.githubrestapi.viewmodel.GitHubViewModel;

import java.util.Objects;

public class AddRepoActivity extends AppCompatActivity {

    //variables
    private ActivityAddRepoBinding binding;
    private GitHubViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_repo);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.add_repository);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = new ViewModelProvider(this).get(GitHubViewModel.class);

        binding.addButton.setOnClickListener(view -> {
            if (checkValidation()) {
                String owner = Objects.requireNonNull(binding.ownerEditText.getText()).toString();
                String repoName = Objects.requireNonNull(binding.repoEditText.getText()).toString();
                String repoDescription = Objects.requireNonNull(binding.repoDesEditText.getText()).toString();
                createRepository(owner, repoName, repoDescription);
            }
        });
    }

    private void createRepository(String owner, String repoName, String repoDescription) {
        binding.progressCircular.setVisibility(View.VISIBLE);
        viewModel.createUserRepos(owner, repoName, repoDescription).observe(this, repository -> {
            binding.progressCircular.setVisibility(View.GONE);
            if (repository != null) {
                Toast.makeText(AddRepoActivity.this, R.string.repository_created_successfully, Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("EXTRA_ADDED_REPO", repository);
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(AddRepoActivity.this, R.string.error_creating_repository, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkValidation() {
        String owner = Objects.requireNonNull(binding.ownerEditText.getText()).toString().trim();
        String repoName = Objects.requireNonNull(binding.repoEditText.getText()).toString().trim();
        String repoDescription = Objects.requireNonNull(binding.repoDesEditText.getText()).toString().trim();

        if (owner.isEmpty()) {
            Toast.makeText(this, R.string.please_enter_owner_name, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (repoName.isEmpty()) {
            Toast.makeText(this, R.string.please_enter_repository_name, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (repoDescription.isEmpty()) {
            Toast.makeText(this, R.string.please_enter_repository_description, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

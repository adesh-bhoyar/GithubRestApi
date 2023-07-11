package com.cluematrix.githubrestapi.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cluematrix.githubrestapi.databinding.ActivityMainBinding;
import com.cluematrix.githubrestapi.model.GitHubRepo;
import com.cluematrix.githubrestapi.viewmodel.GitHubViewModel;
import com.cluematrix.githubrestapi.R;
import com.cluematrix.githubrestapi.adapter.RepositoryAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements RepositoryAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private RepositoryAdapter adapter;
    private List<GitHubRepo> repoList;
    private GitHubViewModel viewModel;
    private ActivityMainBinding binding;

    private ActivityResultLauncher<Intent> addRepoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        // Retrieve the added repo from the data intent
                        GitHubRepo addedRepo = (GitHubRepo) result.getData().getSerializableExtra("EXTRA_ADDED_REPO");
                        if (addedRepo != null) {
                            // Add the repo to the list and notify the adapter
                            repoList.add(addedRepo);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.my_repository);

        viewModel = new ViewModelProvider(this).get(GitHubViewModel.class);

        repoList = new ArrayList<>();
        adapter = new RepositoryAdapter( this, repoList);

        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        binding.progressCircular.setVisibility(View.VISIBLE);
        viewModel.getUserRepos().observe(this, repos -> {
            // Update the UI with the repository data
            binding.progressCircular.setVisibility(View.GONE);
            Toast.makeText(this, R.string.repository_list_fetched_successfully, Toast.LENGTH_SHORT).show();
            repoList.clear();
            repoList.addAll(repos);
            adapter.notifyDataSetChanged();
        });

        binding.swipeUpRefresh.setOnRefreshListener(() -> {
            binding.progressCircular.setVisibility(View.VISIBLE);
            viewModel.getUserRepos().observe(this, repos -> {
                // Update the UI with the repository data
                binding.progressCircular.setVisibility(View.GONE);
                Toast.makeText(this, R.string.repository_list_fetched_successfully, Toast.LENGTH_SHORT).show();
                repoList.clear();
                repoList.addAll(repos);
                adapter.notifyDataSetChanged();
            });
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_add_repo) {
            Intent intent = new Intent(this, AddRepoActivity.class);
            addRepoLauncher.launch(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(GitHubRepo repo) {
        String url = repo.getHtml_url();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public void onShareClick(GitHubRepo repo) {
        String shareText = repo.getName() + ": " + repo.getHtml_url();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_repository)));
    }
}


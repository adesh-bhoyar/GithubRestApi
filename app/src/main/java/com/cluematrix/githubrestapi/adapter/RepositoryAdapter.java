package com.cluematrix.githubrestapi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cluematrix.githubrestapi.R;
import com.cluematrix.githubrestapi.model.GitHubRepo;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.ViewHolder> {
    private List<GitHubRepo> repoList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(GitHubRepo repo);

        void onShareClick(GitHubRepo repo);
    }

    public RepositoryAdapter(OnItemClickListener listener, List<GitHubRepo> repoList) {
        this.listener = listener;
        this.repoList = repoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repository, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GitHubRepo repo = repoList.get(position);
        holder.nameTextView.setText(repo.getName());
        holder.descriptionTextView.setText(repo.getDescription());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(repo);
            }
        });

        holder.shareButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onShareClick(repo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, descriptionTextView;
        ShapeableImageView shareButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            shareButton = itemView.findViewById(R.id.shareButton);
        }
    }
}


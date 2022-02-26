package com.example.gitobserver.presentation.repositories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gitobserver.domain.model.GitHubRepository
import com.example.gitobserver.domain.usecase.SharedPrefStorageUseCase
import com.example.gitobserver.presentation.repositories.OnRepositoryClickListener

class RepositoriesRecyclerAdapter(
    private val repositories: List<GitHubRepository>,
    private val sharedPrefStorageUseCase: SharedPrefStorageUseCase,
    private val onRepositoryClickListener: OnRepositoryClickListener
) : RecyclerView.Adapter<RepositoriesRecyclerViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepositoriesRecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RepositoriesRecyclerViewHolder(
            inflater,
            parent,
            sharedPrefStorageUseCase,
            onRepositoryClickListener
        )
    }

    override fun onBindViewHolder(holder: RepositoriesRecyclerViewHolder, position: Int) {
        val gitHubRepository = repositories[position]
        holder.bind(gitHubRepository = gitHubRepository)
    }

    override fun getItemCount(): Int = repositories.size
}

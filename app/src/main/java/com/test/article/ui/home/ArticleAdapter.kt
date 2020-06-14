package com.test.article.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.article.R
import com.test.article.persistence.ArticleDB

class ArticleAdapter(private val listener: ItemCallback) :
    PagedListAdapter<ArticleDB, RecyclerView.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ArticleViewHolder).bind(
            getItem(position),
            listener
        )
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ArticleDB>() {
            override fun areItemsTheSame(oldItem: ArticleDB, newItem: ArticleDB): Boolean =
                oldItem.articleId == newItem.articleId

            override fun areContentsTheSame(oldItem: ArticleDB, newItem: ArticleDB): Boolean =
                oldItem == newItem
        }
    }
}
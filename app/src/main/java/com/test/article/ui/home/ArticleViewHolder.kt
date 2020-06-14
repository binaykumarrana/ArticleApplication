package com.test.article.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.test.article.persistence.ArticleDB
import com.test.article.utils.ImageUtils
import com.test.article.utils.getDate
import kotlinx.android.synthetic.main.article_item.view.*

class ArticleViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {

    fun bind(
        article: ArticleDB?,
        listener: ItemCallback
    ) {
        article?.let {
            setupViews(it, itemView)
            setListeners(listener, article)
        }
    }

    private fun setupViews(article: ArticleDB, itemView: View) {
        itemView.titleTextView.text = article.title
        itemView.descriptionEditText.text = article.description
        itemView.dateTextView.text = getDate(article.date)
        ImageUtils.loadImage(
            article.avatarUrl,
            itemView.profileImageView,
            itemView.profileImageView.context
        )
    }

    private fun setListeners(
        listener: ItemCallback,
        article: ArticleDB
    ) {
        itemView.setOnClickListener {
            listener.onArticleClicked(article.articleId, article.title, article.avatarUrl)
        }
    }
}
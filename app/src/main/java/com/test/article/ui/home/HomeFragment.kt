package com.test.article.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.article.R
import com.test.article.domain.interactor.NetworkState
import com.test.article.utils.showErrorDialog
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), ItemCallback {
    private lateinit var articleAdapter: ArticleAdapter
    private val model by viewModel<ArticleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleAdapter = ArticleAdapter(this)
        setupRecyclerView()
        progress_circular.bringToFront()
        model.loadArticlesPersistence()
        observeViewModelData()
        checkNetworkStatus()
    }

    private fun setupRecyclerView() {
        articleRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = articleAdapter
        }
    }

    private fun observeViewModelData() {
        //Observe local data if not found then call remote
        model.localArticles.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                model.fetchArticles()
            } else {
                articleAdapter.submitList(it)
            }
        })
        model.articles.observe(
            viewLifecycleOwner,
            Observer {
                articleAdapter.submitList(it)
            })
    }

    private fun checkNetworkStatus() {
        model.networkState?.observe(viewLifecycleOwner, Observer {
            it?.let { status ->
                when (status) {
                    NetworkState.SUCCESS -> {
                        progress_circular.visibility = View.GONE
                    }
                    NetworkState.LOADING -> {
                        progress_circular.visibility = View.VISIBLE
                        progress_circular.bringToFront()
                    }
                    NetworkState.ERROR -> {
                        progress_circular.visibility = View.GONE
                        showErrorDialog(requireContext())
                    }
                }
            }
        })
    }

    override fun onArticleClicked(id: Int, title: String, avatar: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToArticleDetailsFragment(
            id.toString(),
            title,
            avatar
        )
        findNavController().navigate(action)
    }
}
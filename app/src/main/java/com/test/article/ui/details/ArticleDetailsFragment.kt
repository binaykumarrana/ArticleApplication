package com.test.article.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.test.article.R
import com.test.article.domain.interactor.NetworkState
import com.test.article.domain.interactor.Resource
import com.test.article.domain.model.ArticleDetails
import com.test.article.extension.displayMode
import com.test.article.extension.editMode
import com.test.article.ui.DialogCallback
import com.test.article.utils.*
import kotlinx.android.synthetic.main.article_details_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleDetailsFragment : Fragment(), DialogCallback {
    private val args: ArticleDetailsFragmentArgs by navArgs()
    private val model by viewModel<ArticleDetailsViewModel>()
    private var isEdited = false
    private lateinit var articleDetails: ArticleDetails
    private var isRemote: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.article_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progress_circular.bringToFront()
        descriptionEditText.displayMode()
        titleText.text = args.articleTitle
        ImageUtils.loadImage(args.avatar, profileImageView, requireContext())
        getLocalDataAndObserve()
        backButtonClick()
        editButtonClick()
        closeButtonClick()
        saveButtonClick()
    }

    private fun saveButtonClick() {
        saveButton.setOnClickListener {
            descriptionEditText.displayMode()
            showAlertDialog(
                requireContext(),
                args.articleTitle,
                this,
                getString(R.string.save_alert_msg)
            )
        }
    }

    private fun closeButtonClick() {
        closeIcon.setOnClickListener {
            isEdited = false
            descriptionEditText.displayMode()
            showAlertDialog(
                requireContext(),
                args.articleTitle,
                this,
                getString(R.string.alert_msg)
            )

        }
    }

    private fun editButtonClick() {
        editIcon.setOnClickListener {
            editIcon.visibility = View.GONE
            saveButton.visibility = View.VISIBLE
            closeIcon.visibility = View.VISIBLE
            isEdited = true
            descriptionEditText.editMode()
            showKeyboard(descriptionEditText, requireContext())
            descriptionEditText.requestFocus()
            descriptionEditText.setSelection(descriptionEditText.text?.length ?: 0)
        }
    }

    private fun backButtonClick() {
        backIcon.setOnClickListener {
            if (isEdited) {
                showAlertDialog(
                    requireContext(),
                    args.articleTitle,
                    this,
                    getString(R.string.alert_msg)
                )
                return@setOnClickListener
            }
            findNavController().navigateUp()
        }
    }

    private fun getLocalDataAndObserve() {
        model.loadArticlePersistence(args.articleId.toInt()).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                if (resource.data == null) {
                    isRemote = true
                    getRemoteDataAndObserve()
                } else {
                    isRemote = false
                    handleResult(resource)
                }
            }
        })
    }

    private fun getRemoteDataAndObserve() {
        model.getArticle(args.articleId.toInt()).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                handleResult(resource)
            }
        })
    }

    private fun handleResult(resource: Resource<ArticleDetails>) {
        when (resource.status) {
            NetworkState.SUCCESS -> {
                resource.data?.let { article ->
                    articleDetails = article
                    descriptionEditText.setText(article.text)
                }
                hideProgress()
            }
            NetworkState.LOADING -> {
                showProgress()
            }
            NetworkState.ERROR -> {
                hideProgress()
                if (isRemote)
                    showErrorDialog(requireContext())
            }
        }
    }

    override fun callSuccess() {
        hideKeyboard(descriptionEditText, requireContext())
        editIcon.visibility = View.VISIBLE
        saveButton.visibility = View.INVISIBLE
        closeIcon.visibility = View.GONE
        if (isEdited) {
            articleDetails.text = descriptionEditText.text.toString()
            model.updateArticle(articleDetails).observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    handleUpdateResult(resource)
                }
            })
            isEdited = false
        }
    }

    private fun handleUpdateResult(resource: Resource<Nothing?>) {
        when (resource.status) {
            NetworkState.SUCCESS -> {
                hideProgress()
            }
            NetworkState.LOADING -> {
                showProgress()
            }
            NetworkState.ERROR -> {
                hideProgress()
            }
        }
    }

    private fun showProgress() {
        progress_circular.visibility = View.VISIBLE
        progress_circular.bringToFront()
    }

    private fun hideProgress() {
        progress_circular.visibility = View.GONE
    }
}
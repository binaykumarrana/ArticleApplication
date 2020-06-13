package com.test.article.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.test.article.R
import com.test.article.extension.displayMode
import com.test.article.extension.editMode
import com.test.article.ui.DialogCallback
import com.test.article.utils.*
import kotlinx.android.synthetic.main.article_details_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.Observer
import com.test.article.domain.interactor.NetworkState
import com.test.article.domain.interactor.Resource
import com.test.article.domain.model.ArticleDetails

class ArticleDetailsFragment : Fragment(), DialogCallback {
    private val args: ArticleDetailsFragmentArgs by navArgs()
    private val model by viewModel<ArticleDetailsViewModel>()
    private var isEdited = false
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
        model.getArticle(args.articleId.toInt()).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                handleResult(resource)
            }
        })

        //back button
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

        //Edit icon click
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

        //Close icon click
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

        //Close icon clicked
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

    private fun handleResult(resource: Resource<ArticleDetails>) {
        when (resource.status) {
            NetworkState.SUCCESS -> {
                resource.data?.let { article ->
                    descriptionEditText.setText(article.text)
                }
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

    override fun callSuccess() {
        hideKeyboard(descriptionEditText, requireContext())
        editIcon.visibility = View.VISIBLE
        saveButton.visibility = View.INVISIBLE
        closeIcon.visibility = View.GONE
        if (isEdited) {
            //Save changes
            isEdited = false
        }
    }
}
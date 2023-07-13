package com.furkanmulayim.halkekmag.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.furkanmulayim.halkekmag.R
import com.furkanmulayim.halkekmag.databinding.FragmentPostDetailBinding
import com.furkanmulayim.halkekmag.utils.ProgressBarr
import com.furkanmulayim.halkekmag.utils.loadImage
import com.furkanmulayim.halkekmag.presentation.viewmodel.PostDetailViewModel

class PostDetailFragment : Fragment() {

    private lateinit var binding: FragmentPostDetailBinding
    private lateinit var viewModel: PostDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_detail, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PostDetailViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        viewModel.setCurrentDate()

        observingPost()
        detailCancelClicked()
    }

    private fun observingPost() {
        val postId = viewModel.initializePreferences(requireContext())
        viewModel.getPost(postId, requireContext())

        viewModel.post.observe(viewLifecycleOwner) { post ->
            post?.let {
                binding.title.text = post.postId
                binding.location.text = post.location
                binding.date.text = post.date
                binding.about.text = post.about
                post.photoUrl?.let { it1 -> binding.sendPhoto.loadImage(it1, ProgressBarr(requireContext())) }
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                viewModel.showMessage(R.string.err_message, requireContext(), requireView())
            }
        }
    }

    private fun detailCancelClicked() {
        binding.backButton.setOnClickListener {
            viewModel.navigate(R.id.action_postDetailFragment_to_sheetFragment, requireView())
        }
    }
}
package com.furkanmulayim.halkekmag.presentation.fragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import com.furkanmulayim.halkekmag.R
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.furkanmulayim.halkekmag.utils.showMessage
import com.furkanmulayim.halkekmag.presentation.adapter.SheetAdapter
import com.furkanmulayim.halkekmag.data.repo.GetPostRepository
import com.furkanmulayim.halkekmag.GetPostRepositoryFactory
import com.furkanmulayim.halkekmag.databinding.FragmentSheetBinding
import com.furkanmulayim.halkekmag.presentation.viewmodel.SheetFragmentViewModel

class SheetFragment : Fragment() {


    private val binding: FragmentSheetBinding by lazy {
        DataBindingUtil.inflate(layoutInflater, R.layout.fragment_sheet, null, false)
    }

    private val viewModel: SheetFragmentViewModel by lazy {
        val getRepoFactory = GetPostRepositoryFactory(GetPostRepository(requireContext()))
        ViewModelProvider(this, getRepoFactory)[SheetFragmentViewModel::class.java]
    }

    private var loginQuery: Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        loginQuery =viewModel.initializePreferences(requireContext())
        viewModel.setCurrentDate()
        getAllPost()
        onPlusButtonClicked()

    }

    //get all posts from viewmodel
    fun getAllPost() {
        viewModel.getPosts(onSuccess = { updatedPostList ->
            binding.recyc.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = SheetAdapter(updatedPostList) { clickedPostId ->
                    viewModel.saveClickedPostId(clickedPostId)
                    viewModel.navigate(R.id.action_sheetFragment_to_postDetailFragment,requireView())
                }
            }
        }, onFailure = {
            requireContext().showMessage(requireView(), R.string.err_message.toString(),requireContext())
        })
    }

    //For logged in when you press the plus button
    private fun onPlusButtonClicked() {
        binding.shareButton.setOnClickListener {
            loginQuery?.let { it1 -> viewModel.navigateWithDelay(it1, requireView()) }
        }
    }

}
package com.furkanmulayim.halkekmag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.furkanmulayim.halkekmag.data.repo.GetPostRepository
import com.furkanmulayim.halkekmag.data.repo.PostRepository
import com.furkanmulayim.halkekmag.presentation.viewmodel.SheetFragmentViewModel

class GetPostRepositoryFactory(private val postRepository: GetPostRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SheetFragmentViewModel::class.java)) {
            return SheetFragmentViewModel(postRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


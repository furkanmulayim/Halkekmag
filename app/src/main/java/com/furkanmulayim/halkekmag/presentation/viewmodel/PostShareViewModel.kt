package com.furkanmulayim.halkekmag.presentation.viewmodel

import android.content.Context
import android.net.Uri
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.furkanmulayim.halkekmag.data.repo.PostRepository
import com.furkanmulayim.halkekmag.utils.helpers.GetCurrentDateHelper
import com.furkanmulayim.halkekmag.utils.showMessage

class PostShareViewModel :ViewModel() {

    val currentDate = MutableLiveData<String>()


    fun sharePost(
        title: String,
        location: String,
        date: String,
        about: String,
        photoUri: Uri,
        context: Context,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        PostRepository(context).sharePost(
            title = title,
            location = location,
            date = date,
            about = about,
            photoUri = photoUri,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    fun navigate(id: Int, view: View) {
        Navigation.findNavController(view).navigate(id)
    }

    fun setCurrentDate() {
        currentDate.value = GetCurrentDateHelper.getCurrentDate()
    }

    fun showMessage(mess: Int, context: Context, view: View) {
        context.showMessage(view, context.getString(mess), context)
    }
}
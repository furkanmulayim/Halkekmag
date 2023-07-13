package com.furkanmulayim.halkekmag.presentation.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.furkanmulayim.halkekmag.R
import com.furkanmulayim.halkekmag.domain.model.Post
import com.furkanmulayim.halkekmag.data.repo.GetPostRepository
import com.furkanmulayim.halkekmag.utils.helpers.GetCurrentDateHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SheetFragmentViewModel(private val postRepository: GetPostRepository) : ViewModel() {

    private val postListLiveData = MutableLiveData<List<Post>>()
    val currentDate = MutableLiveData<String>()

    private lateinit var prefs: SharedPreferences
    private var logQuery: Boolean = false

    fun navigateWithDelay(girisSorgu: Boolean, view: View) {
        viewModelScope.launch {
            delay(200)
            if (girisSorgu) {
                //If logged in navigate to the share fragment
                navigate(R.id.action_sheetFragment_to_postPaylasFragment, view)
            } else {
                //If not logged in, navigate to the login fragment
                navigate(R.id.action_sheetFragment_to_loginFragment, view)
            }
        }
    }

    fun getPosts(onSuccess: (List<Post>) -> Unit, onFailure: () -> Unit) {
        postRepository.getPosts(onSuccess = { postList ->
            onSuccess.invoke(postList)
            postListLiveData.value = postList
        }, onFailure = onFailure)
    }

    fun navigate(id: Int, view: View) {
        Navigation.findNavController(view).navigate(id)
    }

    fun setCurrentDate() {
        currentDate.value = GetCurrentDateHelper.getCurrentDate()
    }

    fun initializePreferences(context: Context) :Boolean {
        prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        logQuery = prefs.getBoolean("giris", false)
        return logQuery
    }

    // Post ID'nin kaydedilmesi için çağrılan fonksiyon
    fun saveClickedPostId(id: String) {
        val editor = prefs.edit()
        editor.putString("post_id", id)
        editor.apply()
    }
}
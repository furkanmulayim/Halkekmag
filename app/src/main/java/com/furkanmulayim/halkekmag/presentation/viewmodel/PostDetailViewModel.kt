package com.furkanmulayim.halkekmag.presentation.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.furkanmulayim.halkekmag.domain.model.Post
import com.furkanmulayim.halkekmag.data.repo.GetPostRepository
import com.furkanmulayim.halkekmag.utils.helpers.GetCurrentDateHelper
import com.furkanmulayim.halkekmag.utils.showMessage

class PostDetailViewModel : ViewModel() {


    private lateinit var prefs: SharedPreferences
    val currentDate = MutableLiveData<String>()
    //
    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> get() = _post
    //
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getPost(postId: String, context: Context) {
        val repository = GetPostRepository(context)
        repository.onePostGet(postId, onSuccess = { post ->
            _post.value = post
        }, onFailure = {
            _errorMessage.value = "Alma işlemi başarısız oldu."
        })
    }

    fun setCurrentDate(){
        currentDate.value = GetCurrentDateHelper.getCurrentDate()
    }

    //mesaj gosterme islemi için
    fun showMessage(mess: Int, context: Context, view: View) {
        context.showMessage(view, context.getString(mess), context)
    }

    //navigate islemleri için
    fun navigate(id: Int, view: View) {
        Navigation.findNavController(view).navigate(id)
    }

     fun initializePreferences(context: Context) :String{
        prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val id = prefs.getString("post_id", "") ?: ""
        return id
    }

}
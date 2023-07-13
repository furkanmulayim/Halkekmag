package com.furkanmulayim.halkekmag.presentation.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.furkanmulayim.halkekmag.R
import com.furkanmulayim.halkekmag.utils.helpers.GetCurrentDateHelper
import com.furkanmulayim.halkekmag.utils.showMessage

class LoginFragmentViewModel : ViewModel() {

    private lateinit var prefs: SharedPreferences
    val currentDate = MutableLiveData<String>()
    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> get() = _loginSuccess

    //login islemi
    fun login(userName: String, password: String, view: View, context: Context) {
        val asil = "halkekmag"
        if (password.isNotEmpty() && userName.isNotEmpty()) {
            if (userName == asil && password == asil) {
                showMessage(R.string.login_successful, context, view)
                setPreferences(context)
                _loginSuccess.value = true
                navigate(R.id.action_loginFragment_to_postPaylasFragment, view)
            } else {
                showMessage(R.string.id_and_pass_incorrect, context, view)
                _loginSuccess.value = false
            }
        } else {
            showMessage(R.string.id_and_pass_empty, context, view)
            _loginSuccess.value = false
        }
    }
    //kullanıcı girişi yapıldıysa preferences ayarlamak için
    private fun setPreferences(context: Context) {
        initializePreferences(context)
        val editor = prefs.edit()
        editor.putBoolean("giris", true)
        editor.apply()
    }

    //mesaj gosterme islemi için
     fun showMessage(mess: Int, context: Context, view: View) {
        context.showMessage(view, context.getString(mess), context)
    }

    //navigate islemleri için
     fun navigate(id: Int, view: View) {
        Navigation.findNavController(view).navigate(id)
    }

    fun setCurrentDate(){
        currentDate.value = GetCurrentDateHelper.getCurrentDate()
    }


    // ViewModel'in başlatılması sırasında çağrılan fonksiyon
    private fun initializePreferences(context: Context) {
        prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }

}
package com.furkanmulayim.halkekmag.utils

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.furkanmulayim.halkekmag.R
import com.google.android.material.snackbar.Snackbar

fun ImageView.loadImage(url: String?, progressDrawable: CircularProgressDrawable) {
    val opt = RequestOptions().placeholder(progressDrawable).error(R.color.yellow_100)
    Glide.with(context).setDefaultRequestOptions(opt).load(url).into(this)
}
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Context.showMessage(view: View, message: String, context: Context) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).also { s ->
        s.view.background = ContextCompat.getDrawable(this, R.drawable.rounded_700)
        val sView = s.view
        val textView = sView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextColor(Color.WHITE)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
    }.show()
}

fun ProgressBarr(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}



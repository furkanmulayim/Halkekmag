package com.furkanmulayim.halkekmag.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SheetAdapterItemClickListener(private val onItemClick: (position: Int) -> Unit) : View.OnClickListener {

    private var adapterPosition: Int = RecyclerView.NO_POSITION

    fun updateAdapterPosition(position: Int) {
        adapterPosition = position
    }

    override fun onClick(v: View) {
        if (adapterPosition != RecyclerView.NO_POSITION) {
            onItemClick.invoke(adapterPosition)
        }
    }
}

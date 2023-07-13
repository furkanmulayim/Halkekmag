package com.furkanmulayim.halkekmag.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.furkanmulayim.halkekmag.databinding.ItemPostBinding
import com.furkanmulayim.halkekmag.domain.model.Post
import com.furkanmulayim.halkekmag.utils.ProgressBarr
import com.furkanmulayim.halkekmag.utils.loadImage

class SheetAdapter(
    private val postList: List<Post>,
    private val onItemClick: (String) -> Unit,
) : RecyclerView.Adapter<SheetAdapter.SheetViewHolder>() {

    private val itemClickListener = SheetAdapterItemClickListener { position ->
        val clickedPost = postList[position]
        clickedPost.postId?.let { it1 -> onItemClick(it1) }
    }

    inner class SheetViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedPost = postList[position]
                    clickedPost.postId?.let { it1 -> onItemClick(it1) }
                }
            }
        }


        fun bind(post: Post, holder: SheetViewHolder) {
            binding.title.text = post.postId
            binding.location.text = post.location
            binding.date.text = post.date
            binding.sendPhoto.loadImage(post.photoUrl.toString(), ProgressBarr(holder.itemView.context))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SheetViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SheetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SheetViewHolder, position: Int) {
        holder.itemView.tag = position
        itemClickListener.updateAdapterPosition(position)
        val post = postList[position]
        holder.bind(post,holder)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

}


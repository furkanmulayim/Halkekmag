package com.furkanmulayim.halkekmag.data.repo

import android.content.Context
import com.furkanmulayim.halkekmag.domain.model.Post
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class GetPostRepository(context: Context) {
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        FirebaseApp.initializeApp(context)
    }

    fun getPosts(onSuccess: (List<Post>) -> Unit, onFailure: () -> Unit) {
        val postList = mutableListOf<Post>()
        val postsRef = firestore.collection("posts")
        postsRef.get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot.documents) {
                val postId = document.getString("postId")
                val description = document.getString("description")
                val location = document.getString("location")
                val date = document.getString("date")
                val about = document.getString("about")
                val photoUrl = document.getString("photoUrl")
                val post = Post(postId, description, location, date, about, photoUrl)
                postList.add(post)
            }
            onSuccess(postList)
        }.addOnFailureListener {}
    }

    //Detail Fragment İçin Tek Postu Getiren Method
    fun onePostGet(
        postId: String,
        onSuccess: (Post?) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        val postRef = firestore.collection("posts").document(postId)
        postRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val postId = documentSnapshot.getString("postId")
                    val description = documentSnapshot.getString("description")
                    val location = documentSnapshot.getString("location")
                    val date = documentSnapshot.getString("date")
                    val about = documentSnapshot.getString("about")
                    val photoUrl = documentSnapshot.getString("photoUrl")
                    val post = Post(postId, description, location, date, about, photoUrl)
                    onSuccess(post)
                } else {
                    onSuccess(null)
                }
            }
            .addOnFailureListener {}
    }

/*    fun searchPosts(
        searchQuery: String,
        onSuccess: (List<Post>) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        val postsRef = firestore.collection("posts")
        val query = postsRef.whereGreaterThanOrEqualTo("postId", searchQuery)
            .whereLessThanOrEqualTo("postId", searchQuery + "\uf8ff")

        query.get().addOnSuccessListener { querySnapshot ->
            val searchResults = mutableListOf<Post>()

            for (document in querySnapshot.documents) {
                val postId = document.getString("postId")
                val description = document.getString("description")
                val location = document.getString("location")
                val date = document.getString("date")
                val about = document.getString("about")
                val photoUrl = document.getString("photoUrl")
                val post = Post(postId, description, location, date, about, photoUrl)
                searchResults.add(post)
            }
            onSuccess(searchResults)
        }.addOnFailureListener {}
    }*/
}

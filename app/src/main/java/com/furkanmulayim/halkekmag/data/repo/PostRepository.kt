package com.furkanmulayim.halkekmag.data.repo

import android.content.Context
import android.net.Uri
import com.furkanmulayim.halkekmag.domain.model.Post
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class PostRepository(private val context: Context) {
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var storage: FirebaseStorage = FirebaseStorage.getInstance()

    init {
        FirebaseApp.initializeApp(context)
    }

    fun sharePost(
        title: String,
        location: String,
        date: String,
        about: String,
        photoUri: Uri,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {

        firestore = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        val postId = title
        val postRef = firestore.collection("posts").document(postId)

        // Veritabanına postu ekliyoruz
        val post = hashMapOf(
            "postId" to title,
            "description" to title,
            "location" to location,
            "date" to date,
            "about" to about,
            "photoUrl" to ""
        )

        postRef.set(post).addOnSuccessListener {
            // Fotoğrafı F.Storage'a yükleme
            val photoRef = storage.reference.child("photos/$postId.jpg")
            photoRef.putFile(photoUri).addOnSuccessListener {
                // Yükleme başarılı
                photoRef.downloadUrl.addOnSuccessListener { uri ->
                    postRef.update("photoUrl", uri.toString()).addOnSuccessListener {
                        onSuccess()
                    }.addOnFailureListener {}
                }.addOnFailureListener {}
            }.addOnFailureListener {}
        }.addOnFailureListener {}
    }

}

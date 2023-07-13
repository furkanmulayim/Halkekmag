package com.furkanmulayim.halkekmag.presentation.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.furkanmulayim.halkekmag.R
import com.furkanmulayim.halkekmag.databinding.FragmentPostPaylasBinding
import com.furkanmulayim.halkekmag.presentation.viewmodel.PostShareViewModel

@Suppress("DEPRECATION")
class PostPaylasFragment : Fragment() {

    private lateinit var binding: FragmentPostPaylasBinding
    private lateinit var viewModel: PostShareViewModel

    private val pickImageCode = 1
    private val permissionRequestCode = 2
    private lateinit var uri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_paylas, container, false)


        viewModel = ViewModelProvider(this)[PostShareViewModel::class.java]


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        viewModel.setCurrentDate()

        buttonControls()
    }

    private fun sharePosts() {
        binding.progressBar.visibility = View.VISIBLE

        viewModel.sharePost(binding.titleTitle.text.toString(),
            binding.locationTitle.text.toString(),
            binding.dateTitle.text.toString(),
            binding.aboutTitle.text.toString(),
            uri,
            requireContext(),

            onSuccess = {
                binding.progressBar.visibility = View.GONE
                cancelPostShare()
                viewModel.showMessage(R.string.succ_post_submit, requireContext(), requireView())
            },
            onFailure = {
                binding.progressBar.visibility = View.GONE
                viewModel.showMessage(R.string.err_message, requireContext(), requireView())
            })

    }

    private fun cancelPostShare() {
        viewModel.navigate(R.id.action_postPaylasFragment_to_sheetFragment, requireView())
    }

    //Control of all buttons in post share fragment is here
    private fun buttonControls() {
        binding.shareButton.setOnClickListener {
            sharePosts()
        }
        binding.selectButton.setOnClickListener {
            if (checkPermission()) {
                openGallery()
            } else {
                requestPermission()
            }
        }

        binding.backButton.setOnClickListener {
            cancelPostShare()
        }
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            permissionRequestCode
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == permissionRequestCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, pickImageCode)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == pickImageCode && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            uri = data.data!!

            binding.sendPhoto.setImageURI(uri)
            binding.clickText.visibility = View.GONE
        }
    }
}
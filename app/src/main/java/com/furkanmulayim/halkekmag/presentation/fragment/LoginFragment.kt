package com.furkanmulayim.halkekmag.presentation.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.furkanmulayim.halkekmag.R
import com.furkanmulayim.halkekmag.databinding.FragmentLoginBinding
import com.furkanmulayim.halkekmag.presentation.viewmodel.LoginFragmentViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        viewModel = ViewModelProvider(this)[LoginFragmentViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.setCurrentDate()

        isLoggedIn()
        loginButtonClicked()
        loginCancelClicked()

        return binding.root
    }

    private fun isLoggedIn() {
        viewModel.loginSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                viewModel.showMessage(R.string.login_successful,requireContext(),requireView())
            } else {
                viewModel.showMessage(R.string.id_and_pass_incorrect,requireContext(),requireView())
            }
        }
    }

    private fun loginButtonClicked() {
        binding.loginButton.setOnClickListener {
            val userName = binding.loginKullaniciAdi.text.toString()
            val password = binding.loginSifre.text.toString()
            viewModel.login(userName, password, requireView(), requireContext())
        }
    }

    private fun loginCancelClicked() {
        binding.backButton.setOnClickListener {
            viewModel.navigate(R.id.action_loginFragment_to_sheetFragment,requireView())
        }
    }
}
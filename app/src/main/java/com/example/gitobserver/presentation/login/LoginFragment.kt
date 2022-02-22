package com.example.gitobserver.presentation.login

import android.graphics.Color.red
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.gitobserver.R
import com.example.gitobserver.databinding.FragmentLoginBinding
import com.example.gitobserver.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val mViewModel by viewModels<LoginViewModel>()
    private val mBinding by viewBinding(FragmentLoginBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI(view)
    }

    private fun setupUI(view: View) {
        mBinding.btn.setOnClickListener {
            requestLogin(view = view, inputToken = mBinding.etLoginToken.text.toString())
        }

        mBinding.etLoginToken.addTextChangedListener {
            updateErrorMessage(isError = false)
        }
    }

    private fun requestLogin(view: View, inputToken: String) {
        mViewModel.login(inputToken)
            .observe(viewLifecycleOwner) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mBinding.progress.visibility = View.GONE
                            moveToRepositories(view)
                        }
                        Status.ERROR -> {
                            mBinding.progress.visibility = View.GONE
                            updateErrorMessage(isError = true)
                        }
                        Status.LOADING -> {
                            mBinding.progress.visibility = View.VISIBLE
                            //updateErrorMessage(isError = false)
                        }
                    }
                }
            }
    }

    private fun moveToRepositories(view: View) {
        view.findNavController().navigate(R.id.action_loginFragment_to_repositoriesFragment)
    }

    private fun updateErrorMessage(isError: Boolean) {
        if (isError) {
            mBinding.etLayoutLoginToken.helperText = "Invalid token"
            //mBinding.etLoginToken.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            //mBinding.etLayoutLoginToken.stroke
        } else {
            mBinding.etLayoutLoginToken.helperText = ""
            //mBinding.etLoginToken.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
        }
    }
}
package com.example.ecom.fragments.loginRegister

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.ecom.R
import com.example.ecom.data.User
import com.example.ecom.databinding.FragmentRegisterBinding
import com.example.ecom.util.Resource
import com.example.ecom.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


private  val TAG = "RegisterFragment"
@AndroidEntryPoint
class RegisterFragment:Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonRegisterRegister.setOnClickListener{
                val user = User(
                    edFirstNameRegister.text.toString().trim(),
                    edLastNameRegister.text.toString().trim(),
                    edEmailRegister.text.toString().trim()
                )
                val password = edPasswordRegister.text.toString()
                viewModel.createAccountWithEmailAndPassword(user, password)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.register.collect{
                when(it){
                    is Resource.Loading -> {
                            binding.buttonRegisterRegister.startAnimation()
                    }
                    is Resource.success -> {
                     Log.d("test", it.data.toString())
                        binding.buttonRegisterRegister.revertAnimation()
                    }
                    is Resource.Error -> {
                      Log.e(TAG,it.message.toString())
                        binding.buttonRegisterRegister.revertAnimation()
                    }
                }
            }
        }

    }
}
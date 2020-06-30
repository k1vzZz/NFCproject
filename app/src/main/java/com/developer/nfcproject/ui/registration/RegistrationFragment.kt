package com.developer.nfcproject.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.developer.nfcproject.BuildConfig
import com.developer.nfcproject.R
import com.developer.nfcproject.extension.showView
import com.developer.nfcproject.ui.pin.PinFragmentDirections
import com.developer.nfcproject.utils.InjectorUtils
import kotlinx.android.synthetic.main.registration_fragment.view.*

class RegistrationFragment : Fragment() {

    private val viewModel: RegistrationViewModel by viewModels {
        InjectorUtils.provideRegistrationModelFactory(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.registration_fragment, container, false).apply {

//        if (!BuildConfig.BETA) {
//            findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToOperationsFragment())
//        }
        buttonRegistration.setOnClickListener {
            findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToOperationsFragment())
        }
        submitButton.setOnClickListener {
            val number: String = editTextPhone.text.toString().trim { it <= ' ' }
            if (number.isEmpty() || number.length < 10) {
                editTextPhone.error = "Valid number is required"
                editTextPhone.requestFocus()
            }
            val phoneNumber = "+7$number"
            viewModel.registerDevice(phoneNumber)
        }

        viewModel.needPinInput.observe(viewLifecycleOwner) {
            if (it) {
                val action =
                    RegistrationFragmentDirections.actionRegistrationFragmentToPinFragment(
                        viewModel.phoneNumber!!,
                        viewModel.verificationId!!
                    )
                findNavController().navigate(action)
            }
        }

        viewModel.isGeneralError.observe(viewLifecycleOwner) {
            errorText.showView(true)
            errorText.text = it
        }

        viewModel.goToWelcome.observe(viewLifecycleOwner) {
            // move welcome
        }
    }
}
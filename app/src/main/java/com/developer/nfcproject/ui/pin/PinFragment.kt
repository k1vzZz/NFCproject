package com.developer.nfcproject.ui.pin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.developer.nfcproject.R
import com.developer.nfcproject.extension.showView
import com.developer.nfcproject.utils.InjectorUtils
import kotlinx.android.synthetic.main.pin_fragment.view.*

class PinFragment : Fragment() {

    private val viewModel: PinViewModel by viewModels {
        InjectorUtils.providePinModelFactory(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.pin_fragment, container, false).apply {

        arguments?.let {
            viewModel.phone = PinFragmentArgs.fromBundle(it).phoneNumber
            viewModel.verificationId = PinFragmentArgs.fromBundle(it).verificationId
        }

        submitPinBtn.setOnClickListener {
            viewModel.verifyCode(pinInputEditText.text.toString())
        }

        viewModel.goToWelcome.observe(viewLifecycleOwner) {
            // move welcome
            if (it) {
                findNavController().navigate(PinFragmentDirections.actionPinFragmentToUserPinFragment())
            }
        }

        viewModel.isGeneralError.observe(viewLifecycleOwner) {
            pinErrorText.showView(true)
            pinErrorText.text = it
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().moveTaskToBack(true)
                }
            }
        )
    }
}
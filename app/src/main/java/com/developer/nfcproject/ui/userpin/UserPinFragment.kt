package com.developer.nfcproject.ui.userpin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.developer.nfcproject.R
import com.developer.nfcproject.ui.pin.PinFragmentDirections
import com.developer.nfcproject.ui.pin.PinViewModel
import com.developer.nfcproject.utils.InjectorUtils
import kotlinx.android.synthetic.main.user_pin_code_fragment.view.*

class UserPinFragment : Fragment() {

    private val viewModel: UserPinViewModel by viewModels {
        InjectorUtils.provideUserPinModelFactory(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.user_pin_code_fragment, container, false).apply {

        editTextPin.doAfterTextChanged {
            if (it != null) {
                if (it.length == 4){
                    viewModel.saveUserPin(it.toString())
                }
            }
        }

        viewModel.goToWelcome.observe(viewLifecycleOwner) {
            // move welcome
            if (it) {
                findNavController().navigate(UserPinFragmentDirections.actionUserPinFragmentToOperationsFragment())
            }
        }
    }
}
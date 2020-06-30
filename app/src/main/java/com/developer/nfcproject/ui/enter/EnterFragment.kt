package com.developer.nfcproject.ui.enter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.developer.nfcproject.CountryData
import com.developer.nfcproject.R
import kotlinx.android.synthetic.main.registration_fragment.view.*

class EnterFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.registration_fragment, container, false).apply {

    }
}
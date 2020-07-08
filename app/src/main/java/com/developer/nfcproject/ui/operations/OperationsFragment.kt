package com.developer.nfcproject.ui.operations

import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.developer.nfcproject.BaseViewModel
import com.developer.nfcproject.R
import com.developer.nfcproject.utils.InjectorUtils
import kotlinx.android.synthetic.main.operations_fragment.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class OperationsFragment : Fragment() {

    private val viewModel: OperationsViewModel by viewModels {
        InjectorUtils.provideOperationsModelFactory(requireActivity())
    }

    private lateinit var modeAdapter: ModeAdapter
    private var selectedPosition: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.operations_fragment, container, false).apply {

        modeAdapter = ModeAdapter(
            listOf(
                Mode(
                    R.string.app_name,
                    R.string.app_name,
                    0,
                    OperationsFragmentDirections.actionOperationsFragmentToSettingsFragment()
                ),
                Mode(
                    R.string.app_name, //select card
                    R.string.app_name,
                    0,
                    OperationsFragmentDirections.actionOperationsFragmentToSettingsFragment()
                )
            )
        )

        modeAdapter.setOnClickListener {
            findNavController().navigate(modeAdapter.getItem(modeViewPager.currentItem).navDirections)
        }

        modeViewPager.adapter = modeAdapter

        modeViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) = updateColor(this@apply, position)
        })

        with(modeViewPager) {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 1
        }

        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        val operationsItemAdapter = OperationsAdapter {
            val dateFormat = SimpleDateFormat("dd MMMM HH:mm:ss", Locale.getDefault())
            val date = dateFormat.format(Date(it.operationDate))
            Toast.makeText(
                requireContext(),
                String.format("Transport #%d\n%s", it.transportNumber, date),
                Toast.LENGTH_LONG
            ).show()
        }
        recyclerView.adapter = operationsItemAdapter

        settingsBtn.setOnClickListener {
            operationsItemAdapter.submitList(viewModel.items)
        }

        activity?.let {
            val baseViewModel = ViewModelProvider(it).get(BaseViewModel::class.java)
            baseViewModel.nfcIntent.observe(viewLifecycleOwner) { nfcModel ->
                Toast.makeText(it, String.format("TransportNumber #%s", nfcModel.messages?.transportNumber), Toast.LENGTH_LONG).show()
                viewModel.processPay(nfcModel)
            }
        }

        viewModel.isSuccessPay.observe(viewLifecycleOwner) {
            if (it) {
                //Toast.makeText(context, String.format("Success"), Toast.LENGTH_LONG).show()
            }
        }

        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.view_pager_margin)
        val offsetPx = resources.getDimensionPixelOffset(R.dimen.view_pager_offset)
        modeViewPager.setPageTransformer { page, position ->
            val offset = position * -(2 * offsetPx + pageMarginPx)
            page.translationX = offset
            val scaleFactor = 1f - abs(position) * 0.125f
            page.scaleY = scaleFactor
        }
    }

    private fun updateColor(rootView: View, newPosition: Int) {
        //rootView.descriptionTextView.setText(modeAdapter.getItem(newPosition).descriptionRes)

        var descriptionColorRes: Int? = null
        var logoColorRes: Int? = null
        when (newPosition) {
            0 -> {
                descriptionColorRes = R.color.welcome_description_default_color
                logoColorRes = R.color.welcome_logo_grey_color
            }
            1 -> {
                descriptionColorRes = R.color.welcome_description_color
                logoColorRes = R.color.welcome_logo_blue_color
            }
        }
//        descriptionColorRes?.let {
//            rootView.descriptionTextView.setTextColor(
//                ContextCompat.getColor(rootView.context, it)
//            )
//        }
        logoColorRes?.let { modeAdapter.setLogoColor(it) }

        var backgroundRes: Int? = null
        var cardBackgroundRes: Int? = null
        when (selectedPosition) {
            0 -> {
                backgroundRes = R.drawable.welcome_default_background_color
                cardBackgroundRes = R.drawable.welcome_card_default_background_color
            }
            1 -> {
                backgroundRes = R.drawable.welcome_background_color
                cardBackgroundRes = R.drawable.welcome_card_background_color
            }
        }
        backgroundRes?.let { rootView.setBackgroundResource(it) }
        cardBackgroundRes?.let { modeAdapter.setBackgroundResource(it) }

        if (selectedPosition != newPosition) {
            selectedPosition = newPosition

            val transition = rootView.background as TransitionDrawable
            transition.startTransition(300)
            modeAdapter.startBackgroundTransition(300)
        }
    }
}
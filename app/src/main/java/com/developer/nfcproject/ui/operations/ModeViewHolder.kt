package com.developer.nfcproject.ui.operations

import android.graphics.drawable.TransitionDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.mode_view_holder.view.*

class ModeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Mode) = with(itemView) {
        //titleTextView.setText(item.titleRes)
        logoImageView.setImageResource(item.logoRes)
    }

    fun startBackgroundTransition(durationMillis: Int) = with(itemView) {
        val transition = modeCardLayout.background as TransitionDrawable
        transition.startTransition(durationMillis)
    }

    fun setBackgroundResource(res: Int) = with(itemView) {
        modeCardLayout.setBackgroundResource(res)
    }

    fun setLogoColor(res: Int) = with(itemView) {
        logoImageView.setColorFilter(ContextCompat.getColor(context, res))
    }
}
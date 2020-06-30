package com.developer.nfcproject.ui.operations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developer.nfcproject.R

class ModeAdapter(private val items: List<Mode>) : RecyclerView.Adapter<ModeViewHolder>() {

    private var listener: (() -> Unit)? = null
    private val viewHolders: MutableList<ModeViewHolder> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.mode_view_holder, parent, false)
        view.setOnClickListener { listener?.invoke() }

        val viewHolder = ModeViewHolder(view)
        viewHolders.add(viewHolder)

        return viewHolder
    }

    override fun onBindViewHolder(holder: ModeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun getItem(position: Int) = items[position]

    fun setOnClickListener(listener: () -> Unit) {
        this.listener = listener
    }

    fun startBackgroundTransition(durationMillis: Int) {
        viewHolders.forEach {
            it.startBackgroundTransition(durationMillis)
        }
    }

    fun setBackgroundResource(res: Int) {
        viewHolders.forEach {
            it.setBackgroundResource(res)
        }
    }

    fun setLogoColor(res: Int) {
        viewHolders.forEach {
            it.setLogoColor(res)
        }
    }

}
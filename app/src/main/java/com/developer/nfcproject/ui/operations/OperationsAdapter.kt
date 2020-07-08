package com.developer.nfcproject.ui.operations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.developer.nfcproject.R
import com.developer.nfcproject.models.OperationsModel

class OperationsAdapter (
    private val onItemClick: (OperationsModel) -> Unit
) : ListAdapter<OperationsModel, OperationsViewHolder>(
    object : DiffUtil.ItemCallback<OperationsModel>() {
        override fun areItemsTheSame(oldItem: OperationsModel, newItem: OperationsModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: OperationsModel,
            newItem: OperationsModel
        ): Boolean {
            return oldItem.operation == newItem.operation
                    && oldItem.operationDate == newItem.operationDate
                    && oldItem.transportNumber == newItem.transportNumber
                    && oldItem.transportType == newItem.transportType
        }
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OperationsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.operations_view_holder, parent, false)
    )

    override fun onBindViewHolder(holder: OperationsViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }

}
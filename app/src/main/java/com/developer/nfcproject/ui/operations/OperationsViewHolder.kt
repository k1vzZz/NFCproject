package com.developer.nfcproject.ui.operations

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.developer.nfcproject.models.OperationsModel
import kotlinx.android.synthetic.main.operations_view_holder.view.*
import java.text.SimpleDateFormat
import java.util.*

class OperationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(
        item: OperationsModel,
        onItemClick: (OperationsModel) -> Unit
    ) = with(itemView) {
        //val date = Date(item.operationDate.time)
        val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
        val test = dateFormat.format(Date(item.operationDate))
        date.text = test.toString()
        numberTransport.text = String.format("Transport #%d", item.transportNumber)
        typeTransport.text = item.transportType
        operation.text = item.operation

        val imageName = RESOURCES_MAPPING.getOrDefault(item.transportType, "bus")
        val imageId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
        imageTransport.setImageResource(imageId)

        itemView.setOnClickListener { onItemClick.invoke(item) }
    }

    companion object {
        @JvmStatic
        val RESOURCES_MAPPING = mapOf(
            "BUS" to "icon_bus",
            "MINIBUS" to "icon_minibus",
            "TRAM" to "icon_tram"
        )
    }
}
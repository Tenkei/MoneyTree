package com.esbati.keivan.moneytreelight

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class DataViewHolder<T>(itemView: ViewGroup): RecyclerView.ViewHolder(itemView) {
    var data: T? = null
    fun bindData(item: T) {
        data = item
        setupView(item)
    }

    abstract fun setupView(item: T)
}
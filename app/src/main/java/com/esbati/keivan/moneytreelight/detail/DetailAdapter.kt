package com.esbati.keivan.moneytreelight.detail

import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.esbati.keivan.moneytreelight.Transaction

class TransactionAdapter(
    private val hook: (Transaction) -> Unit
) : ListAdapter<Transaction, TransactionViewHolder>(TransactionDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(LinearLayout(parent.context), hook)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }
}

class TransactionViewHolder(itemView: ViewGroup, hook: (Transaction) -> Unit): RecyclerView.ViewHolder(itemView) {

    private var data: Transaction? = null
    private val textView = TextView(itemView.context)

    init {
        itemView.setOnClickListener { data?.let{ hook.invoke(it) } }
        itemView.addView(textView)
    }

    fun bindView(Transaction: Transaction){
        data = Transaction
        textView.text = Transaction.description
    }
}

object TransactionDiffCallback : DiffUtil.ItemCallback<Transaction>() {
    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction) = oldItem == newItem
}

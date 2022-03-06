package com.esbati.keivan.moneytreelight.detail

import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.esbati.keivan.moneytreelight.R
import com.esbati.keivan.moneytreelight.Transaction
import com.esbati.keivan.moneytreelight.inflate

class TransactionAdapter(
    private val hook: (Transaction) -> Unit
) : ListAdapter<Transaction, TransactionViewHolder>(TransactionDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(parent.inflate(R.layout.cell_transaction) as ViewGroup, hook)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }
}

class TransactionViewHolder(itemView: ViewGroup, hook: (Transaction) -> Unit): RecyclerView.ViewHolder(itemView) {

    private var data: Transaction? = null
    private val date: TextView = itemView.findViewById(R.id.transaction_date)
    private val description: TextView = itemView.findViewById(R.id.transaction_description)
    private val amount: TextView = itemView.findViewById(R.id.transaction_amount)

    init {
        itemView.setOnClickListener { data?.let{ hook.invoke(it) } }
    }

    fun bindView(transaction: Transaction){
        data = transaction
        date.text = transaction.date
        description.text = transaction.description
        amount.text = transaction.amount.toString()
    }
}

object TransactionDiffCallback : DiffUtil.ItemCallback<Transaction>() {
    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction) = oldItem == newItem
}

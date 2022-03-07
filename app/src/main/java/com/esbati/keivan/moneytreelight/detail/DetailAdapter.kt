package com.esbati.keivan.moneytreelight.detail

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.esbati.keivan.moneytreelight.DataViewHolder
import com.esbati.keivan.moneytreelight.R
import com.esbati.keivan.moneytreelight.inflate

private const val ITEM_MONTH_HEADER = 0
private const val ITEM_TRANSACTION = 1

class DetailAdapter() : ListAdapter<DetailRow, DataViewHolder<DetailRow>>(TransactionDiffCallback) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MonthHeaderRow -> ITEM_MONTH_HEADER
            is TransactionRow -> ITEM_TRANSACTION
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<DetailRow> =
        when (viewType) {
            ITEM_MONTH_HEADER -> MonthHeaderViewHolder((parent.inflate(R.layout.cell_month) as ViewGroup)) as DataViewHolder<DetailRow>
            ITEM_TRANSACTION -> TransactionViewHolder(parent.inflate(R.layout.cell_transaction) as ViewGroup) as DataViewHolder<DetailRow>
            else -> throw IllegalArgumentException("Item with viewType $viewType is not supported!")
        }

    override fun onBindViewHolder(holder: DataViewHolder<DetailRow>, position: Int) {
        holder.bindData(getItem(position))
    }
}

class MonthHeaderViewHolder(itemView: ViewGroup) : DataViewHolder<MonthHeaderRow>(itemView) {

    private val date: TextView = itemView.findViewById(R.id.header_date)
    private val balanceIn: TextView = itemView.findViewById(R.id.header_balance_in)
    private val balanceOut: TextView = itemView.findViewById(R.id.header_balance_out)

    override fun setupView(item: MonthHeaderRow) {
        date.text = "${item.year}-${item.month}"
        balanceIn.text = item.balanceIn
        balanceOut.text = item.balanceOut
    }
}

class TransactionViewHolder(itemView: ViewGroup) : DataViewHolder<TransactionRow>(itemView) {

    private val date: TextView = itemView.findViewById(R.id.transaction_date)
    private val description: TextView = itemView.findViewById(R.id.transaction_description)
    private val amount: TextView = itemView.findViewById(R.id.transaction_amount)

    override fun setupView(item: TransactionRow) {
        date.text = item.date
        description.text = item.detail
        amount.text = item.amount
    }
}

object TransactionDiffCallback : DiffUtil.ItemCallback<DetailRow>() {
    override fun areItemsTheSame(oldItem: DetailRow, newItem: DetailRow) = when (newItem) {
        is MonthHeaderRow -> oldItem is MonthHeaderRow && oldItem.year == newItem.year && oldItem.month == newItem.month
        is TransactionRow -> oldItem is TransactionRow && oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DetailRow, newItem: DetailRow) = oldItem == newItem
}

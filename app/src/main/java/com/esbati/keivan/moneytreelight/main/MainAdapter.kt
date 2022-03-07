package com.esbati.keivan.moneytreelight.main

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.esbati.keivan.moneytreelight.DataViewHolder
import com.esbati.keivan.moneytreelight.R
import com.esbati.keivan.moneytreelight.inflate
import java.lang.IllegalArgumentException

private const val ITEM_INSTITUTION = 0
private const val ITEM_ACCOUNT = 1

class Adapter(
    private val hook: (AccountRow) -> Unit
) : ListAdapter<MainRow, DataViewHolder<MainRow>>(AccountDiffCallback) {

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is InstitutionRow -> ITEM_INSTITUTION
            is AccountRow -> ITEM_ACCOUNT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<MainRow> {
        return when(viewType){
            ITEM_INSTITUTION -> InstitutionViewHolder(parent.inflate(R.layout.cell_institution) as ViewGroup) as DataViewHolder<MainRow>
            ITEM_ACCOUNT -> AccountViewHolder(parent.inflate(R.layout.cell_account) as ViewGroup, hook) as DataViewHolder<MainRow>
            else -> throw IllegalArgumentException("Item with viewType $viewType is not supported!")
        }
    }

    override fun onBindViewHolder(holder: DataViewHolder<MainRow>, position: Int) {
        holder.bindData(getItem(position))
    }
}

class InstitutionViewHolder(itemView: ViewGroup): DataViewHolder<InstitutionRow>(itemView) {

    private val title: TextView = itemView.findViewById(R.id.account_institution)

    override fun setupView(item: InstitutionRow) {
        title.text = item.name
    }
}

class AccountViewHolder(itemView: ViewGroup, hook: (AccountRow) -> Unit): DataViewHolder<AccountRow>(itemView) {

    private val name: TextView = itemView.findViewById(R.id.account_name)
    private val balance: TextView = itemView.findViewById(R.id.account_current_balance)

    init {
        itemView.setOnClickListener { data?.let{ hook.invoke(it) } }
    }

    override fun setupView(item: AccountRow) {
        name.text = item.name
        balance.text = item.balance
    }
}

object AccountDiffCallback : DiffUtil.ItemCallback<MainRow>() {
    override fun areItemsTheSame(oldItem: MainRow, newItem: MainRow) = when(newItem){
        is InstitutionRow -> oldItem is InstitutionRow && oldItem.name == newItem.name
        is AccountRow -> oldItem is AccountRow && oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: MainRow, newItem: MainRow) = oldItem == newItem
}

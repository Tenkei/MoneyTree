package com.esbati.keivan.moneytreelight.main

import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.esbati.keivan.moneytreelight.Account
import com.esbati.keivan.moneytreelight.R
import com.esbati.keivan.moneytreelight.inflate

class Adapter(
    private val hook: (Account) -> Unit
) : ListAdapter<Account, AccountViewHolder>(AccountDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        return AccountViewHolder(parent.inflate(R.layout.cell_account) as ViewGroup, hook)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }
}

class AccountViewHolder(itemView: ViewGroup, hook: (Account) -> Unit): RecyclerView.ViewHolder(itemView) {

    private var data: Account? = null

    private val title: TextView = itemView.findViewById(R.id.account_institution)
    private val name: TextView = itemView.findViewById(R.id.account_name)
    private val balance: TextView = itemView.findViewById(R.id.account_current_balance)

    init {
        itemView.setOnClickListener { data?.let{ hook.invoke(it) } }
    }

    fun bindView(account: Account){
        data = account
        title.text = account.institution
        name.text = account.name
        balance.text = account.currency + account.current_balance
    }
}

object AccountDiffCallback : DiffUtil.ItemCallback<Account>() {
    override fun areItemsTheSame(oldItem: Account, newItem: Account) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Account, newItem: Account) = oldItem == newItem
}

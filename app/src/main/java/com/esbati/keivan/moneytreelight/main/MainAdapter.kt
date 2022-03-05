package com.esbati.keivan.moneytreelight.main

import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.esbati.keivan.moneytreelight.Account

class Adapter(
    private val hook: (Account) -> Unit
) : ListAdapter<Account, AccountViewHolder>(AccountDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        return AccountViewHolder(LinearLayout(parent.context), hook)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }
}

class AccountViewHolder(itemView: ViewGroup, hook: (Account) -> Unit): RecyclerView.ViewHolder(itemView) {

    private var data: Account? = null
    private val textView = TextView(itemView.context)

    init {
        itemView.setOnClickListener { data?.let{ hook.invoke(it) } }
        itemView.addView(textView)
    }

    fun bindView(account: Account){
        data = account
        textView.text = account.name
    }
}

object AccountDiffCallback : DiffUtil.ItemCallback<Account>() {
    override fun areItemsTheSame(oldItem: Account, newItem: Account) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Account, newItem: Account) = oldItem == newItem
}

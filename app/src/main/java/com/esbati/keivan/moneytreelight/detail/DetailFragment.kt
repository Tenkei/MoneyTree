package com.esbati.keivan.moneytreelight.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esbati.keivan.moneytreelight.ServiceLocator
import com.esbati.keivan.moneytreelight.data.Repository
import kotlinx.coroutines.Dispatchers

class DetailFragment : Fragment() {

    private val viewModel by lazy {
        DetailViewModel(
            lifecycleScope,
            ServiceLocator.getInstance().get(),
            requireArguments().getLong("id")
        )
    }

    private lateinit var recyclerView: RecyclerView
    private val adapter = DetailAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        recyclerView = RecyclerView(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        return recyclerView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.transactions.observe(this as LifecycleOwner) {
            adapter.submitList(it)
        }
    }
}

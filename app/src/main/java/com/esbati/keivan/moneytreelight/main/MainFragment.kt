package com.esbati.keivan.moneytreelight.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esbati.keivan.moneytreelight.FakeRepository
import kotlinx.coroutines.Dispatchers

class MainFragment : Fragment() {

    private val viewModel by lazy {
        MainViewModel(FakeRepository(requireContext(), Dispatchers.IO))
    }

    private lateinit var recyclerView: RecyclerView
    private val adapter = Adapter { Toast.makeText(context, it.name, Toast.LENGTH_LONG).show() }

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
        viewModel.accounts.observe(this as LifecycleOwner) {
            adapter.submitList(it)
        }
    }
}

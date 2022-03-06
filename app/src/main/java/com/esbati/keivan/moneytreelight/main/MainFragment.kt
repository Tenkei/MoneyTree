package com.esbati.keivan.moneytreelight.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esbati.keivan.moneytreelight.FakeRepository
import com.esbati.keivan.moneytreelight.detail.DetailFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

class MainFragment : Fragment() {

    private val viewModel by lazy {
        MainViewModel(lifecycleScope, FakeRepository(requireContext(), Dispatchers.IO))
    }

    private lateinit var recyclerView: RecyclerView
    private val adapter = Adapter {
        activity?.supportFragmentManager?.commit {
            replace(
                android.R.id.content,
                DetailFragment::class.java,
                bundleOf("id" to it.id),
                null
            )
            addToBackStack("detail")
        }
    }

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

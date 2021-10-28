package com.example.hiltexample.view.menu

import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import androidx.navigation.navGraphViewModels
import com.example.hiltexample.R
import com.example.hiltexample.base.BaseFragment
import com.example.hiltexample.data.Status
import com.example.hiltexample.databinding.FragmentMenuBinding
import com.example.hiltexample.view.MenuVM
import com.example.hiltexample.view.adapter.MenuAdapter
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding>(R.layout.fragment_menu) {

    lateinit var menuAdapter: MenuAdapter
    @ExperimentalCoroutinesApi
    private val menuVM: MenuVM by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuVM.apiView.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    e { "data : ${it.data}" }
                    menuAdapter = MenuAdapter(requireContext(), it.data!!)
                    binding.lvMenu.adapter = menuAdapter
                    menuAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> e(it.throwable)
                Status.LOADING -> i { "Loading" }
            }
        })

        menuVM.getCovidView("summary")
        e { "denememem" }
    }
}
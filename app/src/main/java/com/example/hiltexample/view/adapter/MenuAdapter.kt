package com.example.hiltexample.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hiltexample.base.BaseAdapter
import com.example.hiltexample.databinding.ItemMenuBinding
import com.example.hiltexample.model.Country

class MenuAdapter(
    private val context: Context,
    private val data: List<Country>,
) : BaseAdapter<Country>(context, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemMenuBinding
        if (convertView == null) {
            binding = ItemMenuBinding.inflate(LayoutInflater.from(context), parent, false)
            binding.root.tag = binding
        } else {
            binding = convertView.tag as ItemMenuBinding
        }

        binding.covid = getItem(position)
        val number = position+1
        binding.number.text = number.toString()

        return binding.root
    }
}
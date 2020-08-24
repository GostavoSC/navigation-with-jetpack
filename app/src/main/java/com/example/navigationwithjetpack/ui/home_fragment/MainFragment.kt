package com.example.navigationwithjetpack.ui.home_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.navigationwithjetpack.R
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        setupViewModel()
        observerValues()
        return view
    }

    private fun observerValues() {
        viewModel.getValues()
        viewModel.valorFinal.observe(requireActivity(), Observer {
            var value = it.toString().replace(".", ",") + "0"
            valueDividas.text = value
            value = (carteira.text.toString().toDouble() - it).toString().replace(".", ",") + "0"
            sobramValue.text = value
        })
    }

    private fun setupViewModel() {
        viewModel = MainViewModel()
        viewModel.inicaRepository(requireContext())
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {

            }
    }
}
package com.example.navigationwithjetpack.ui.list_fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationwithjetpack.R
import com.example.navigationwithjetpack.data.database.entity.Divida
import com.example.navigationwithjetpack.ui.dummy.DummyContent
import com.example.navigationwithjetpack.ui.list_fragment.adapters.MyItemRecyclerViewAdapter
import com.example.navigationwithjetpack.ui.list_fragment.adapters.MyItemRecyclerViewAdapter.BtnClickListener
import com.example.navigationwithjetpack.ui.list_fragment.adapters.MyItemRecyclerViewAdapter.BtnOnLongClickListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class ListFragment() : Fragment() {

    private var columnCount = 1
    private lateinit var viewModel: ListViewModel


    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ListViewModel(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
        pegaLista()
    }

    private lateinit var adapter2: MyItemRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_list, container, false)
        val recyclerView = view.findViewById(R.id.list) as RecyclerView

        onClickFabButtonInserDivida(view)
        with(recyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter=
                MyItemRecyclerViewAdapter(
                    DummyContent.ITEMS,
                    object : BtnClickListener {
                        override fun onBtnClick(position: Int) {
                            Toast.makeText(context, "Clique normal", Toast.LENGTH_SHORT).show()
                        }
                    },
                object : BtnOnLongClickListener{
                    override fun onBtnOnLongClick(position: Int) {
                        Toast.makeText(context, "Longo clique", Toast.LENGTH_SHORT).show()
                    }

                })
            adapter2  = adapter as MyItemRecyclerViewAdapter
        }
        return view
    }


    private fun pegaLista() {
        viewModel.listaLiveDta.observe(this, Observer {
            DummyContent.addAllItem(it)
            adapter2.setValuesFromList(DummyContent.ITEMS)
        })
    }


    private fun onClickFabButtonInserDivida(view: View) {
        val fabAddDivida = view.findViewById<FloatingActionButton>(R.id.fab_add_dividas)
        fabAddDivida.setOnClickListener {

            val date = Date()
            val divida = Divida(date.time.toString(), 20.0)
            viewModel.insertDivida(divida)

        }
    }

    override fun onResume() {
        super.onResume()
        pegaLista()
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        fun newInstance(columnCount: Int) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
package com.example.navigationwithjetpack.ui.list_fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.navigationwithjetpack.ui.util.DialogCustom
import com.example.navigationwithjetpack.ui.util.DialogCustom.OnDialogConfirmationListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class ListFragment() : Fragment() {

    private var columnCount = 1
    private lateinit var viewModel: ListViewModel
    private lateinit var popupDialog: DialogCustom

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
        setupSaveDialog(inflater)

        onClickFabButtonInserDivida(view)
        with(recyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter =
                MyItemRecyclerViewAdapter(
                    DummyContent.ITEMS,
                    object : BtnClickListener {
                        override fun onBtnClick(divida: Divida) {
                            showEditDialog(divida)
                        }
                    }
                    , object : MyItemRecyclerViewAdapter.BtnClickListenerDelete {
                        override fun onBtnClickDelete(divida: Divida) {
                            removeDivida(divida)
                        }
                    })
            adapter2 = adapter as MyItemRecyclerViewAdapter
        }
        return view
    }

    fun removeDivida(divida: Divida) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Remover")
            .setMessage("Deseja mesmo remover esta dívida?")
            .setNegativeButton("Sim") { dialog, which ->
                viewModel.removeDivida(divida)
                Snackbar.make(requireView(), getString(R.string.removeu), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.ok)) {
                    }
                    .show()
            }
            .setPositiveButton("não") { dialog, which ->
            }
            .show()
    }

    private fun pegaLista() {
        viewModel.listaLiveDta.observe(this, Observer {
            DummyContent.addAllItem(it)
            adapter2.setValuesFromList(DummyContent.ITEMS)
        })
    }

    private fun setupSaveDialog(inflater: LayoutInflater) {
        popupDialog = DialogCustom(inflater, getString(R.string.cadastro))
        popupDialog.setSaveAndCancelButton()
        observeIfIsAbleToRegister()
        observeNewProductNameError()
    }

    private fun prepareSaveEditDialog() {
        popupDialog.showDialog()
        popupDialog.setNameTextChangedListener(getProductNameTextWatcher())
        popupDialog.setValueTextChangedListener(getDividaValueTextWatcher())
        popupDialog.setPositiveButtonEnabled(popupDialog.checkIfDataIsValid())
    }

    private fun showSaveDialog() {
        prepareSaveEditDialog()
        popupDialog.name = ""
        popupDialog.value = ""
        popupDialog.setNameError(false)
        popupDialog.onDialogConfirmationListener =
            object : OnDialogConfirmationListener {
                override fun onConfirmation(name: String, value: Double) {

                    saveDivida(
                        name,
                        value
                    )
                }
            }
    }

    private fun saveDivida(nameDivida: String, valueDivida: Double) {
        val divida = Divida(nameDivida, valueDivida)
        if (viewModel.verificaSeJaExisteDivida(divida)) {
            Snackbar.make(requireView(), getString(R.string.jaExiste), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.ok)) {
                }
                .show()
        } else {
            viewModel.insertDivida(divida)
            Snackbar.make(requireView(), getString(R.string.inseriu), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.ok)) {
                }
                .show()
        }
    }

    private fun onClickFabButtonInserDivida(view: View) {
        val fabAddDivida = view.findViewById<FloatingActionButton>(R.id.fab_add_dividas)
        fabAddDivida.setOnClickListener {
            showSaveDialog()
        }
    }

    private fun showEditDialog(divida: Divida) {
        prepareSaveEditDialog()
        popupDialog.name = divida.nameDivida
        popupDialog.value = divida.valueDivida.toString()
        popupDialog.onDialogConfirmationListener =
            object : OnDialogConfirmationListener {
                override fun onConfirmation(name: String, value: Double) {
                    editDivida(
                        name,
                        value,
                        divida

                    )
                }
            }
    }

    private fun editDivida(nameDivida: String, valueDivida: Double, divida: Divida) {
        val newDivida = Divida(nameDivida, valueDivida)
        if (viewModel.verificaSeJaExisteDivida(newDivida)) {
            Snackbar.make(requireView(), getString(R.string.jaExiste), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.ok)) {
                    // Responds to click on the action
                }
                .show()
        } else {
            viewModel.updateDivida(newDivida, divida)
            Snackbar.make(requireView(), getString(R.string.editou), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.ok)) {
                }
                .show()
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

    private fun observeIfIsAbleToRegister() {
        viewModel.getPodeCadastrar()?.observe(requireActivity(), Observer {
            popupDialog.setPositiveButtonEnabled(popupDialog.checkIfDataIsValid())
        })
        viewModel.podeCadastrarValue.observe(requireActivity(), Observer {
            popupDialog.setPositiveButtonEnabled(popupDialog.checkIfDataIsValid())
        })
    }
    private fun observeNewProductNameError() {
        viewModel.getErroDivida()?.observe(requireActivity(), Observer{ hasError ->
            popupDialog.setNameError(hasError)
            popupDialog.setPositiveButtonEnabled(popupDialog.checkIfDataIsValid())
        })

        viewModel.getErroDividaValue()?.observe(requireActivity(), Observer {
            popupDialog.setValueError(it)
            popupDialog.setPositiveButtonEnabled(popupDialog.checkIfDataIsValid())
        })
    }

    private fun getProductNameTextWatcher(): TextWatcher? {
        return object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
                // Unused method
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                // Unused method
            }

            override fun afterTextChanged(s: Editable) {
                val dividaName = s.toString()
                viewModel.verificarNomeDaDivida(dividaName)
            }
        }
    }
    private fun getDividaValueTextWatcher(): TextWatcher? {
        return object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
                // Unused method
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                // Unused method
            }

            override fun afterTextChanged(s: Editable) {
                val dividaName = s.toString()
                viewModel.verificarValorDivida(dividaName)
            }
        }
    }
}
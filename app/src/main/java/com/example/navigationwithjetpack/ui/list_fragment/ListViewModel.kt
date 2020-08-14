package com.example.navigationwithjetpack.ui.list_fragment

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationwithjetpack.data.database.entity.Divida
import com.example.navigationwithjetpack.data.repository.divida.DividaHelperIpImpl
import kotlinx.coroutines.launch


class ListViewModel(
    context: Context
) : ViewModel() {

    var listaLiveDta = MutableLiveData<List<Divida>>()
    private var repositoryDivida: DividaHelperIpImpl =
        DividaHelperIpImpl(
            context
        )
    private var inseriu = MutableLiveData<Boolean>()

    init {
        loadAllDividas()
    }

    private fun loadAllDividas() {
        viewModelScope.launch {
            try {
                listaLiveDta.postValue(repositoryDivida.getAllDividas())
            } catch (e: RuntimeException) {
                Log.e("Lista", "Não pegou")
            }
        }
    }

    fun verificaSeJaExisteDivida(divida: Divida): Boolean {
        var existe = false
        if (listaLiveDta.value != null) {
            for (i in listaLiveDta.value!!) {
                if (i.nameDivida.equals(divida.nameDivida) &&
                    i.valueDivida == divida.valueDivida
                ) {
                    existe = true
                }
            }
        }
        return existe
    }

    fun updateDivida(newDivida: Divida, divida: Divida) {
        viewModelScope.launch {
            try {
                divida.nameDivida = newDivida.nameDivida
                divida.valueDivida = newDivida.valueDivida
                repositoryDivida.updateDivida(divida)
                loadAllDividas()
            } catch (e: java.lang.RuntimeException) {
                Log.e("Update", "Não rolou")
            }
        }
    }

    fun removeDivida(divida: Divida) {
        viewModelScope.launch {
            try {
                repositoryDivida.removeDivida(divida)
            } catch (e: java.lang.RuntimeException) {
                Log.e("Delete", "Não rolou")
            }

        }
    }

    fun insertDivida(divida: Divida) {
        viewModelScope.launch {
            try {
                if (!verificaSeJaExisteDivida(divida)) {
                    repositoryDivida.insertDivida(divida)
                }
                loadAllDividas()
            } catch (e: java.lang.RuntimeException) {
                Log.e("Inserir", "Não inseriu")
            }
        }
    }
}
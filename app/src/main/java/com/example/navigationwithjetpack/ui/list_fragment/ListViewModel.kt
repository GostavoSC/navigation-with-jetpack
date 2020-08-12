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


    fun insertDivida(divida: Divida) {
        viewModelScope.launch {
            try {
                repositoryDivida.insertDivida(divida)
            } catch (e: java.lang.RuntimeException) {
                Log.e("Inserir", "Não inseriu")
            }
        }
    }
}
package com.example.navigationwithjetpack.ui.list_fragment

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationwithjetpack.data.database.AppDatabase
import com.example.navigationwithjetpack.data.database.entity.Divida
import com.example.navigationwithjetpack.data.repository.divida.DividaHelperImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ListViewModel(
) : ViewModel() {

    var listaLiveDta = MutableLiveData<List<Divida>>()
    var erroNameDivida = MutableLiveData<Boolean>()
    var podeCadastrar = MutableLiveData<Boolean>()
    var erroNameDividaValue = MutableLiveData<Boolean>()
    var podeCadastrarValue = MutableLiveData<Boolean>()
    private lateinit var repositoryDivida: DividaHelperImpl

    fun iniciaRepository(context: Context) {
        val db = AppDatabase.DatabaseBuilder.getInstance(context)
        repositoryDivida = DividaHelperImpl(db)
        loadAllDividas()
    }


    fun loadAllDividas() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                listaLiveDta.postValue(repositoryDivida.getAllDividas())
            } catch (e: RuntimeException) {

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
        viewModelScope.launch(Dispatchers.IO) {
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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repositoryDivida.removeDivida(divida)
                loadAllDividas()
            } catch (e: java.lang.RuntimeException) {
                Log.e("Delete", "Não rolou")
            }

        }
    }

    fun verificarNomeDaDivida(nameDivida: String) {
        this.erroNameDivida.value = nameDivida.trim() == "";
        this.podeCadastrar.value = nameDivida.trim() != "";
    }

    fun getErroDivida(): LiveData<Boolean?>? {
        return erroNameDivida
    }

    fun verificarValorDivida(nameDivida: String) {
        this.erroNameDividaValue.value = nameDivida.trim() == "";
        this.podeCadastrarValue.value = nameDivida.trim() != "";
    }

    fun getErroDividaValue(): LiveData<Boolean?>? {
        return erroNameDivida
    }

    fun getPodeCadastrar(): LiveData<Boolean?>? {
        return podeCadastrar
    }

    fun insertDivida(divida: Divida) {
        viewModelScope.launch(Dispatchers.IO) {
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
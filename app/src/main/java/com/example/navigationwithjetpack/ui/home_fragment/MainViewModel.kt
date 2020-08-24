package com.example.navigationwithjetpack.ui.home_fragment

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationwithjetpack.data.database.AppDatabase
import com.example.navigationwithjetpack.data.repository.divida.DividaHelperImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {


    var valorFinal: MutableLiveData<Double> = MutableLiveData()

    private lateinit var repositoryDivida: DividaHelperImpl

    fun inicaRepository(context: Context) {
        val db = AppDatabase.DatabaseBuilder.getInstance(context)
        repositoryDivida = DividaHelperImpl(
            db
        )
    }

    fun getValues() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                calculaValorDeDividas(repositoryDivida.getValuesDividas())
            } catch (e: RuntimeException) {
                Log.e("Valores", "Não rolou")
            }
        }
    }


    fun calculaValorDeDividas(list: List<Double>) {
        var soma = 0.0
        for (i in list) {
            soma += i
        }
        valorFinal.postValue(soma)
    }
}
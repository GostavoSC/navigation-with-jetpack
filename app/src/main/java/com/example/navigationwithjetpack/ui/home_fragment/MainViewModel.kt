package com.example.navigationwithjetpack.ui.home_fragment

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationwithjetpack.data.repository.divida.DividaHelperImpl
import kotlinx.coroutines.launch

class MainViewModel(val context: Context) : ViewModel() {

     var valorFinal: MutableLiveData<Double> = MutableLiveData()

    private var repositoryDivida: DividaHelperImpl =
        DividaHelperImpl(
            context
        )

    fun getValues() {
        viewModelScope.launch {
            try {
               calculaValorDeDividas(repositoryDivida.getValuesDividas())
            } catch (e: RuntimeException) {
                Log.e("Valores", "Não rolou")
            }
        }
    }

    fun calculaValorDaCarteira(valueCarteira: Double, valuesDividas: Double) : Double{
        return valueCarteira-valuesDividas
    }

    private fun calculaValorDeDividas(list: List<Double>) {
        var soma = 0.0
        for (i in list) {
            soma += i
        }
        valorFinal.postValue(soma)
    }
}
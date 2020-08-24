package com.example.navigationwithjetpack.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.navigationwithjetpack.data.repository.divida.DividaHelperImpl
import com.example.navigationwithjetpack.ui.home_fragment.MainViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class MainViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    val viewModel = MainViewModel()

    @MockK
    lateinit var repository: DividaHelperImpl
    private val ioScope = CoroutineScope(Dispatchers.IO)
    val dispatcher = Dispatchers.Unconfined

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun deveRetornar4CasoASomaDeCerto() {
        val valor1 = 2.0
        val valor2 = 2.0
        val list = ArrayList<Double>()
        list.add(valor1)
        list.add(valor2)
        viewModel.calculaValorDeDividas(list)

        viewModel.valorFinal.observeForever {
            assert(it == 4.0)
        }
    }

    @Test
    fun deveRetornar0CasoPasseListaVazia() {
        val list = ArrayList<Double>(emptyList())
        viewModel.calculaValorDeDividas(list)
        viewModel.valorFinal.observeForever {
            assert(it == 0.0)
        }
    }

    @Test
    fun deveRetornarOValor4QuandoChamadaACorrotinaESomadoOsValores() = runBlocking {
        coEvery { repository.getValuesDividas() } returns getListaDeDivida()
        viewModel.getValues()
        viewModel.valorFinal.observeForever{
            assert(it == 4.0)
        }
    }

    fun getListaDeDivida(): List<Double> {
        val divida1 = 1.0
        val divida2 = 1.0
        val divida3 = 1.0
        val divida4 = 1.0
        val list  = ArrayList<Double>()
        list.add(divida1)
        list.add(divida2)
        list.add(divida3)
        list.add(divida4)

        return list
    }

}
package com.example.navigationwithjetpack.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.navigationwithjetpack.data.database.entity.Divida
import com.example.navigationwithjetpack.data.repository.divida.DividaHelperImpl
import com.example.navigationwithjetpack.ui.list_fragment.ListViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ListViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: DividaHelperImpl

    lateinit var viewModel: ListViewModel


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = ListViewModel()
    }

    @Test
    fun deveRetornarAListaDeDividas() {
        coEvery { repository.getAllDividas() } returns getListaDeDivida()
        viewModel.loadAllDividas()
        viewModel.listaLiveDta.observeForever {
            for (i in it) {
                assert(!i.equals(""))
            }
        }

    }

    fun getListaDeDivida(): List<Divida> {
        val divida1 = Divida("a", 1.0)
        val divida2 = Divida("b", 1.0)
        val divida3 = Divida("c", 1.0)
        val divida4 = Divida("d", 1.0)
        val list = ArrayList<Divida>()
        list.add(divida1)
        list.add(divida2)
        list.add(divida3)
        list.add(divida4)

        return list
    }
}
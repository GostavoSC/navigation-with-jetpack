package com.example.navigationwithjetpack.data.repository.divida

import com.example.navigationwithjetpack.data.database.entity.Divida

interface DividaHelper {

    suspend fun getAllDividas(): List<Divida>
    suspend fun insertDivida(divida: Divida)
}
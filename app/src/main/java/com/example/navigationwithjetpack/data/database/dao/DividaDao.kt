package com.example.navigationwithjetpack.data.database.dao

import androidx.room.*
import com.example.navigationwithjetpack.data.database.entity.Divida

@Dao
interface DividaDao {
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM divida ORDER BY name_divida")
    suspend fun getAll(): List<Divida>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Delete
    suspend fun delete(divida: Divida)

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Insert
    suspend fun insert(divida: Divida)

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Update
    suspend fun updateDivida(divida: Divida)

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Delete
    suspend fun removeDivida(divida: Divida)


}
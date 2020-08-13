package com.example.navigationwithjetpack.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity
data class Divida(

    @ColumnInfo(name = "name_divida") val nameDivida: String,
    @ColumnInfo(name = "value_divida") val valueDivida: Double
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
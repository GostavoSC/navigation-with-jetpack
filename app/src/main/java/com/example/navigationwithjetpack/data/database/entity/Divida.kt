package com.example.navigationwithjetpack.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity
data class Divida(

    @ColumnInfo(name = "name_divida") var nameDivida: String,
    @ColumnInfo(name = "value_divida") var valueDivida: Double
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
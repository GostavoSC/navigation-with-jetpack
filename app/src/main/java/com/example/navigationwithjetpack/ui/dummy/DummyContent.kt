package com.example.navigationwithjetpack.ui.dummy

import com.example.navigationwithjetpack.data.database.entity.Divida
import java.util.*

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DummyContent {

    var ITEMS: MutableList<Divida> = ArrayList()

    fun addAllItem(list: List<Divida>) {
        ITEMS.clear()
        ITEMS.addAll(list)
    }


}
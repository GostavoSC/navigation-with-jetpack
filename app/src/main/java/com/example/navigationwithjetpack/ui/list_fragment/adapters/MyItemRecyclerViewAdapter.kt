package com.example.navigationwithjetpack.ui.list_fragment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationwithjetpack.R
import com.example.navigationwithjetpack.data.database.entity.Divida


class MyItemRecyclerViewAdapter(
    private var values: List<Divida>,
    val btnlistener: BtnClickListener,
    val btnOnLonglistener: BtnOnLongClickListener
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    companion object {
        lateinit var onClick: BtnClickListener
        lateinit var onLongClick: BtnOnLongClickListener
    }

    fun setValuesFromList(valuesFromList: List<Divida>){
        values = valuesFromList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        onClick = btnlistener
        onLongClick = btnOnLonglistener
        holder.idView.text = position.toString()
        holder.idView.setOnClickListener {
            onClick.onBtnClick(position)
        }
        holder.contentView.text = item.nameDivida
        holder.contentView.setOnClickListener {
            onClick.onBtnClick(position)
        }
        holder.contentView.setOnLongClickListener {
            onLongClick.onBtnOnLongClick(position).toString().toBoolean()
        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    interface BtnClickListener {
        fun onBtnClick(position: Int)
    }
    interface BtnOnLongClickListener {
        fun onBtnOnLongClick(position: Int)
    }

}
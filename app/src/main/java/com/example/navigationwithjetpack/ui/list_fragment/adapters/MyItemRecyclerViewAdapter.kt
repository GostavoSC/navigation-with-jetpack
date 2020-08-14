package com.example.navigationwithjetpack.ui.list_fragment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationwithjetpack.R
import com.example.navigationwithjetpack.data.database.entity.Divida
import kotlinx.android.synthetic.main.fragment_list.view.*
import org.w3c.dom.Text


class MyItemRecyclerViewAdapter(
    private var values: List<Divida>,
    val btnlistener: BtnClickListener,
    val btnlistenerDelete: BtnClickListenerDelete
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    companion object {
        lateinit var onClick: BtnClickListener
        lateinit var onDeleteClick: BtnClickListenerDelete
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
        onDeleteClick = btnlistenerDelete
        holder.valueDivida.text = item.valueDivida.toString()
        holder.contentView.text = item.nameDivida
        holder.imageButton.setOnClickListener {
            onDeleteClick.onBtnClickDelete(values.get(position))
        }
        holder.contentView.setOnClickListener {
            onClick.onBtnClick(values.get(position))
        }


    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contentView: TextView = view.findViewById(R.id.content)
        val imageButton: ImageButton  = view.findViewById(R.id.imageButton2)
        val valueDivida: TextView = view.findViewById(R.id.textView)
        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    interface BtnClickListener {
        fun onBtnClick(divida: Divida)
    }
    interface BtnClickListenerDelete {
        fun onBtnClickDelete(divida: Divida)
    }

}
package com.example.kotlinrest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinrest.model.ListData

/**
 * @project 
 * @class JsonAdapter.kt
 * @author seungho
 * @github devaspirant0510
 * @email seungho020510@gmail.com
 * @since 2021-06-10
 * @description
 **/
class JsonAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val myList = arrayListOf<ListData>()
    private var callback:jsonAdapterCallback? = null

    interface jsonAdapterCallback{
        fun onClick(pos:Int)
    }
    fun setJsonAdapterCallback(callback: jsonAdapterCallback){
        this.callback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_rc_item,
        parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyViewHolder).setItem(myList[position])
        holder.setIsRecyclable(false)
        holder.itemView.setOnClickListener {
            callback?.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return myList.size

    }
    fun addItem(data: ListData){
        myList.add(data)
    }
    inner class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        private val jsonId = itemView.findViewById<TextView>(R.id.tv_list_id)
        private val jsonTitle = itemView.findViewById<TextView>(R.id.tv_list_title)


        fun setItem(data: ListData){
            jsonId.text = data.id
            jsonTitle.text = data.title
        }

    }
}
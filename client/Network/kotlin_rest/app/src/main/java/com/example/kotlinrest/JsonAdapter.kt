package com.example.kotlinrest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinrest.JsonAdapter.MyViewHolder as MyViewHolder1

class JsonAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val myList = arrayListOf<JsonListData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_rc_item,
        parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyViewHolder).setItem(myList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return myList.size

    }
    fun addItem(data:JsonListData){
        myList.add(data)
    }
    inner class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        private val jsonId = itemView.findViewById<TextView>(R.id.tv_list_id)
        private val jsonTitle = itemView.findViewById<TextView>(R.id.tv_list_title)

        fun setItem(data: JsonListData){
            jsonId.text = data.id
            jsonTitle.text = data.title
        }

    }
}
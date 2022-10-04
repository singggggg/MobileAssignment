package com.example.mobileassignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecAdapter (private val recommendationList : ArrayList<RecommendationAdmin>) : RecyclerView.Adapter<RecAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recommendation_items,
            parent,false)
        return MyViewHolder(itemView, mListener)

    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = recommendationList[position]

        holder.nameTitle.text = currentitem.recName
        holder.desc.text = currentitem.recDesc
        holder.uploadT.text = currentitem.uploadTime
        holder.like.text = currentitem.like.toString()
        holder.dislike.text = currentitem.dislike.toString()

    }

    override fun getItemCount(): Int {

        return recommendationList.size
    }


    class MyViewHolder(itemView : View, clickListener: RecAdapter.onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val nameTitle : TextView = itemView.findViewById(R.id.etNameTitle)
        val desc : TextView = itemView.findViewById(R.id.etDesc)
        val uploadT : TextView = itemView.findViewById(R.id.tvTime)
        val like : TextView = itemView.findViewById(R.id.tvLike)
        val dislike : TextView = itemView.findViewById(R.id.tvDislike)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}
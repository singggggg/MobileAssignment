package com.example.mobileassignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MealAdapter(private val mealMenuList : ArrayList<MealMenu>):RecyclerView.Adapter<MealAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.meal_items,
            parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = mealMenuList[position]

        holder.name.text = currentitem.name
        holder.desc.text = currentitem.description
        holder.price.text = currentitem.price
        holder.category.text = currentitem.category

    }

    override fun getItemCount(): Int {

        return mealMenuList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val name : TextView = itemView.findViewById(R.id.tvName)
        val desc : TextView = itemView.findViewById(R.id.tvDesc)
        val price : TextView = itemView.findViewById(R.id.tvPrice)
        val category : TextView = itemView.findViewById(R.id.tvCategory)

    }
}
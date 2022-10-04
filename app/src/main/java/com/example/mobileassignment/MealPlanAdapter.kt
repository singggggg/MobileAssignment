package com.example.mobileassignment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileassignment.Fragment.MealPlanFragment
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.*
import java.util.*
import kotlin.collections.ArrayList

class MealPlanAdapter (private val mealPlan : ArrayList<MealPlan>) : RecyclerView.Adapter<MealPlanAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.meal_plan_items,
            parent,false)
        return MyViewHolder(itemView)

    }

    val mpf = MealPlanFragment()

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = mealPlan[position]

        holder.mealName.text = currentitem.mealName
        holder.mealIsConsumed.isChecked = currentitem.mealIsConsumed == "1"

        holder.mealIsConsumed.setOnClickListener(){
            val db = Firebase.database
            val myRef = db.getReference("MealPlan")
            if(holder.mealIsConsumed.isChecked){
                val mp = MealPlan(currentitem.mealName,currentitem.mealCalories,"1",currentitem.mealPlanStartDate)
                myRef.child(currentitem.mealName.toString()).setValue(mp).addOnCompleteListener{}
            }
            else{
                val mp = MealPlan(currentitem.mealName,currentitem.mealCalories,"0",currentitem.mealPlanStartDate)
                myRef.child(currentitem.mealName.toString()).setValue(mp).addOnCompleteListener{}
            }
        }
    }

    override fun getItemCount(): Int {
        return mealPlan.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val mealName : TextView = itemView.findViewById(R.id.tv_MealPlan_MealName)
        val mealIsConsumed : CheckBox = itemView.findViewById(R.id.tv_mealPlan_comsumed)
    }

}
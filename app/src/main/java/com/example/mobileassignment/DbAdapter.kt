package com.example.mobileassignment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileassignment.Fragment.HomePageFragment
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DbAdapter (private val recommendation : ArrayList<Recommendation>) : RecyclerView.Adapter<DbAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recommendation_items,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = recommendation[position]

        holder.recTitle.text = currentitem.recName
        holder.recLikeAmount.text = currentitem.like.toString()
        holder.recDislikeAmount.text = currentitem.dislike.toString()
        /*holder.recImage.imag = currentitem.recDislike*/ //TODO: unsolvable image part

        val sum = MainActivity()

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context,RecommendationDetails::class.java)
            intent.putExtra("name",currentitem.recName)
            intent.putExtra("desc",currentitem.recDesc)
            intent.putExtra("uploadTime",currentitem.uploadTime)
            startActivity(holder.itemView.context,intent,null)
        }
        val db = Firebase.database
        val myRef = db.getReference("Recommendation")

        holder.recLike.setOnClickListener{
            if(holder.recLike.isChecked){
                holder.recLike.setButtonDrawable(R.drawable.ic_like_checked)
                holder.recDislike.setButtonDrawable(R.drawable.ic_dislike_unchecked)
                holder.recDislike.isChecked = false

                val newLike = currentitem.like?.toInt()?.plus(1)
                val mp = Recommendation(currentitem.recName,currentitem.recDesc, newLike, currentitem.dislike,currentitem.uploadTime)
                myRef.child(currentitem.recName.toString()).setValue(mp).addOnCompleteListener{}
            }
            if(!holder.recLike.isChecked){
                holder.recLike.setButtonDrawable(R.drawable.ic_like_unchecked)
                val newLike = currentitem.like?.toInt()?.minus(1)
                val mp = Recommendation(currentitem.recName,currentitem.recDesc, newLike, currentitem.dislike,currentitem.uploadTime)
                myRef.child(currentitem.recName.toString()).setValue(mp).addOnCompleteListener{}
            }
        }

        holder.recDislike.setOnClickListener{
            // TODO: increase dislike amount in firebase
            // TODO: refresh dislike amount
            if(holder.recDislike.isChecked){
                holder.recDislike.setButtonDrawable(R.drawable.ic_dislike_checked)
                holder.recLike.setButtonDrawable(R.drawable.ic_like_unchecked)
                holder.recLike.isChecked = false

                val newDislike = currentitem.dislike?.toInt()?.plus(1)
                val mp = Recommendation(currentitem.recName,currentitem.recDesc, currentitem.like, newDislike,currentitem.uploadTime)
                myRef.child(currentitem.recName.toString()).setValue(mp).addOnCompleteListener{}
            }
            if(!holder.recDislike.isChecked){
                holder.recDislike.setButtonDrawable(R.drawable.ic_dislike_unchecked)
                val newDislike = currentitem.dislike?.toInt()?.minus(1)
                val mp = Recommendation(currentitem.recName,currentitem.recDesc, currentitem.like, newDislike,currentitem.uploadTime)
                myRef.child(currentitem.recName.toString()).setValue(mp).addOnCompleteListener{}
            }
        }
    }

    override fun getItemCount(): Int {
        return recommendation.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val recTitle : TextView = itemView.findViewById(R.id.tv_recTitle)
        val recLikeAmount : TextView = itemView.findViewById(R.id.tv_recLikeAmount)
        val recDislikeAmount : TextView = itemView.findViewById(R.id.tv_recDislikeAmount)
/*        val recImage : ImageView = itemView.findViewById(R.id.iv_recPicture)*/
        val recLike : CheckBox = itemView.findViewById(R.id.btn_like)
        val recDislike : CheckBox = itemView.findViewById(R.id.btn_dislike)
    }

}
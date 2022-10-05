package com.example.mobileassignment.Fragment

import android.content.ContentValues
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileassignment.MealPlan
import com.example.mobileassignment.MealPlanAdapter
import com.example.mobileassignment.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_meal_plan.*
import java.io.*
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MealPlanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealPlanFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_plan, container, false)
    }

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<MealPlan>

    var weeklyCal = ""
    var monthlyCal = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userRecyclerview = requireView().findViewById<View>(R.id.RecyclerView_mealPlan) as RecyclerView
        userRecyclerview.layoutManager = LinearLayoutManager(this.context)
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<MealPlan>()
        getUserData()

        updateCalories()
    }

    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("MealPlan")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userArrayList.clear()
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(MealPlan::class.java)
                        Log.d(ContentValues.TAG, "Value is: $user")
                        userArrayList.add(user!!)
                    }
                    userRecyclerview.adapter = MealPlanAdapter(userArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    private fun updateCalories(){

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dayOfWeek = day%7
        val week = (day/7)+1

        val currentDate:String = "W"+week.toString()+"_"+month.toString()+"_"+year.toString()

        /*val times = retreiveLocal("timestamp")*/
        monthlyCal = if (!retreiveLocal("$month$year Monthly").isNullOrEmpty()) retreiveLocal("$month$year Monthly") else "0.0"
        weeklyCal = if (!retreiveLocal("$currentDate Weekly").isNullOrEmpty()) retreiveLocal("$currentDate Weekly") else "0.0"

        var initialMonthly = monthlyCal

        val query =
            FirebaseDatabase.getInstance().getReference("MealPlan").orderByChild("mealIsConsumed")
                .equalTo("1")


        query.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var wcalc:Double = 0.0
                var mcalc:Double = 0.0
                mcalc = initialMonthly.toDouble()
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(MealPlan::class.java)
                    wcalc += user?.mealCalories?.toDouble()!!
                    mcalc += user?.mealCalories?.toDouble()!!
                }

                weeklyCal = wcalc.toString()
                monthlyCal = mcalc.toString()

                tv_weeklyCalories.text = weeklyCal+" KCAL"
                tv_monthlyCalories.text = monthlyCal+" KCAL"

                if (weeklyCal != "0.0"){
                    storeLocal(weeklyCal, "$currentDate Weekly")
                }
                if (monthlyCal != "0.0" && dayOfWeek == 7) {
                    storeLocal(monthlyCal, "$month$year Monthly")
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun storeLocal(data: String, type: String){
        val file: String = type + "Record"
        val fileOutputStream: FileOutputStream

        if (file.trim()!=""){
            try{
                fileOutputStream = requireContext().openFileOutput(file,Context.MODE_PRIVATE)
                fileOutputStream.write(data.toByteArray())
            }
            catch (e:FileNotFoundException){
                e.printStackTrace()
            }
            catch (e:NumberFormatException){
                e.printStackTrace()
            }
            catch (e:IOException){
                with(e){printStackTrace()}
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun retreiveLocal(data: String):String{
        val filename = data + "Record"
        val file = File(context?.applicationInfo!!.dataDir, "files/"+filename)

        if(!file.exists()){
            file.createNewFile()
        }

        if (filename.trim()!=""){
            val fileInputStream: FileInputStream? = getActivity()?.openFileInput(filename)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferReader = BufferedReader(inputStreamReader)
            val stringBuilder: StringBuilder = StringBuilder()
            var text: String?
            while(run{
                    text = bufferReader.readLine()
                    text
                }!= null){
                stringBuilder.append(text)
            }
            return stringBuilder.toString()
        }
        else{
            return ""
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MealPlanFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MealPlanFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
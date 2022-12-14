package com.example.mobileassignment.AdminFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.mobileassignment.AdminAddMeal
import com.example.mobileassignment.AdminDeleteMeal
import com.example.mobileassignment.AdminUpdateMeal
import com.example.mobileassignment.AdminViewMeal
import com.example.mobileassignment.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminMealMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminMealMenuFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_admin_meal_menu, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdminMealMenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminMealMenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val MealAddBtn=view.findViewById<Button>(R.id.Meal_AddBtn)
        val MealUpdateBtn=view.findViewById<Button>(R.id.Meal_UpdateBtn)
        val MealViewBtn=view.findViewById<Button>(R.id.Meal_ViewBtn)
        val MealDeleteBtn=view.findViewById<Button>(R.id.Meal_DeleteBtn)

        MealAddBtn.setOnClickListener {
            val intent = Intent(this.context, AdminAddMeal::class.java)
            startActivity(intent)
        }

        MealUpdateBtn.setOnClickListener{
            val intent = Intent(this.context, AdminUpdateMeal::class.java)
            startActivity(intent)
        }

        MealViewBtn.setOnClickListener{
            val intent = Intent(this.context, AdminViewMeal::class.java)
            startActivity(intent)
        }

        MealDeleteBtn.setOnClickListener{
            val intent = Intent(this.context, AdminDeleteMeal::class.java)
            startActivity(intent)
        }



    }
}
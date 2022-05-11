package com.podium.papermenu.ui.foodmenu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.podium.papermenu.FoodAdapter
import com.podium.papermenu.MyUtil
import com.podium.papermenu.R
import com.podium.papermenu.databinding.FragmentFoodBinding
import com.podium.papermenu.model.Food

class FoodFragment : Fragment() {
    private var _binding: FragmentFoodBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var foods: ArrayList<Food>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        _binding = FragmentFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val category = FoodFragmentArgs.fromBundle(requireArguments()).category
        foods = arrayListOf()
        val adapter = FoodAdapter(foods)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        val ref = db.collection("foods").whereEqualTo("category", category).get()
        ref.addOnSuccessListener { docs ->
            for (document in docs.documents) {
                document.toObject(Food::class.java)?.let { foods.add(it) }
            }

            (binding.recyclerView.adapter as FoodAdapter).notifyDataSetChanged()
        }.addOnFailureListener {
            Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_INDEFINITE)
                .show()
        }

        binding.btnOrder.setOnClickListener {
            val prefs = MyUtil(requireContext())
            val allOrders = prefs.getAll()
            when {
                allOrders?.isNotEmpty() == true -> {
                    findNavController().navigate(R.id.action_foodFragment_to_orderConfirmDialog)
                }
                else -> {
                    Snackbar.make(
                        binding.root,
                        "Please order something from menu",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }
}
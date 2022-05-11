package com.podium.papermenu.ui.foodmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.podium.papermenu.CategoryAdapter
import com.podium.papermenu.MyUtil
import com.podium.papermenu.R
import com.podium.papermenu.databinding.FragmentCategoryBinding
import com.podium.papermenu.model.Category

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var categories: ArrayList<Category>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val util = MyUtil(requireContext())

        categories = arrayListOf()
        val adapter = CategoryAdapter(categories)
        binding.recyclerView2.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView2.adapter = adapter
        load_categories()
        binding.btnCOrder.setOnClickListener {
            val prefs = MyUtil(requireContext())
            val allOrders = prefs.getAll()
            when {
                allOrders?.isNotEmpty() == true -> {
                    findNavController().navigate(R.id.action_navigation_food_menu_to_orderConfirmDialog)
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

    private fun load_categories() {
        try {
            db.collection("categories").get().addOnSuccessListener { snapshot ->
                categories.clear()
                if (!snapshot.isEmpty) {
                    for (document in snapshot.documents) {
                        document.toObject(Category::class.java)?.let { categories.add(it) }
                    }
                    (binding.recyclerView2.adapter as CategoryAdapter).notifyDataSetChanged()
                }
            }.addOnFailureListener {
                Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_INDEFINITE)
                    .show()
            }
        } catch (e: Exception) {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
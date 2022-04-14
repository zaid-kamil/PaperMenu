package com.podium.papermenu.ui.foodmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
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
        if (util.getAll()?.isNotEmpty() == true) {
            findNavController().navigate(R.id.action_foodFragment_to_orderConfirmDialog)
        }
        categories = arrayListOf()
        db.collection("categories").get()
            .addOnSuccessListener { snapshot ->
                snapshot.forEach {
                    categories.add(Category(it["name"].toString()))
                }
            }.addOnFailureListener {
                Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_INDEFINITE)
                    .show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.podium.papermenu.ui.pay

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.podium.papermenu.MyUtil
import com.podium.papermenu.R
import com.podium.papermenu.databinding.FragmentTransactionBinding

class TransactionFragment : Fragment() {
    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var prefs: MyUtil
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        prefs = MyUtil(requireContext())
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        removeOrders()
        prefs.removeAll()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun removeOrders() {

        db.collection("orders").whereEqualTo("table", prefs.getTable()).get()
            .addOnSuccessListener { query ->
                if (query.size() > 0) {
                    for (doc in query.documents) {
                        db.collection("orders").document(doc.id).delete()
                    }
                    Handler().postDelayed(
                        {
                            findNavController().navigate(R.id.action_transactionFragment_to_navigation_food_menu)
                        }, 3000
                    )
                } else {

                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
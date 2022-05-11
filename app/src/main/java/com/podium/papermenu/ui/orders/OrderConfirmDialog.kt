package com.podium.papermenu.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.podium.papermenu.MyUtil
import com.podium.papermenu.R
import com.podium.papermenu.databinding.DialogOrderConfirmationBinding
import com.podium.papermenu.model.Order

class OrderConfirmDialog : BottomSheetDialogFragment() {
    private var _binding: DialogOrderConfirmationBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var prefs: MyUtil


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogOrderConfirmationBinding.inflate(
            inflater,
            container,
            false
        )
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        prefs = MyUtil(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val allOrders = prefs.getAll()
        if (allOrders != null) {
            for (order in allOrders.entries) {
                val orderData = order.value.toString().split("::")

                binding.textOrders.append("\n${order.key} \nPrice:  ${orderData[1]}\n Quantity: ${orderData[2]}\n\n")
            }
        }
        binding.btnConfirm.setOnClickListener {
            for (order in allOrders?.entries!!) {
                val orderData = order.value.toString().split("::")
                val name = orderData[0]
                val price = orderData[1]
                val qty = orderData[2]
                val cat = orderData[3]
                val img = orderData[4]
                val table = prefs.getTable()
                val order = Order(table, name, price.toInt(), qty, cat, img)
                db.collection("orders").add(order)
            }
            findNavController().navigate(R.id.action_orderConfirmDialog_to_navigation_orders)
        }
        binding.button.setOnClickListener {
            prefs.removeAll()
            this.dialog?.hide()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
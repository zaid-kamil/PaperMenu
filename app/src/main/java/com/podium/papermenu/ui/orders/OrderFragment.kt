package com.podium.papermenu.ui.orders

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
import com.podium.papermenu.MyUtil
import com.podium.papermenu.OrderAdapter
import com.podium.papermenu.databinding.FragmentOrderBinding
import com.podium.papermenu.model.Order

class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var prefs: MyUtil
    private lateinit var orders: ArrayList<Order>
    private var totalPay: Float = 0.0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        prefs = MyUtil(requireContext())
        orders = arrayListOf()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView3.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView3.adapter = OrderAdapter(orders)

        load_orders()
        binding.btnFinish.setOnClickListener {
            findNavController().navigate(
                OrderFragmentDirections.actionNavigationOrdersToNavigationPay(
                    totalPay
                )
            )
        }
        binding.swiper.setOnRefreshListener {
            load_orders()
            binding.swiper.isRefreshing = false
        }
    }

    fun load_orders() {
        db.collection("orders")
            .whereEqualTo("table", prefs.getTable())
            .get()
            .addOnFailureListener {
                Snackbar.make(
                    binding.root,
                    "No Food Orders, Please order from the menu",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            .addOnSuccessListener { query ->
                if (query.size() > 0) {
                    totalPay = 0.0f
                    orders.clear()
                    for (doc in query.documents) {
                        val order = doc.toObject(Order::class.java)
                        orders.add(order!!)
                        totalPay += order.price.toFloat() * order.qty
                    }
                    binding.recyclerView3.adapter!!.notifyDataSetChanged()
                    binding.btnFinish.text = "Finish to Pay Rs. $totalPay"
                    binding.btnFinish.tag = totalPay
                    prefs.removeAll()
                } else {
                    Snackbar.make(
                        binding.root,
                        "No Food Orders, Please order from the menu",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
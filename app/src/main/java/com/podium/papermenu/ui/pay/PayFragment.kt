package com.podium.papermenu.ui.pay

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.podium.papermenu.MyUtil
import com.podium.papermenu.R
import com.podium.papermenu.databinding.FragmentPayBinding


class PayFragment : Fragment() {

    private var _binding: FragmentPayBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var prefs: MyUtil
    var GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user"
    private var totalPay: Float = 0.0f
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentPayBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        prefs = MyUtil(requireContext())
        return binding.root
    }

    private val result =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                findNavController().navigate(R.id.action_navigation_pay_to_transactionFragment)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args = PayFragmentArgs.fromBundle(requireArguments())
        binding.amountPayable.text = args.amount.toString()
        if (args.amount == 0.0f) {
            findNavController().navigate(R.id.action_navigation_pay_to_navigation_orders)
        }
        binding.btnUpi.setOnClickListener {
            val uri: Uri = Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", "xaidmetamorphos@oksbi")
                .appendQueryParameter("pn", "Paper Menu")
                .appendQueryParameter("mc", "your-merchant-code")
                .appendQueryParameter("tr", "your-transaction-ref-id")
                .appendQueryParameter("tn", "the minimum service pay")
                .appendQueryParameter("am", args.amount.toString())
                .appendQueryParameter("cu", "INR")
                .build()
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = uri
            intent.setPackage(GOOGLE_PAY_PACKAGE_NAME)

            result.launch(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
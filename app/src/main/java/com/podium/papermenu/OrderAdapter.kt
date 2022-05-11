package com.podium.papermenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.podium.papermenu.model.Order

class OrderAdapter(private val orderList: ArrayList<Order>) :
    RecyclerView.Adapter<OrderAdapter.OrderHolder>() {

    class OrderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.name)
        private val img: ImageView = itemView.findViewById(R.id.img)
        private val price: TextView = itemView.findViewById(R.id.priceval)
        private val amount: TextView = itemView.findViewById(R.id.amount)
        private val category: TextView = itemView.findViewById(R.id.cat)
        private val qty: TextView = itemView.findViewById(R.id.qty)
        private val isPrep: TextView = itemView.findViewById(R.id.isprep)
        fun bind(order: Order) {
            name.text = order.name
            price.text = order.price
            category.text = order.category
            qty.text = order.qty.toString()
            if (!order.isDelivered) isPrep.text = "Prep"
            else if (order.isDelivered) isPrep.text = "Given"
            Glide.with(img).load(order.img).into(img)
            val total = order.price.toFloat() * order.qty
            amount.text = total.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapter.OrderHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_card, parent, false)
        return OrderAdapter.OrderHolder(view)
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        val order = orderList[position]
        holder.bind(order)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

}
package com.podium.papermenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.podium.papermenu.model.Category
import com.podium.papermenu.ui.foodmenu.CategoryFragmentDirections

class CategoryAdapter(private val categories: ArrayList<Category>) :
    RecyclerView.Adapter<CategoryAdapter.Holder>() {
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tc: TextView = itemView.findViewById(R.id.textCategory)
        private val card: ViewGroup = itemView.findViewById(R.id.card)
        fun bind(category: Category) {
            tc.text = category.name
            card.tag =category
            card.setOnClickListener {
                val dir= CategoryFragmentDirections.actionNavigationFoodMenuToFoodFragment((it.tag as Category).name)
                card.findNavController().navigate(dir)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_card,parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

}
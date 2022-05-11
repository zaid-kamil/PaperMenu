package com.podium.papermenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mcdev.quantitizerlibrary.QuantitizerListener
import com.mcdev.quantitizerlibrary.VerticalQuantitizer
import com.podium.papermenu.model.Food

class FoodAdapter(private val foodlist: ArrayList<Food>) :
    RecyclerView.Adapter<FoodAdapter.FoodHolder>() {

    class FoodHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.name)
        private val img: ImageView = itemView.findViewById(R.id.img)
        private val price: TextView = itemView.findViewById(R.id.price)
        private val hq: VerticalQuantitizer = itemView.findViewById(R.id.h_q)
        private val category: TextView = itemView.findViewById(R.id.cat)
        init {
            hq.minValue = 0
            hq.maxValue = 10
        }
        fun bind(food: Food) {
            name.text = food.name
            Glide.with(img).load(food.image).into(img)
            price.text = food.price
            hq.tag = food
            category.text = food.category
            val prefs = MyUtil(name.context)
            hq.setQuantitizerListener(object : QuantitizerListener {
                override fun onDecrease() {}

                override fun onIncrease() {}

                override fun onValueChanged(value: Int) {
                    if(value>0) {
                        prefs.updateOrder(food, value)
                    }
                    else{
                        prefs.removeOrder(food.name)
                    }
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_card, parent, false)
        return FoodHolder(view)
    }

    override fun onBindViewHolder(holder: FoodHolder, position: Int) {
        val food = foodlist[position]
        holder.bind(food)
    }

    override fun getItemCount(): Int {
        return foodlist.size
    }

}
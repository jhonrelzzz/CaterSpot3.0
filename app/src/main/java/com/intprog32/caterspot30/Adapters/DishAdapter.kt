package com.intprog32.caterspot30.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.intprog32.caterspot30.Data.DishData
import com.intprog32.caterspot30.R

class DishAdapter(
    private val dishList: List<DishData>,
    private val onItemClick: (DishData) -> Unit
) : RecyclerView.Adapter<DishAdapter.DishViewHolder>() {

    class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dishImage: ImageView = itemView.findViewById(R.id.dish_image)
        val dishName: TextView = itemView.findViewById(R.id.dish_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dish_item_card, parent, false)
        return DishViewHolder(view)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val dish = dishList[position]
        holder.dishImage.setImageResource(dish.imageResId)
        holder.dishName.text = dish.name

        holder.itemView.setOnClickListener {
            onItemClick(dish)
        }
    }

    override fun getItemCount() = dishList.size
}


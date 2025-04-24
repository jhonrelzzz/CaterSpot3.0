package com.intprog32.caterspot30.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.intprog32.caterspot30.Data.CatererData
import com.intprog32.caterspot30.R

class CatererAdapter(private val catererList: List<CatererData>) :
    RecyclerView.Adapter<CatererAdapter.CatererViewHolder>() {

    class CatererViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.caterer_name)
        val desc = itemView.findViewById<TextView>(R.id.caterer_desc)
        val image = itemView.findViewById<ImageView>(R.id.caterer_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatererViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_caterer_card, parent, false)
        return CatererViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatererViewHolder, position: Int) {
        val caterer = catererList[position]
        holder.name.text = caterer.name
        holder.desc.text = caterer.description
        holder.image.setImageResource(caterer.imageResId)
    }

    override fun getItemCount() = catererList.size
}

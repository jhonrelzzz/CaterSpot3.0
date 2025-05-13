package com.intprog32.caterspot30.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.intprog32.caterspot30.Data.CatererData
import com.intprog32.caterspot30.R
import com.bumptech.glide.Glide

class CatererAdapter(
    private var catererList: MutableList<CatererData>,
    private val onItemClick: (CatererData) -> Unit,
    private val onItemLongClick: (CatererData) -> Unit
) : RecyclerView.Adapter<CatererAdapter.CatererViewHolder>() {

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

        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(caterer.imageUrl)
            .placeholder(R.drawable.sampleseafood)
            .error(R.drawable.error_image)
            .into(holder.image)

        // Item click
        holder.itemView.setOnClickListener {
            onItemClick(caterer)
        }

        holder.itemView.setOnLongClickListener {
            onItemLongClick(caterer)
            true
        }
    }

    override fun getItemCount() = catererList.size

    // Optional: method to update data after deletion
    fun removeItem(caterer: CatererData) {
        val index = catererList.indexOf(caterer)
        if (index != -1) {
            catererList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun filterList(filteredList: List<CatererData>) {
        catererList = filteredList.toMutableList()
        notifyDataSetChanged()
    }

}

package com.azeupaul.globotour.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.azeupaul.globotour.R
import com.azeupaul.globotour.city.City
import java.util.ArrayList

class FavoriteAdapter(private val context: Context, private val favoritesList: ArrayList<City>): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_item_favorite, parent, false)
        return FavoriteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = favoritesList[position]
        holder.display(favorite)
    }

    override fun getItemCount(): Int = favoritesList.size

    inner class FavoriteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var favorite: City? = null

        private val txtCityName = itemView.findViewById<TextView>(R.id.txv_city_name)
        private val imvCityImage = itemView.findViewById<ImageView>(R.id.imv_city)

        fun display(favorite: City) {
            txtCityName.text = favorite.name
            imvCityImage.setImageResource(favorite.imageId)
        }
    }

}
package com.azeupaul.globotour.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azeupaul.globotour.R
import com.azeupaul.globotour.city.City
import com.azeupaul.globotour.city.CityAdapter
import com.azeupaul.globotour.city.VacationSpots
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.collections.ArrayList


class FavoriteFragment : Fragment() {

    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteList: ArrayList<City>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        setupRecyclerView(view)

        return view
    }

    private fun setupRecyclerView(view: View?) {
        val context = requireContext()
        favoriteList = VacationSpots.favoriteCityList as ArrayList<City>
        favoriteAdapter = FavoriteAdapter(context, favoriteList)

        recyclerView = view?.findViewById(R.id.favorite_recycler_view)!!
        recyclerView.adapter = favoriteAdapter

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = layoutManager

        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private val itemTouchHelper = ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
        ): Boolean {
            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition

            Collections.swap(favoriteList, fromPosition, toPosition)
            recyclerView.adapter?.notifyItemMoved(fromPosition, toPosition)

            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val deletedCity = favoriteList[position]
            
            deleteItem(position)
            updateCityList(deletedCity, false)

            Snackbar.make(recyclerView, "Favorite deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo"){
                    undoDelete(position, deletedCity)
                    updateCityList(deletedCity, true)
                }
                .show()

        }

    })

    private fun deleteItem(position: Int) {
        favoriteList.removeAt(position)
        favoriteAdapter.notifyItemRemoved(position)
        favoriteAdapter.notifyItemRangeChanged(position, favoriteList.size)
    }

    private fun updateCityList(deletedCity: City, isFavorite: Boolean) {
        val cityList = VacationSpots.cityList
        val position = cityList?.indexOf(deletedCity)
        cityList!![position!!].isFavorite = isFavorite
    }

    private fun undoDelete(position: Int, deletedCity: City) {
        favoriteList.add(position, deletedCity)
        favoriteAdapter.notifyItemInserted(position)
        favoriteAdapter.notifyItemRangeChanged(position, favoriteList.size)
    }
}

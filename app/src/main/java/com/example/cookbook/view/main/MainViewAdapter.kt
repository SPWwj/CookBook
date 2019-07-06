package com.example.cookbook.view.main

import android.content.Context
import android.content.Intent
import com.squareup.picasso.Picasso
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.cookbook.data.model.DishesModel
import com.example.cookbook.R
import com.example.cookbook.utils.Constants
import com.example.cookbook.view.detail.DetailActivity
import com.jakewharton.picasso.OkHttp3Downloader


class MainViewAdapter : PagedListAdapter<DishesModel.Dishes, MainViewAdapter.MainViewHolder>(diffCallback) {
      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
          MainViewHolder(parent)

      override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }


    companion object {
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         * <p>
         * When you add a Cheese with the 'Add' button, the PagedListAdapter uses diffCallback to
         * detect there's only a single item difference from before, so it only needs to animate and
         * rebind a single view.
         *
         * @see android.support.v7.util.DiffUtil
         */
        private val diffCallback = object : DiffUtil.ItemCallback<DishesModel.Dishes>() {
            override fun areItemsTheSame(oldItem: DishesModel.Dishes, newItem: DishesModel.Dishes): Boolean =
                oldItem.DishesID == newItem.DishesID

            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            override fun areContentsTheSame(oldItem: DishesModel.Dishes, newItem: DishesModel.Dishes): Boolean =
                oldItem == newItem
        }
    }
      inner class MainViewHolder(parent :ViewGroup) : RecyclerView.ViewHolder(
         LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)) {


         var dishesName: TextView = itemView.findViewById(R.id.dishes_name)
         val dishesImage: ImageView = itemView.findViewById(R.id.dishes_image)
         var dishes : DishesModel.Dishes? = null


         fun bindTo(dishes : DishesModel.Dishes?) {
             this.dishes = dishes
             dishesName.text = dishes?.Name

             if(dishes?.Image != null) {
                 val builder = Picasso.Builder(itemView.context)
                 builder.downloader(OkHttp3Downloader(itemView.context))
                 builder.build().load(dishes.Image)
                     .placeholder(R.drawable.ic_launcher_background)
                     .error(R.drawable.ic_launcher_background)
                     .into(dishesImage)
             }
             var mLastClickTime = System.currentTimeMillis()
             itemView.setOnClickListener { view ->
                 //
                 val now = System.currentTimeMillis()
                 if (now - mLastClickTime < Constants.CLICK_TIME_INTERVAL) {
                     return@setOnClickListener
                 }
                 mLastClickTime = now
                 // passing data to the book activity
                 val intent = Intent(itemView.context, DetailActivity::class.java)
                 intent.putExtra(Constants.DISHES_DETAIL, dishes!!.DishesID)
                 view.context.startActivity(intent)
             }
         }
     }

 }
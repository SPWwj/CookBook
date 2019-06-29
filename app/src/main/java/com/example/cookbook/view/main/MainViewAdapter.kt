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
import com.example.cookbook.data.model.DishesModel
import com.example.cookbook.R
import com.example.cookbook.utils.Constants
import com.example.cookbook.view.detail.DetailActivity
import com.jakewharton.picasso.OkHttp3Downloader


internal class MainViewAdapter(private val context: Context, private val dataList: List<DishesModel.Recipe>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<MainViewAdapter.MainViewHolder>() {

    internal inner class MainViewHolder(val mView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mView) {

        var txtTitle: TextView = mView.findViewById(R.id.title)
        val coverImage: ImageView = mView.findViewById(R.id.coverImage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.custom_row, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.txtTitle.text = dataList[position].dishes.name

        val builder = Picasso.Builder(context)
        builder.downloader(OkHttp3Downloader(context))
        builder.build().load(dataList[position].dishes.image)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.coverImage)
        var mLastClickTime = System.currentTimeMillis()

        holder.itemView.setOnClickListener { view ->

            val now = System.currentTimeMillis()
            if (now - mLastClickTime < Constants.CLICK_TIME_INTERVAL) {
                return@setOnClickListener
            }
            mLastClickTime = now
            // passing data to the book activity
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(Constants.DISHES_DETAIL,dataList[position])
            view.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
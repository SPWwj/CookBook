package com.wstonedev.xcollector.view.details.cast

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.cookbook.R
import com.example.cookbook.data.model.DishesModel
import com.example.cookbook.utils.Constants
import com.example.cookbook.view.detail.DetailActivity
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso


internal class StepAdapter(private val context: Context, private val dataList: List<DishesModel.Step>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<StepAdapter.StepHolder>() {

    internal inner class StepHolder(val mView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mView) {

        var txtTitle: TextView = mView.findViewById(R.id.step_content)
        val coverImage: ImageView = mView.findViewById(R.id.step_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_step, parent, false)
        return StepHolder(view)
    }

    override fun onBindViewHolder(holder: StepHolder, position: Int) {
        holder.txtTitle.text = dataList[position].content

        val builder = Picasso.Builder(context)
        builder.downloader(OkHttp3Downloader(context))
        builder.build().load(dataList[position].image)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.coverImage)
//        var mLastClickTime = System.currentTimeMillis()
//
//        holder.itemView.setOnClickListener { view ->
//
//            val now = System.currentTimeMillis()
//            if (now - mLastClickTime < Constants.CLICK_TIME_INTERVAL) {
//                return@setOnClickListener
//            }
//            mLastClickTime = now
//            // passing data to the book activity
//            val intent = Intent(context, DetailActivity::class.java)
//            intent.putExtra(Constants.DISHES_DETAIL,dataList[position])
//            view.context.startActivity(intent)
//        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}

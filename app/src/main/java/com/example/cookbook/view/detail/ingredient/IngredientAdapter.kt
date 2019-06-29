package com.wstonedev.xcollector.view.details.episode

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R
import com.example.cookbook.data.model.DishesModel
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

internal class IngredientAdapter(private val context: Context, private val dataList: List<DishesModel.Ingredient>) :
    RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    internal inner class IngredientViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        var txtTitle: TextView = mView.findViewById(R.id.ingredient_name)
        val coverImage: ImageView = mView.findViewById(R.id.ingredient_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_ingredient, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.txtTitle.text = dataList[position].name

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

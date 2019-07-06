package com.wstonedev.xcollector.view.details.episode

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R
import com.example.cookbook.data.model.DishesModel
import com.example.cookbook.utils.Constants
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

internal class IngredientAdapter(private val context: Context, private val dataList: List<DishesModel.Ingredient>) :
    RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    internal inner class IngredientViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        var txtTitle: TextView = mView.findViewById(R.id.ingredient_name)
        val coverImage: ImageView = mView.findViewById(R.id.ingredient_image)
        val itemLayoutIngredient: RelativeLayout = mView.findViewById(R.id.item_layout_ingredient)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_ingredient, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.txtTitle.text = dataList[position].Name + dataList[position].Quantity
        if(dataList[position].Image!=null && dataList[position].Image?.any()!!)
        {
            val builder = Picasso.Builder(context)
            builder.downloader(OkHttp3Downloader(context))
            builder.build().load(dataList[position].Image)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.coverImage)
        }
        else{
            holder.coverImage.visibility=View.GONE
        }
        var mLastClickTime = System.currentTimeMillis()

        holder.itemView.setOnClickListener {

            val now = System.currentTimeMillis()
            if (now - mLastClickTime < Constants.CLICK_TIME_INTERVAL) {
                return@setOnClickListener
            }
            mLastClickTime = now
            // passing data to the book activity
            if (dataList[position].Highlight) {
                holder.itemLayoutIngredient.setBackgroundResource(0);
                dataList[position].Highlight=false

            } else {
                holder.itemLayoutIngredient.background = ContextCompat.getDrawable(context, R.drawable.item_layout_border);
                dataList[position].Highlight=true

            }
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}

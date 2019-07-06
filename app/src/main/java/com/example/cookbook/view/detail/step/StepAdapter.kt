package com.wstonedev.xcollector.view.details.cast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.cookbook.R
import com.example.cookbook.data.model.DishesModel
import com.example.cookbook.utils.Constants
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso


internal class StepAdapter(private val context: Context, private val dataList: List<DishesModel.RecipeInstruction>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<StepAdapter.StepHolder>() {

    internal inner class StepHolder(val mView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mView) {

        var txtTitle: TextView = mView.findViewById(R.id.step_content)
        val coverImage: ImageView = mView.findViewById(R.id.step_image)
        val itemLayoutStep: RelativeLayout = mView.findViewById(R.id.item_layout_step)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_step, parent, false)
        return StepHolder(view)
    }

    override fun onBindViewHolder(holder: StepHolder, position: Int) {
        holder.txtTitle.text = dataList[position].Step

        if(dataList[position].Image!=null && dataList[position].Image?.any()!!)
        {
            val builder = Picasso.Builder(context)
            builder.downloader(OkHttp3Downloader(context))
//        builder.build().load(dataList[position].Image)
//            .placeholder(R.drawable.ic_launcher_background)
//            .error(R.drawable.ic_launcher_background)
//            .into(holder.dishesImage)
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
                holder.itemLayoutStep.setBackgroundResource(0);
                dataList[position].Highlight=false

            } else {
                holder.itemLayoutStep.background = ContextCompat.getDrawable(context, R.drawable.item_layout_border);
                dataList[position].Highlight=true
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}

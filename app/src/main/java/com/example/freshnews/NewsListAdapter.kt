package com.example.freshnews

import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter(private val listener: NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>() {
    private val items:ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val View =LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false)
        val viewHolder =NewsViewHolder(View)
        View.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder



    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
     val currentItem =items[position]
        holder.TitleView.text =currentItem.title
        holder.author.text =currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
    }

    override fun getItemCount(): Int {
      return items.size
    }

    fun updateNews(updateNews: ArrayList<News>){
        items.clear()
        items.addAll(updateNews)

        notifyDataSetChanged()
    }
}
class NewsViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
    val TitleView:TextView =itemView.findViewById(R.id.ImageView)
    val image:ImageView =itemView.findViewById(R.id.Image)
    val author:TextView =itemView.findViewById(R.id.Author)
}
interface NewsItemClicked{
    fun onItemClicked(item:News)
}
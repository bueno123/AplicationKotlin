package com.example.aplicationkotlin01.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicationkotlin01.netWork.UserResponsePosts
import com.example.myprojectkotlin.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main_feed.view.*
import kotlinx.android.synthetic.main.activity_main_feed.view.*

class ReyclerAdapterPosts (private var data: List<UserResponsePosts>, private val listener: PostHolder.OnAdapterListener) :
RecyclerView.Adapter<ReyclerAdapterPosts.PostHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflatedView = parent.inflate(R.layout.activity_main_feed , false)
        return PostHolder(
            inflatedView
        )
    }

    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    fun updateList(postList: List<UserResponsePosts>){
        this.data = postList
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val posts : UserResponsePosts = this.data[position]

        holder.itemView.infopersons.text = posts.username
        holder.itemView.infocommnent.text = posts.body
        holder.itemView.textprofilelikes.text = posts.likes

        if(!posts.image.isBlank()) {
            Picasso.get()
                .load(posts.image)
                .into(holder.itemView.imageView2)
        }

        if(!posts.user_image.isBlank()) {
            Picasso.get()
                .load(posts.user_image)
                .resize(100,100)
                .centerCrop()
                .into(holder.itemView.imagenuserloggin)
        }
        holder.itemView.setOnClickListener { listener.onItemClickListener(posts) }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class PostHolder(v: View): RecyclerView.ViewHolder(v), View.OnClickListener {

        init {
            v.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            if (v != null) {
                Toast.makeText(v.context, "Item", Toast.LENGTH_SHORT).show()
            }
        }
        interface OnAdapterListener {
            fun onItemClickListener( item: UserResponsePosts)
        }
    }
}
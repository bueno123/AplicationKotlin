package com.example.myprojectkotlin.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myprojectkotlin.R
import com.example.myprojectkotlin.netWork.UserResposeUsers
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main_feed.view.imagenuserloggin
import kotlinx.android.synthetic.main.activity_main_friends.view.*


class ReyclerAdapterUsers (private var data: List<UserResposeUsers>, private val listener: ReyclerAdapterUsers.PostHolder.OnAdapterListener) :
    RecyclerView.Adapter<ReyclerAdapterUsers.PostHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReyclerAdapterUsers.PostHolder {
        val inflatedView = parent.inflate(R.layout.activity_main_friends, false)
        return ReyclerAdapterUsers.PostHolder(
            inflatedView
        )
    }

    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    fun updateList(postList: List<UserResposeUsers>){
        this.data = postList
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }


    class PostHolder (v: View): RecyclerView.ViewHolder(v), View.OnClickListener{
        init {
            v.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            if (v != null) {

                Toast.makeText(v.context, "Item", Toast.LENGTH_SHORT).show()
            }
        }
        interface OnAdapterListener {
            fun onItemClickListener( item: UserResposeUsers)
        }
    }

    override fun onBindViewHolder(holder: ReyclerAdapterUsers.PostHolder, position: Int) {
        val posts : UserResposeUsers = this.data[position]

        holder.itemView.infofriends.text = posts.name + ' ' + posts.lastname

        if(!posts.image.isBlank()) {
            Picasso.get()
                .load(posts.image)
                .into(holder.itemView.imagenuserloggin)
        }

        holder.itemView.setOnClickListener {
            listener.onItemClickListener(posts)
        }
    }
}

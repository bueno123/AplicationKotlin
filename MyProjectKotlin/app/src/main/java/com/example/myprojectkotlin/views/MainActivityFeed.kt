package com.example.myprojectkotlin.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplicationkotlin01.adapters.ReyclerAdapterPosts
import com.example.aplicationkotlin01.netWork.RepositoryPosts
import com.example.aplicationkotlin01.netWork.UserResponsePosts
import com.example.myprojectkotlin.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main_feed_recycler.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.ArrayList

class MainActivityFeed : AppCompatActivity(), ReyclerAdapterPosts.PostHolder.OnAdapterListener {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: ReyclerAdapterPosts

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_feed_recycler)

        adapter = ReyclerAdapterPosts(
            ArrayList(),
            this
        )
        linearLayoutManager = LinearLayoutManager(this)
        recyclerViewPost.layoutManager = linearLayoutManager
        recyclerViewPost.adapter = adapter

        callServicePosts()
    }


    private fun callServicePosts() {
        val serviceposts = RepositoryPosts.RetrofitRepository.getServicePosts()

        CoroutineScope(Dispatchers.IO).launch {
            val response = serviceposts.getPosts()

            withContext(Dispatchers.Main) {

                try {
                    if(response.isSuccessful) {
                        val user : List<UserResponsePosts>? = response.body()
                        if( user != null) updateInfo(user)
                    }else{
                        Toast.makeText(this@MainActivityFeed, "Error ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }catch (e : HttpException) {
                    Toast.makeText(this@MainActivityFeed, "Error ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateInfo(list: List<UserResponsePosts>) {
        adapter.updateList(list)
    }

    override fun onItemClickListener(item: UserResponsePosts) {
        Toast.makeText(this, "Click item ${item.username}", Toast.LENGTH_LONG).show()
        val postString : String = Gson().toJson(item, UserResponsePosts::class.java)
        val post : UserResponsePosts = Gson().fromJson(postString, UserResponsePosts::class.java)
    }
}
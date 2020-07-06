package com.example.myprojectkotlin.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myprojectkotlin.R
import com.example.myprojectkotlin.adapters.ReyclerAdapterUsers
import com.example.myprojectkotlin.netWork.RepositoryUsers
import com.example.myprojectkotlin.netWork.UserResposeUsers
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_feed_recycler.*
import kotlinx.android.synthetic.main.activity_main_friends.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.makeCall
import retrofit2.HttpException
import java.util.*


class MainActivityFriends : AppCompatActivity(), ReyclerAdapterUsers.PostHolder.OnAdapterListener {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: ReyclerAdapterUsers


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_friends_recycler)

        adapter = ReyclerAdapterUsers( ArrayList(), this )
        linearLayoutManager = LinearLayoutManager(this)
        recyclerViewPost.layoutManager = linearLayoutManager
        recyclerViewPost.adapter = adapter

        callServiceUSers()

    }

    private fun callServiceUSers() {
        val serviceposts = RepositoryUsers.RetrofitRepository.getServiceUsers()

        CoroutineScope(Dispatchers.IO).launch {
            val response = serviceposts.getUsers()

            withContext(Dispatchers.Main) {

                try {
                    if(response.isSuccessful) {
                        val user : List<UserResposeUsers>? = response.body()
                        if( user != null) updateInfo(user)
                    }else{
                        Toast.makeText(this@MainActivityFriends, "Error ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }catch (e : HttpException) {
                    Toast.makeText(this@MainActivityFriends, "Error ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateInfo(list: List<UserResposeUsers>) {
        adapter.updateList(list)
    }

    override fun onItemClickListener(item: UserResposeUsers) {

        Toast.makeText(this, "Email: ${item.email} \nPhone: ${item.phone}", Toast.LENGTH_LONG).show()
        val postString : String = Gson().toJson(item, UserResposeUsers::class.java)
        val post : UserResposeUsers = Gson().fromJson(postString, UserResposeUsers::class.java)

        makeCall("${item.phone}")
    }

    fun Context.makeCall(number: String): Boolean {
        try {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
            startActivity(intent)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

}
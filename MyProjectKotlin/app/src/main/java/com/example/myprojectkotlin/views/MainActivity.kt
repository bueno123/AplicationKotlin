package com.example.myprojectkotlin.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aplicationkotlin01.netWork.Repository
import com.example.aplicationkotlin01.netWork.UserResponse
import com.example.myprojectkotlin.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        callService()

        imageButtonFeed.setOnClickListener{ view ->
            intent = Intent(this, MainActivityFeed::class.java)
            startActivity(intent)
        }

    }

    private fun callService() {
        val service = Repository.RetrofitRepository.getService()

        CoroutineScope(Dispatchers.IO).launch {
            val response =  service.getProfile()

            withContext(Dispatchers.Main) {

                try {
                    if(response.isSuccessful) {

                        val user : UserResponse?  = response.body()
                        if( user != null) {

                            textnameuser.text = user.name+' '+user.lastname
                            textcantlikes.text = user.social.likes.toString()
                            textcantposts.text = user.social.posts.toString()
                            textcantshares.text = user.social.shares.toString()
                            textcantfriends.text = user.social.friends.toString()
                            textuseryear.text = user.age
                            textemail.text = user.email
                            textgps.text = user.location
                            textcarpet.text = user.occupation

                            Picasso.get()
                                .load(user.image)
                                .resize(100,100)
                                .centerCrop()
                                .into(imagenuserloggin)


                            Toast.makeText(this@MainActivity, "Usuario: ${user.name} tiene ${user.social.friends} amigos", Toast.LENGTH_LONG).show()
                        }
                    }else{
                        Toast.makeText(this@MainActivity, "Error ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }catch (e : HttpException) {
                    Toast.makeText(this@MainActivity, "Error ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
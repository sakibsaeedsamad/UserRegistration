package com.sssakib.userregistration.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sssakib.userregistration.R
import com.sssakib.userregistration.model.RetrofitClient
import com.sssakib.userregistration.model.User
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        logInButton.setOnClickListener(){
            val mobile = mobileLogIn!!.text.toString().trim { it <= ' ' }
            val password = passwordLogIn!!.text.toString().trim { it <= ' ' }

            if (mobile.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Please give Mobile and Password",
                    Toast.LENGTH_LONG
                ).show()
            } else {

                val call: Call<User?>? = RetrofitClient
                    .instance
                    ?.aPI
                    ?.checkUser(User(mobile, password))
                call?.enqueue(object: Callback<User?>{
                    override fun onFailure(call: Call<User?>, t: Throwable) {
                        Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<User?>, response: Response<User?>) {
                        val name: String? = response?.body()?.name
                        val mob: String? = response?.body()?.mobile
                        val email: String? = response?.body()?.email
                        val address: String? = response?.body()?.address
                        val pass: String? = response?.body()?.password
                        val id: Long? = response?.body()?.id
                        val img: String? = response?.body()?.image
                        if (mob == mobile && pass == password) {
                            val i = Intent(this@MainActivity, Welcome::class.java)
                            i.putExtra("name", name)
                            i.putExtra("mobile", mob)
                            i.putExtra("email", email)
                            i.putExtra("address", address)
                            i.putExtra("id", id)
                            i.putExtra("img", img)
                            startActivity(i)
                            cleanFields()
                        }


                    }

                })


            }
        }
        registerId.setOnClickListener(){

            val i = Intent(this@MainActivity, Registration::class.java)
            startActivity(i)
        }
    }



    private fun cleanFields() {
        mobileLogIn!!.text = null
        passwordLogIn!!.text = null
    }
    override fun onBackPressed() {
        finishAffinity()
        finish()
    }
}
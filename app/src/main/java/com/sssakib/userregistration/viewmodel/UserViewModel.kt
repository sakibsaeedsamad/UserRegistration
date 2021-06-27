package com.sssakib.userregistration.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sssakib.userregistration.model.ResponseModel
import com.sssakib.userregistration.model.RetrofitClient
import com.sssakib.userregistration.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var uSer: MutableLiveData<User>
    lateinit var eror: MutableLiveData<ResponseModel>

    init {
        uSer = MutableLiveData()
        eror = MutableLiveData()

    }

//    fun getAllUsersObservers(): MutableLiveData<User> {
//
//        return uSer
//
//    }

//    fun getAllUsers() {
//
//        val call: Call<List<User>?>? = RetrofitClient
//            .instance
//            ?.aPI
//            ?.findAllUsers()
//        call?.enqueue(object : Callback<List<User>?> {
//            override fun onFailure(call: Call<List<User>?>?, t: Throwable) {
//            }
//            override fun onResponse(call: Call<List<User>?>?, response: Response<List<User>?>?) {
//                if (response!!.isSuccessful) {
//                    val list = response.body()
//                    allUsers.postValue( list as List<User>)
//                }
//            }
//
//        })
//
//    }

    fun insertUserInfo(user: User) {

        val call: Call<ResponseModel?>? = RetrofitClient
            .instance
            ?.aPI
            ?.createUser(user)
        call?.enqueue(object : Callback<ResponseModel?> {
            override fun onFailure(call: Call<ResponseModel?>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseModel?>, response: Response<ResponseModel?>) {
                if (response.isSuccessful) {
                    val e = response.body()
                    eror.postValue(e)
                }
            }


        })


    }

    fun updateUserInfo(user: User) {
        val call: Call<User?>? = RetrofitClient
            .instance
            ?.aPI
            ?.updateUser(user, user.id)
        call?.enqueue(object : Callback<User?> {
            override fun onFailure(call: Call<User?>, t: Throwable) {

            }

            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                if (response.isSuccessful) {
                   val u = response.body()
                    uSer.postValue(u)
                }
            }


        })
    }

    fun logIn(user: User) {
        val call: Call<User?>? = RetrofitClient
            .instance
            ?.aPI
            ?.checkUser(User(user.mobile, user.password))
        call?.enqueue(object : Callback<User?> {
            override fun onFailure(call: Call<User?>, t: Throwable) {

            }override fun onResponse(
                call: Call<User?>,
                response: Response<User?>
            ) {
                if (response.isSuccessful) {
                    val u = response.body()
                    uSer.postValue(u)
                }
            }


        })
    }


}
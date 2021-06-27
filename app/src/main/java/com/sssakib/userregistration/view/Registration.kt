package com.sssakib.userregistration.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sssakib.userregistration.R
import com.sssakib.userregistration.model.ResponseModel
import com.sssakib.userregistration.model.RetrofitClient
import com.sssakib.userregistration.model.User
import kotlinx.android.synthetic.main.activity_registration.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Registration : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)


        registerButton.setOnClickListener(this)
        goBackButton.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.registerButton) {
            doInsert()
        } else if (v.id == R.id.goBackButton) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }

    fun doInsert() {
        val name = nameRegistrationET!!.text.toString().trim { it <= ' ' }
        val mobile = mobileRegistrationET!!.text.toString().trim { it <= ' ' }
        val email = ""
        val password =
            passwordRegistrationET!!.text.toString().trim { it <= ' ' }
        val address = ""
        val img =
            """
            /9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAKBueIx4ZKCMgoy0qqC+8P//8Nzc8P//////////////
            ////////////////////////////////////////////2wBDAaq0tPDS8P//////////////////
            ////////////////////////////////////////////////////////////wAARCAEEAMMDASIA
            AhEBAxEB/8QAFwABAQEBAAAAAAAAAAAAAAAAAAECA//EAB8QAQACAgIDAQEAAAAAAAAAAAABEQIx
            IUFRYYEyA//EABYBAQEBAAAAAAAAAAAAAAAAAAABAv/EABoRAQEBAQEBAQAAAAAAAAAAAAARARIh
            AjH/2gAMAwEAAhEDEQA/AOYAIAAAAAAAAAAAAAAAAAAABC5XfKCgACiNY6RWSm6KKsYopuigjFFN
            0UJGKKbooIwU1RQRmhqigjI1RQRkaooIyLMMiKIAoAAADWPbLWOpFxQVGkFAQUBC1+HwEuS1FEst
            TgEsteDgEsXg4BmdMN5aYGdABBUAAAVrHtlrDci40AjQcABwcAAAoAAfQAAACgBMvzLm3npgZ0AE
            AAAAVrDcstYbFaWvYUjRXsr2UUoV7AAKACigAooAEUBF6AGM9MOmenMZ39ABAAAAFaw/TLWP6gGx
            Y2g2AAAABQBZfsoAufIAAAAHQMZ9Mt59MKzqKCIgAAAK1j+oZax3Cja3PkEbLnyXPkALAAA+AB8P
            gAfAAAAAHPPbLee2VY1CQlBBUAABqFjaKo6HYqNIKAgoCCgIKAgoCCgIL8QGMtstZbRWUJAEAQQA
            GlRpRtUjUKi7swAWMd6AEO9ACHegBDvQAh3oANfO0RURpidos7RWRFQEFQEAQbVFUbjUKkaUN9wA
            Wsc6AFOdAA50ASnOgBTnQAa+cgiiNsTtFRWERpBUJiuxctar7YMAINqiqNY6aZx0s8QiqM3K2sKo
            llkKolyckKonJyQqiXJckKozclyQrSESSDJwd8mVXFDKIqCnaZrG0yBkBBtUhYUaxWdJioqVJTQU
            SilCiVJUqFEqfJU+VCiUUoUSkpQoRFE6EnQIh2DIgCkbZyahnIEAQdMdhAo1irMbaDEtbSpKlfBb
            LSijwWy5KKlAuS5OSgSyyigLLkopQiSdGiUVkARAAIZnbTE7AAQdI2vbKztRY20zG2gwARQAAAAA
            AABAUCRJAvhCgQRUAYnbp05gAINqi2osNMNAoCKAAAAAAAigAAkqkiCKAgAGWnN0z05gAINqgopY
            AogCgAAAAUAAACggAApwCAAznphrPplAABsBQUAAAAAAAUAAAAAAAAAAgAY/ptkEAAH/2Q==
            
            """.trimIndent()
        if (name.isEmpty() || mobile.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty()) {
            Toast.makeText(applicationContext, "Fill up all values", Toast.LENGTH_LONG).show()
        } else {
            val call: Call<ResponseModel?>? = RetrofitClient
                .instance
                ?.aPI
                ?.createUser(User(name, mobile, email, address, password, img))
            call?.enqueue(object : Callback<ResponseModel?> {
                override fun onFailure(call: Call<ResponseModel?>, t: Throwable) {
                    Toast.makeText(this@Registration, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<ResponseModel?>,
                    response: Response<ResponseModel?>
                ) {
                    val outCode: String? = response.body()?.outCode
                    val outMessage: String? = response.body()?.outMessage
                    if (outCode == "1" && outMessage == "USER ALREADY EXISTS") {
                        Toast.makeText(this@Registration, "User already Exists!", Toast.LENGTH_LONG)
                            .show()
                    }else {
                        Toast.makeText(this@Registration,"User Save Successfully",Toast.LENGTH_LONG).show()

                    }




                }

            })
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@Registration, MainActivity::class.java))
        finish()
    }
}
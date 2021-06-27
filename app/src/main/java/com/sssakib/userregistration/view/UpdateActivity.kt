package com.sssakib.userregistration.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.sssakib.userregistration.R
import com.sssakib.userregistration.model.RetrofitClient
import com.sssakib.userregistration.model.User
import kotlinx.android.synthetic.main.activity_update.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream


class UpdateActivity : AppCompatActivity() {
    private var id: Long = 0

    var imageResult: String? = null
    val RequestPermissionCode = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        val intent = intent
        id = intent.extras!!.getLong("id")


        EnableRuntimePermission()

        updateButton.setOnClickListener{

            val email = ""
            val address =""
            val password =
                passwordUpdateET.getText().toString().trim { it <= ' ' }
            val image = imageResult
            if (password.isEmpty() || image!!.isEmpty()) {
                Toast.makeText(applicationContext, "Fill up all values", Toast.LENGTH_LONG)
                    .show()
            } else {
                val u = User(email, address, password, image)
                val call: Call<User?>? = RetrofitClient
                    .instance
                    ?.aPI
                    ?.updateUser(u, id)
                call?.enqueue(object : Callback<User?>{
                    override fun onFailure(call: Call<User?>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<User?>, response: Response<User?>) {
                        val name: String? = response?.body()?.name
                        val mob: String? = response?.body()?.mobile
                        val email: String? = response?.body()?.email
                        val address: String? = response?.body()?.address
                        val pass: String? = response?.body()?.password
                        val id: Long? = response?.body()?.id
                        val img: String? = response?.body()?.image
                        val i = Intent(this@UpdateActivity, Welcome::class.java)
                        i.putExtra("name", name)
                        i.putExtra("mobile", mob)
                        i.putExtra("email", email)
                        i.putExtra("address", address)
                        i.putExtra("id", id)
                        i.putExtra("img", img)
                        startActivity(i)

                    }

                })
            }
        }

        captureImageBTN.setOnClickListener(View.OnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 7)
        })

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 7 && resultCode == Activity.RESULT_OK) {
            val captureImage = data!!.extras!!["data"] as Bitmap?
            profilePic!!.setImageBitmap(captureImage)
            imageResult = captureImage?.let { convertBitmapToString(it) }
        }
    }
    fun EnableRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this@UpdateActivity,
                Manifest.permission.CAMERA
            )
        ) {
            Toast.makeText(
                this@UpdateActivity,
                "CAMERA permission allows us to Access CAMERA app",
                Toast.LENGTH_LONG
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                this@UpdateActivity, arrayOf(
                    Manifest.permission.CAMERA
                ), RequestPermissionCode
            )
        }
    }
    override fun onRequestPermissionsResult(
        RC: Int,
        per: Array<String>,
        PResult: IntArray
    ) {
        super.onRequestPermissionsResult(RC, per, PResult)
        when (RC) {
            RequestPermissionCode -> if (PResult.size > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this@UpdateActivity,
                    "Permission Granted, Now your application can access CAMERA.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this@UpdateActivity,
                    "Permission Canceled, Now your application cannot access CAMERA.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun convertBitmapToString(bitmap: Bitmap): String? {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 5, stream)
        val byteArray = stream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@UpdateActivity, Welcome::class.java))
        finish()
    }



}
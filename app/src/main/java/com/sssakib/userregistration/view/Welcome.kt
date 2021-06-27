package com.sssakib.userregistration.view


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sssakib.userregistration.R
import kotlinx.android.synthetic.main.activity_welcome.*


class Welcome : AppCompatActivity(), View.OnClickListener {

    var name: String? = null
    var mobile: String? = null
    var email: String? = null
    var address: String? = null
    var img: String? = null
    private var id: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        val intent = intent
        name = intent.extras!!.getString("name")
        mobile = intent.extras!!.getString("mobile")
        email = intent.extras!!.getString("email")
        address = intent.extras!!.getString("address")
        img = intent.extras!!.getString("img")
        id = intent.extras!!.getLong("id")

        nameTV.setText(name)
        mobileTV.setText(mobile)
        imageView.setImageBitmap(convertStringToBitmap(img))
        clickForUpdateBtn.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.clickForUpdateBtn) {
            val i = Intent(this@Welcome, UpdateActivity::class.java)
            i.putExtra("id", id)
            this@Welcome.startActivity(i)
        }
    }

    companion object {
        fun convertStringToBitmap(string: String?): Bitmap {
            val byteArray =
                Base64.decode(string, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@Welcome, MainActivity::class.java))
        finish()
    }
}
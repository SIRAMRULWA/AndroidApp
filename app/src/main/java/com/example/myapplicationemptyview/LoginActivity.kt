package com.example.myapplicationemptyview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    lateinit var etUsername: EditText
    lateinit var etPassword: EditText
    lateinit var userName:  String
    lateinit var password: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsername = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);

        var etButton = findViewById<Button>(R.id.btnLogin)
        etButton.setOnClickListener {
            login(etUsername, etPassword);
        }

        this.findViewById<TextView>(R.id.tvRegisterLink).setOnClickListener{ //Routing back to register
            startActivity(Intent(this, RegisterActivity::class.java)) // routing back to register
        }

    }
    private fun login(etUsername: EditText, etPassword: EditText) {
        val userName: String = this.etUsername.getText().toString().trim()
        val password: String = this.etPassword.getText().toString().trim()

        val call: Call<ResponseBody> = RetrofitClient
            .getInstance()
            .api
            .checkUser(user(userName, password))

        if (userName.isEmpty()) {
            this.etUsername.setError("Username is required")
            this.etUsername.requestFocus()
            return
        } else if (password.isEmpty()) {
            this.etPassword.setError("Password is required")
            this.etPassword.requestFocus()
            return
        }

        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>?, response: Response<ResponseBody?>) {
                var s = ""
                try {
                    s = response.body()!!.string()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                if (s == userName) {
                    val intent = Intent(this@LoginActivity,Dashboard::class.java)
                    intent.putExtra("Username",s)

                    Toast.makeText(
                        this@LoginActivity,
                        "Successfully login",
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity, "User does not exists!", Toast.LENGTH_LONG)
                        .show()
                }


            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_LONG).show()
            }


        })
    }

}

package com.example.myapplicationemptyview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import android.content.Intent
import android.widget.TextView
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {

    lateinit var etUsername: EditText
    lateinit var etPassword: EditText
    lateinit var btnRegister: Button
    lateinit var userName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)

        etUsername =findViewById(R.id.etRUserName)
        etPassword = findViewById(R.id.etRPassword)
        btnRegister =findViewById(R.id.btnRegister)


        btnRegister.setOnClickListener{
            userName = etUsername.text.toString();
            //Toast.makeText(this,  " has been registered", Toast.LENGTH_SHORT).show()
            registeUser()
        }
        this.findViewById<TextView>(R.id.tvLoginLink).setOnClickListener{ //routing to login
            startActivity(Intent(this, LoginActivity::class.java)) //routing to login
        }

    }

    private fun registeUser() {
        val userName: String = etUsername.text.toString().trim()
        val password: String = etPassword.text.toString().trim()

        if (userName.isEmpty()) {
            etUsername.error = "Username is required"
            etUsername.requestFocus()
            return         }
        else if (password.isEmpty()) {
            etPassword.error = "Password is required"
            etPassword.requestFocus()
            return
        }

        val call: Call<ResponseBody> = RetrofitClient.getInstance()
            .api
            .createUser(user(userName, password))

        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>?,
                response: Response<ResponseBody?>
            )
            {
                var s = ""
                try {
                    s = response.body()!!.string()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                if (s == "SUCCESS") {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Successfully registered. Please login",
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                } else {
                    Toast.makeText(this@RegisterActivity, "User already exists!", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    }

package com.sagheer.attendenceappnjalauniversity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.sagheer.attendenceappnjalauniversity.Fragments.DetailsSelectStudent
import com.sagheer.attendenceappnjalauniversity.Fragments.DetailsSelectTeacher
import com.sagheer.attendenceappnjalauniversity.Fragments.Login
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    companion object {
        var mAuth = FirebaseAuth.getInstance()
        var isTeacher = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onResume() {
        super.onResume()

        if (mAuth.currentUser != null) {
            var isStudent = false
            val url = "https://njala-attendence.firebaseio.com/Students.json"
            val queue = Volley.newRequestQueue(this)
            val sr = StringRequest(Request.Method.GET, url, Response.Listener {

                val jsonObject = JSONObject(it)
                val user = mAuth.currentUser!!.email.toString().substringBefore('@')
                for (key in jsonObject.keys()) {
                    if (key.toString().equals(user.toLowerCase())) {
                        isStudent = true
                    }
                }
                if (isStudent) {
                    openFragment(DetailsSelectStudent())
                } else {
                    openFragment(DetailsSelectTeacher())
                }

            }, Response.ErrorListener {
                showToast("No Internet Connection!!!")
            })
            queue.add(sr)

        } else
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, Login()).commit()
    }


    fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .addToBackStack("").commit()
    }
}

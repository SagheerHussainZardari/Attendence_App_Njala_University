package com.sagheer.attendenceappnjalauniversity.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sagheer.attendenceappnjalauniversity.MainActivity
import com.sagheer.attendenceappnjalauniversity.R
import com.sagheer.attendenceappnjalauniversity.showToast
import kotlinx.android.synthetic.main.fragment_login.*
import org.json.JSONObject


class Login : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            com.sagheer.attendenceappnjalauniversity.R.layout.fragment_login,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var list = arrayOf("Lecturer", "Student")
        spinner_accountType.adapter = ArrayAdapter<String>(requireContext(), R.layout.spinner, list)

        btn_login.setOnClickListener {
            if (et_Email_LoginFragment.text.isNotEmpty()) {

                if (et_Password_LoginFragment.text.isNotEmpty()) {
                    if (spinner_accountType.selectedItem.equals("Lecturer")) {
                        loginInAsTeacher()
                    } else {
                        loginInAsStudent()
                    }

                } else {
                    et_Password_LoginFragment.error = "Field Should Not Be Empty"
                }
            } else {
                et_Email_LoginFragment.error = "Field Should Not Be Empty"
            }
        }

    }

    private fun loginInAsTeacher() {

        progressBarLayout2.visibility = View.VISIBLE

        var isTeacher = false
        val url = "https://njala-attendence.firebaseio.com/TeachersEmailsList.json"
        val queue = Volley.newRequestQueue(context)
        val sr = StringRequest(Request.Method.GET, url, Response.Listener {
            var jsonObject = JSONObject(it)
            var user = et_Email_LoginFragment.text.toString().substringBefore('@')
            for (key in jsonObject.keys()) {
                if (key.toString().equals(user.toLowerCase())) {
                    isTeacher = true
                }
            }

            if (isTeacher) {
                MainActivity.mAuth.signInWithEmailAndPassword(
                    et_Email_LoginFragment.text.toString(),
                    et_Password_LoginFragment.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        MainActivity.isTeacher = true
                        (context as MainActivity).openFragment(DetailsSelectTeacher())

                        progressBarLayout2.visibility = View.GONE
                    } else {
                        progressBarLayout2.visibility = View.GONE
                        context?.showToast("Failed Login ${it.exception!!.localizedMessage}")
                    }
                }

            } else {
                progressBarLayout2.visibility = View.GONE
                context?.showToast("$user you are not a Lecturer")
            }
        }, Response.ErrorListener {

        })

        queue.add(sr)
    }

    private fun loginInAsStudent() {
        var isStudent = false

        val url = "https://njala-attendence.firebaseio.com/StudentsEmailsList.json"
        val queue = Volley.newRequestQueue(context)
        val sr = StringRequest(Request.Method.GET, url, Response.Listener {

            progressBarLayout2.visibility = View.VISIBLE
            var jsonObject = JSONObject(it)
            var user = et_Email_LoginFragment.text.toString().substringBefore('@')
            for (key in jsonObject.keys()) {
                if (key.toString().equals(user.toLowerCase())) {
                    isStudent = true
                }
            }

            if (isStudent) {
                MainActivity.mAuth.signInWithEmailAndPassword(
                    et_Email_LoginFragment.text.toString(),
                    et_Password_LoginFragment.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        MainActivity.isTeacher = false
                        (context as MainActivity).openFragment(DetailsSelectStudent())
                        progressBarLayout2.visibility = View.GONE

                    } else {
                        progressBarLayout2.visibility = View.GONE
                        context?.showToast("Failed Login ${it.exception!!.localizedMessage}")
                    }
                }
            } else {
                progressBarLayout2.visibility = View.GONE
                context?.showToast("$user you are not a Student")
            }

        }, Response.ErrorListener {

        })
        queue.add(sr)
    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar!!.show()
    }

}
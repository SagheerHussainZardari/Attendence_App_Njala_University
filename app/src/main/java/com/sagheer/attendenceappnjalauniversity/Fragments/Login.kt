package com.sagheer.attendenceappnjalauniversity.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var list = arrayOf("Teacher", "Student")
        spinner_accountType.adapter = ArrayAdapter<String>(requireContext(), R.layout.spinner, list)

        btn_login.setOnClickListener {
            if (et_Email_LoginFragment.text.isNotEmpty()) {

                if (et_Password_LoginFragment.text.isNotEmpty()) {
                    if (spinner_accountType.selectedItem.equals("Teacher")) {
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
        var isTeacher = false
        val url = "https://njala-attendence.firebaseio.com/Teachers.json"
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
                (context as MainActivity).openFragment(DetailsSelectTeacher())
                context?.showToast("$user is a Teacher")
            } else {
                context?.showToast("$user you are not a Teacher")
            }
        }, Response.ErrorListener {

        })

        queue.add(sr)
    }

    private fun loginInAsStudent() {
        var isStudent = false

        val url = "https://njala-attendence.firebaseio.com/Students.json"
        val queue = Volley.newRequestQueue(context)
        val sr = StringRequest(Request.Method.GET, url, Response.Listener {
            var jsonObject = JSONObject(it)
            var user = et_Email_LoginFragment.text.toString().substringBefore('@')
            for (key in jsonObject.keys()) {
                if (key.toString().equals(user.toLowerCase())) {
                    isStudent = true
                }
            }

            if (isStudent) {
                (context as MainActivity).openFragment(DetailsSelectStudent())
                context?.showToast("$user is a Student")
            } else {
                context?.showToast("$user you are not a Student")
            }

        }, Response.ErrorListener {

        })
        queue.add(sr)
    }


}
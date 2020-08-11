package com.sagheer.attendenceappnjalauniversity.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sagheer.attendenceappnjalauniversity.AdaptersModels.ShowAttendenceAdapter
import com.sagheer.attendenceappnjalauniversity.AdaptersModels.ShowAttendenceModel
import com.sagheer.attendenceappnjalauniversity.Fragments.DetailsSelectStudent.Companion.course
import com.sagheer.attendenceappnjalauniversity.Fragments.DetailsSelectStudent.Companion.program
import com.sagheer.attendenceappnjalauniversity.Fragments.DetailsSelectStudent.Companion.year
import com.sagheer.attendenceappnjalauniversity.Fragments.DetailsSelectTeacher.Companion.coursee
import com.sagheer.attendenceappnjalauniversity.Fragments.DetailsSelectTeacher.Companion.isTeacherLoggedIn
import com.sagheer.attendenceappnjalauniversity.Fragments.DetailsSelectTeacher.Companion.programm
import com.sagheer.attendenceappnjalauniversity.Fragments.DetailsSelectTeacher.Companion.yearr
import com.sagheer.attendenceappnjalauniversity.R
import com.sagheer.attendenceappnjalauniversity.showToast
import kotlinx.android.synthetic.main.fragment_show_attendence.*
import org.json.JSONObject


class showAttendence : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_attendence, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isTeacherLoggedIn) {
            showTeacherAttendence()
        } else {
            showStudentAttendence()
        }

    }

    private fun showTeacherAttendence() {
        tv_ProgramAndYear_ShowAttendence.text = "$programm -- $yearr\n$coursee"
        var list = ArrayList<ShowAttendenceModel>()
        var url =
            "https://njala-attendence.firebaseio.com/StudentsAttedence/$programm/$yearr/$coursee.json"
        var queue = Volley.newRequestQueue(context)

        var sr = StringRequest(Request.Method.GET, url, Response.Listener {
            try {
                if (it != "null") {

                    for (key in JSONObject(it).keys()) {
                        var nameRoll =
                            JSONObject(it).getJSONObject(key).getString("name")
                                .toString() + "-" + key.toString()
                        var present = JSONObject(it).getJSONObject(key).getString("present").toInt()
                        var absent = JSONObject(it).getJSONObject(key).getString("absent").toInt()
                        var sick = JSONObject(it).getJSONObject(key).getString("sick").toInt()
                        var excuse = JSONObject(it).getJSONObject(key).getString("excuse").toInt()
                        var total = present + absent + sick + excuse
                        var percentage = (present * 100) / total
                        list.add(
                            ShowAttendenceModel(
                                nameRoll,
                                present.toString(),
                                absent.toString(),
                                sick.toString(),
                                excuse.toString(),
                                total.toString(),
                                "$percentage"

                            )
                        )
                    }

                    rc_showAttendence.setHasFixedSize(true)
                    rc_showAttendence.layoutManager = LinearLayoutManager(context)
                    rc_showAttendence.adapter = ShowAttendenceAdapter(requireContext(), list)

                } else {
                    context?.showToast("No Data Found!!!")
                }

            } catch (e: Exception) {
                context?.showToast("No Data Found!!!")
            }

        }, Response.ErrorListener {
            context?.showToast("Check Your Internet Connection!")
        })

        queue.add(sr)
    }

    private fun showStudentAttendence() {
        tv_ProgramAndYear_ShowAttendence.text = "$program -- $year\n$course"
        var list = ArrayList<ShowAttendenceModel>()
        var url =
            "https://njala-attendence.firebaseio.com/StudentsAttedence/$program/$year/$course.json"
        var queue = Volley.newRequestQueue(context)

        var sr = StringRequest(Request.Method.GET, url, Response.Listener {
            try {
                if (it != "null") {

                    for (key in JSONObject(it).keys()) {
                        var nameRoll =
                            JSONObject(it).getJSONObject(key).getString("name").toString() + "-" + key.toString()
                        var present = JSONObject(it).getJSONObject(key).getString("present").toInt()
                        var absent = JSONObject(it).getJSONObject(key).getString("absent").toInt()
                        var sick = JSONObject(it).getJSONObject(key).getString("sick").toInt()
                        var excuse = JSONObject(it).getJSONObject(key).getString("excuse").toInt()
                        var total = present + absent + sick + excuse
                        var percentage = (present * 100) / total
                        list.add(
                            ShowAttendenceModel(
                                nameRoll,
                                present.toString(),
                                absent.toString(),
                                sick.toString(),
                                excuse.toString(),
                                total.toString(),
                                "$percentage"

                            )
                        )
                    }

                    rc_showAttendence.setHasFixedSize(true)
                    rc_showAttendence.layoutManager = LinearLayoutManager(context)
                    rc_showAttendence.adapter = ShowAttendenceAdapter(requireContext(), list)

                } else {
                    context?.showToast("No Data Found!!!")
                }

            } catch (e: Exception) {
                context?.showToast("No Data Found!!!")
            }

        }, Response.ErrorListener {
            context?.showToast("Check Your Internet Connection!")
        })

        queue.add(sr)
    }
}
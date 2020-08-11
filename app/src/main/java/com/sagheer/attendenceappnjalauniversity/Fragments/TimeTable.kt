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
import com.sagheer.attendenceappnjalauniversity.AdaptersModels.TimeTableAdapter
import com.sagheer.attendenceappnjalauniversity.AdaptersModels.TimeTableModel
import com.sagheer.attendenceappnjalauniversity.R
import com.sagheer.attendenceappnjalauniversity.showToast
import kotlinx.android.synthetic.main.fragment_time_table.*
import org.json.JSONObject

class TimeTable : Fragment() {

    companion object {
        var program = ""
        var year = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_table, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_ProgramAndYear_TimeTable.text = "$program - $year"
        val url = "https://njala-attendence.firebaseio.com/TimeTable/$program/$year.json"
        val queue = Volley.newRequestQueue(context)
        val sr = StringRequest(Request.Method.GET, url, Response.Listener {
        val list = ArrayList<TimeTableModel>()

            if (it != "null") {

                for (key in JSONObject(it).keys()) {
                    list.add(
                        TimeTableModel(
                            JSONObject(it).getJSONObject(key).getString("name"),
                            JSONObject(it).getJSONObject(key).getString("time")
                        )
                    )
                }
                rc_timeTable.setHasFixedSize(true)
                rc_timeTable.layoutManager = LinearLayoutManager(context)
                rc_timeTable.adapter = TimeTableAdapter(requireContext(), list)

            } else {
                context?.showToast("No Time Table Found!!!")
            }
        }, Response.ErrorListener {

        })
        queue.add(sr)
    }
}
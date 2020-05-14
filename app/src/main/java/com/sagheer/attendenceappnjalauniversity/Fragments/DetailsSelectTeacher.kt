package com.sagheer.attendenceappnjalauniversity.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sagheer.attendenceappnjalauniversity.R
import com.sagheer.attendenceappnjalauniversity.showToast
import kotlinx.android.synthetic.main.fragment_details_select_teacher.*
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class DetailsSelectTeacher : Fragment() {
    private var listOfTeachersNames = ArrayList<String>()
    private var listOfPrograms = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_select_teacher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTeachersDetails()


    }

    private fun setTeachersDetails() {
        val urlTeachers = "https://njala-attendence.firebaseio.com/Teachers.json"
        val urlProgramms = "https://njala-attendence.firebaseio.com/Programs.json"
        val queue = Volley.newRequestQueue(context)

        val srTeachers = StringRequest(Request.Method.GET, urlTeachers, Response.Listener {

            for (key in JSONObject(it).keys()) {
                listOfTeachersNames.add(JSONObject(it).getJSONObject(key).getString("name"))
            }

            at_TeacherName_TeacherDetailsFragment.setAdapter(
                ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    listOfTeachersNames
                )
            )
        }, Response.ErrorListener {
            context?.showToast("no responce")
        })

        val srPrograms = StringRequest(Request.Method.GET, urlProgramms, Response.Listener {
            listOfPrograms.add(" Select Program")

            for (key in JSONObject(it).keys()) {
                listOfPrograms.add(JSONObject(it).getJSONObject(key).getString("name"))
            }

            spinner_Program_TeacherDetailsFragment.adapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                listOfPrograms
            )
            setListnerForSpinnerPrograms()
        }, Response.ErrorListener {
            context?.showToast("no responce")
        })

        queue.add(srTeachers)
        queue.add(srPrograms)
    }

    private fun setListnerForSpinnerPrograms() {
        spinner_Program_TeacherDetailsFragment.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    context?.showToast(spinner_Program_TeacherDetailsFragment.selectedItem.toString())
                }
            }
    }
}
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
import com.sagheer.attendenceappnjalauniversity.MainActivity
import com.sagheer.attendenceappnjalauniversity.R
import com.sagheer.attendenceappnjalauniversity.showToast
import kotlinx.android.synthetic.main.fragment_details_select_student.*
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class DetailsSelectStudent : Fragment() {
    var listOfCourses = ArrayList<String>()
    var listOfYears = ArrayList<String>()
    var listOfCourseCodes = ArrayList<String>()


    companion object {
        var program = ""
        var course = ""
        var year = ""
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_select_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarLayoutSelectStudentDetails.visibility = View.VISIBLE


        setPrograms()
        setYears()

        btn_viewAttendence.setOnClickListener {
            if (program != "") {
                if (course != "" || course != "Select Course") {
                    (context as MainActivity).openFragment(showAttendence())
                } else {
                    context?.showToast("Select A Course..")
                }
            } else {
                context?.showToast("Select A Program..")
            }
        }
    }

    private fun setYears() {
        listOfYears.clear()
        listOfYears.add("1st Year")
        listOfYears.add("2nd Year")
        listOfYears.add("3rd Year")
        listOfYears.add("4th Year")
        listOfYears.add("5th Year")

        spinner_Year_StudentDetailsFragment.adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            listOfYears
        )
        setListnerForSpinnerYears()

    }

    private fun setListnerForSpinnerYears() {

        spinner_Year_StudentDetailsFragment.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    year = spinner_Year_StudentDetailsFragment.selectedItem.toString()

                }
            }
    }

    private fun setPrograms() {
        var listOfPrograms = ArrayList<String>()

        val urlProgramms = "https://njala-attendence.firebaseio.com/Programs.json"
        val queue = Volley.newRequestQueue(context)

        val srPrograms = StringRequest(Request.Method.GET, urlProgramms, Response.Listener {
            try {
                listOfPrograms.add("Select Program")

                for (key in JSONObject(it).keys()) {
                    listOfPrograms.add(JSONObject(it).getJSONObject(key).getString("name"))
                }

                spinner_Program_StudentDetailsFragment.adapter = ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    listOfPrograms
                )
                setListnerForSpinnerPrograms()
                progressBarLayoutSelectStudentDetails.visibility = View.GONE


            } catch (e: Exception) {

            }
        }, Response.ErrorListener {
            context?.showToast("no responce")
            progressBarLayoutSelectStudentDetails.visibility = View.GONE

        })

        queue.add(srPrograms)

    }

    private fun setListnerForSpinnerPrograms() {
        spinner_Program_StudentDetailsFragment.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (spinner_Program_StudentDetailsFragment.selectedItem != "Select Program") {
                        program = spinner_Program_StudentDetailsFragment.selectedItem.toString()

                        val url =
                            "https://njala-attendence.firebaseio.com/Courses/" + spinner_Program_StudentDetailsFragment.selectedItem.toString() + ".json"
                        val queue = Volley.newRequestQueue(context)
                        val sr = StringRequest(Request.Method.GET, url, Response.Listener {

                            if (it != "null") {
                                listOfCourses.clear()
                                listOfCourseCodes.clear()
                                listOfCourses.add("Select Course")
                                listOfCourseCodes.add("Code")
                                for (key in JSONObject(it).keys()) {
                                    listOfCourses.add(JSONObject(it).getJSONObject(key).getString("name"))
                                    if (JSONObject(it).getJSONObject(key).has("code")) {
                                        listOfCourseCodes.add(
                                            JSONObject(it).getJSONObject(key).getString(
                                                "code"
                                            )
                                        )
                                    }
                                }
                                spinner_Course_StudentDetailsFragment.visibility = View.VISIBLE
                                spinner_Course_StudentDetailsFragment.adapter =
                                    ArrayAdapter<String>(
                                        requireContext(),
                                        android.R.layout.simple_list_item_1,
                                        listOfCourses
                                    )
                                setListnerForSpinnerCourses()
                            } else {
                                listOfCourses.clear()
                                listOfCourseCodes.clear()
                                listOfCourses.add("Select Course")
                                setListnerForSpinnerCourses()

                                context?.showToast("No Courses Found In This Program")
                            }
                        }, Response.ErrorListener {

                        })

                        queue.add(sr)

                    }
                }
            }
    }

    private fun setListnerForSpinnerCourses() {
        spinner_Course_StudentDetailsFragment.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (spinner_Course_StudentDetailsFragment.selectedItem != "Select Course") {
                        course = spinner_Course_StudentDetailsFragment.selectedItem.toString()

                    }
                }
            }
    }

}

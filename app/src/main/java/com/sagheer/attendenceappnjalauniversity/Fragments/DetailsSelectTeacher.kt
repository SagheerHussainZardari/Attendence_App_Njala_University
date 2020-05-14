package com.sagheer.attendenceappnjalauniversity.Fragments


import android.os.Bundle
import android.util.Log
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
    private var listOfCourses = ArrayList<String>()
    private var listOfCourseCodes = ArrayList<String>()
    private var listOfYears = ArrayList<String>()
    private var listOfSemesters = ArrayList<String>()


    companion object {
        var teacherName = ""
        var program = ""
        var course = ""
        var courseCode = ""
        var year = ""
        var semester = ""
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_select_teacher, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setYearAndSemester()
        setTeachersDetails()

        btn_takeAttendence.setOnClickListener {
            if (listOfTeachersNames.contains(at_TeacherName_TeacherDetailsFragment.text.toString())) {
                teacherName = at_TeacherName_TeacherDetailsFragment.text.toString()

                if (spinner_Program_TeacherDetailsFragment.selectedItem != "Select Program") {
                    program = spinner_Program_TeacherDetailsFragment.selectedItem.toString()

                    if (spinner_Course_TeacherDetailsFragment.selectedItem != "Select Course") {
                        course = spinner_Course_TeacherDetailsFragment.selectedItem.toString()
                        courseCode =
                            listOfCourseCodes[spinner_Course_TeacherDetailsFragment.selectedItemPosition]

                        if (spinner_Year_TeacherDetailsFragment.selectedItem != "Select Year") {
                            year = spinner_Year_TeacherDetailsFragment.selectedItem.toString()

                            if (spinner_Semester_TeacherDetailsFragment.selectedItem != "Select Semester") {
                                semester =
                                    spinner_Semester_TeacherDetailsFragment.selectedItem.toString()

                                Log.d("a", "dataSagheer : TeacherName = $teacherName")
                                Log.d("a", "dataSagheer : Program = $program")
                                Log.d("a", "dataSagheer : Course = $course")
                                Log.d("a", "dataSagheer : Course Code = $courseCode")
                                Log.d("a", "dataSagheer : Year = $year")
                                Log.d("a", "dataSagheer : Semester = $semester")


                            } else {
                                context?.showToast("Must Select a Semester")
                            }
                        } else {
                            context?.showToast("Must Select a Year")
                        }
                    } else {
                        context?.showToast("Must Select a Course")
                    }
                } else {
                    context?.showToast("Must Select a Program")
                }
            } else {
                at_TeacherName_TeacherDetailsFragment.error =
                    "Your Name Must Be in Teachers Name List.."
            }
        }

    }

    private fun setYearAndSemester() {
        listOfYears.add("Select Year")
        listOfYears.add("1st Year")
        listOfYears.add("2nd Year")
        listOfYears.add("3rd Year")
        listOfYears.add("4th Year")
        listOfSemesters.add("Select Semester")
        listOfSemesters.add("1st Semester")
        listOfSemesters.add("2nd Semester")
        listOfSemesters.add("3rd Semester")
        listOfSemesters.add("4th Semester")
        listOfSemesters.add("5th Semester")
        listOfSemesters.add("6th Semester")
        listOfSemesters.add("7th Semester")
        listOfSemesters.add("8th Semester")
        listOfSemesters.add("9th Semester")
        listOfSemesters.add("10th Semester")
        spinner_Year_TeacherDetailsFragment.adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            listOfYears
        )
        setListnerForSpinnerYears()

        spinner_Semester_TeacherDetailsFragment.adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            listOfSemesters
        )
        setListnerForSpinnerSemesters()
    }



    private fun setTeachersDetails() {
        val urlTeachers = "https://njala-attendence.firebaseio.com/Teachers.json"
        val urlProgramms = "https://njala-attendence.firebaseio.com/Programs.json"
        val queue = Volley.newRequestQueue(context)

        val srTeachers = StringRequest(Request.Method.GET, urlTeachers, Response.Listener {
            try {


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

            } catch (e: Exception) {
            }
        }, Response.ErrorListener {
            context?.showToast("no responce")
        })

        val srPrograms = StringRequest(Request.Method.GET, urlProgramms, Response.Listener {
            try {
                listOfPrograms.add("Select Program")

                for (key in JSONObject(it).keys()) {
                    listOfPrograms.add(JSONObject(it).getJSONObject(key).getString("name"))
                }

                spinner_Program_TeacherDetailsFragment.adapter = ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    listOfPrograms
                )
                setListnerForSpinnerPrograms()

            } catch (e: Exception) {
            }
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
                    if (spinner_Program_TeacherDetailsFragment.selectedItem != "Select Program") {
                        var url =
                            "https://njala-attendence.firebaseio.com/Courses/" + spinner_Program_TeacherDetailsFragment.selectedItem.toString() + ".json"
                        var queue = Volley.newRequestQueue(context)
                        var sr = StringRequest(Request.Method.GET, url, Response.Listener {


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

                            spinner_Course_TeacherDetailsFragment.adapter = ArrayAdapter<String>(
                                requireContext(),
                                android.R.layout.simple_list_item_1,
                                listOfCourses
                            )
                            setListnerForSpinnerCourses()

                        }, Response.ErrorListener {

                        })

                        queue.add(sr)

                    }
                }
            }
    }

    private fun setListnerForSpinnerCourses() {
        spinner_Course_TeacherDetailsFragment.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (spinner_Course_TeacherDetailsFragment.selectedItem != "Select Course") {
                        et_CourseCode_TeacherDetailsFragment.setText(listOfCourseCodes[position])
                        context?.showToast(spinner_Course_TeacherDetailsFragment.selectedItem.toString())
                    }
                }
            }
    }

    private fun setListnerForSpinnerYears() {
        spinner_Year_TeacherDetailsFragment.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                }
            }
    }

    private fun setListnerForSpinnerSemesters() {
        spinner_Semester_TeacherDetailsFragment.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                }
            }
    }
}

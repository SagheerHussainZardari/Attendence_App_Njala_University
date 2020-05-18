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

                    try {

                        if (spinner_Course_TeacherDetailsFragment.selectedItem != "Select Course") {
                            course = spinner_Course_TeacherDetailsFragment.selectedItem.toString()
                            courseCode =
                                listOfCourseCodes[spinner_Course_TeacherDetailsFragment.selectedItemPosition]
                            year = spinner_Year_TeacherDetailsFragment.selectedItem.toString()
                            semester =
                                spinner_Semester_TeacherDetailsFragment.selectedItem.toString()


                            (context as MainActivity).openFragment(TakeAttendencecTeacher())

                        } else {
                            context?.showToast("Must Select a Course")
                        }
                    } catch (e: java.lang.Exception) {
                        context?.showToast("Please Select A Program With Courses!")
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
        listOfYears.clear()
        listOfYears.add("Select Year")
        listOfYears.add("1st Year")
        listOfYears.add("2nd Year")
        listOfYears.add("3rd Year")
        listOfYears.add("4th Year")
        listOfYears.add("5th Year")

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
        listOfTeachersNames.clear()
        listOfPrograms.clear()
        listOfCourses.clear()
        listOfCourseCodes.clear()

        val urlTeachers = "https://njala-attendence.firebaseio.com/TeachersEmailsList.json"
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

        queue.add(srPrograms)
        queue.add(srTeachers)
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
                    program = spinner_Program_TeacherDetailsFragment.selectedItem.toString()
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
                        et_CourseCode_TeacherDetailsFragment.visibility = View.VISIBLE
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
                    var year = spinner_Year_TeacherDetailsFragment.selectedItem.toString()

                    if (year == "1st Year") {
                        listOfSemesters.clear()
                        listOfSemesters.add("1st Semester")
                        listOfSemesters.add("2nd Semester")

                        spinner_Semester_TeacherDetailsFragment.adapter = ArrayAdapter<String>(
                            requireContext(),
                            android.R.layout.simple_list_item_1,
                            listOfSemesters
                        )

                        val url =
                            "https://njala-attendence.firebaseio.com/Courses/$program/$year.json"
                        val queue = Volley.newRequestQueue(context)
                        val sr = StringRequest(Request.Method.GET, url, Response.Listener {

                            context?.showToast(it)
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
                                spinner_Course_TeacherDetailsFragment.visibility = View.VISIBLE
                                spinner_Course_TeacherDetailsFragment.adapter =
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





                    } else if (spinner_Year_TeacherDetailsFragment.selectedItem == "2nd Year") {
                        listOfSemesters.clear()
                        listOfSemesters.add("3rd Semester")
                        listOfSemesters.add("4th Semester")

                        spinner_Semester_TeacherDetailsFragment.adapter = ArrayAdapter<String>(
                            requireContext(),
                            android.R.layout.simple_list_item_1,
                            listOfSemesters
                        )


                        spinner_Semester_TeacherDetailsFragment.adapter = ArrayAdapter<String>(
                            requireContext(),
                            android.R.layout.simple_list_item_1,
                            listOfSemesters
                        )

                        val url =
                            "https://njala-attendence.firebaseio.com/Courses/$program/$year.json"
                        val queue = Volley.newRequestQueue(context)
                        val sr = StringRequest(Request.Method.GET, url, Response.Listener {

                            context?.showToast(it)
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
                                spinner_Course_TeacherDetailsFragment.visibility = View.VISIBLE
                                spinner_Course_TeacherDetailsFragment.adapter =
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




                    } else if (spinner_Year_TeacherDetailsFragment.selectedItem == "3rd Year") {
                        listOfSemesters.clear()
                        listOfSemesters.add("5th Semester")
                        listOfSemesters.add("6th Semester")

                        spinner_Semester_TeacherDetailsFragment.adapter = ArrayAdapter<String>(
                            requireContext(),
                            android.R.layout.simple_list_item_1,
                            listOfSemesters
                        )


                        spinner_Semester_TeacherDetailsFragment.adapter = ArrayAdapter<String>(
                            requireContext(),
                            android.R.layout.simple_list_item_1,
                            listOfSemesters
                        )

                        val url =
                            "https://njala-attendence.firebaseio.com/Courses/$program/$year.json"
                        val queue = Volley.newRequestQueue(context)
                        val sr = StringRequest(Request.Method.GET, url, Response.Listener {

                            context?.showToast(it)
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
                                spinner_Course_TeacherDetailsFragment.visibility = View.VISIBLE
                                spinner_Course_TeacherDetailsFragment.adapter =
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


                    } else if (spinner_Year_TeacherDetailsFragment.selectedItem == "4th Year") {
                        listOfSemesters.clear()
                        listOfSemesters.add("7th Semester")
                        listOfSemesters.add("8th Semester")

                        spinner_Semester_TeacherDetailsFragment.adapter = ArrayAdapter<String>(
                            requireContext(),
                            android.R.layout.simple_list_item_1,
                            listOfSemesters
                        )


                        spinner_Semester_TeacherDetailsFragment.adapter = ArrayAdapter<String>(
                            requireContext(),
                            android.R.layout.simple_list_item_1,
                            listOfSemesters
                        )

                        val url =
                            "https://njala-attendence.firebaseio.com/Courses/$program/$year.json"
                        val queue = Volley.newRequestQueue(context)
                        val sr = StringRequest(Request.Method.GET, url, Response.Listener {

                            context?.showToast(it)
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
                                spinner_Course_TeacherDetailsFragment.visibility = View.VISIBLE
                                spinner_Course_TeacherDetailsFragment.adapter =
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


                    } else if (spinner_Year_TeacherDetailsFragment.selectedItem == "5th Year") {
                        listOfSemesters.clear()
                        listOfSemesters.add("9th Semester")
                        listOfSemesters.add("10th Semester")

                        spinner_Semester_TeacherDetailsFragment.adapter = ArrayAdapter<String>(
                            requireContext(),
                            android.R.layout.simple_list_item_1,
                            listOfSemesters
                        )


                        spinner_Semester_TeacherDetailsFragment.adapter = ArrayAdapter<String>(
                            requireContext(),
                            android.R.layout.simple_list_item_1,
                            listOfSemesters
                        )

                        val url =
                            "https://njala-attendence.firebaseio.com/Courses/$program/$year.json"
                        val queue = Volley.newRequestQueue(context)
                        val sr = StringRequest(Request.Method.GET, url, Response.Listener {

                            context?.showToast(it)
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
                                spinner_Course_TeacherDetailsFragment.visibility = View.VISIBLE
                                spinner_Course_TeacherDetailsFragment.adapter =
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
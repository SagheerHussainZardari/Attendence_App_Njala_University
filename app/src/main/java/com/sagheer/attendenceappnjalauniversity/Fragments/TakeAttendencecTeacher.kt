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
import com.google.firebase.database.FirebaseDatabase
import com.sagheer.attendenceappnjalauniversity.AdaptersModels.AttedenceAdapter
import com.sagheer.attendenceappnjalauniversity.AdaptersModels.AttendenceMmodel
import com.sagheer.attendenceappnjalauniversity.Fragments.DetailsSelectTeacher.Companion.course
import com.sagheer.attendenceappnjalauniversity.Fragments.DetailsSelectTeacher.Companion.program
import com.sagheer.attendenceappnjalauniversity.Fragments.DetailsSelectTeacher.Companion.year
import com.sagheer.attendenceappnjalauniversity.showToast
import kotlinx.android.synthetic.main.fragment_take_attendencec_teacher.*
import org.json.JSONObject


class TakeAttendencecTeacher : Fragment() {

    companion object {
        var AttedenceList = IntArray(200)
        val studList = ArrayList<AttendenceMmodel>()
        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )
            : View? = inflater.inflate(
        com.sagheer.attendenceappnjalauniversity.R.layout.fragment_take_attendencec_teacher,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i in AttedenceList.indices) {
            AttedenceList[i] = 1
        }

        val queue = Volley.newRequestQueue(context)
        val url = "https://njala-attendence.firebaseio.com/StudentsDetails/$program/$year.json"
        val sr = StringRequest(Request.Method.GET, url, Response.Listener {

            try {
                studList.clear()
                for (key in JSONObject(it).keys()) {
                    studList.add(
                        AttendenceMmodel(
                            JSONObject(it).getJSONObject(key).getString("name") + "-" + JSONObject(
                                it
                            ).getJSONObject(key).getString("roll")
                        )
                    )
                }

                recyclerViewAttendence.setHasFixedSize(true)
                recyclerViewAttendence.layoutManager = LinearLayoutManager(context)
                recyclerViewAttendence.adapter = AttedenceAdapter(requireContext(), studList)

            } catch (e: Exception) {
            }

        }, Response.ErrorListener {
            context?.showToast("No Response Check Your Internet!!!")
        })

        queue.add(sr)

        btn_finishAttedence.setOnClickListener {
            val ref = myRef.database.getReference("StudentsAttedence").child(program).child(year)
                .child(course)



            for (i in studList.indices) {
                var queue = Volley.newRequestQueue(context)
                var url =
                    "https://njala-attendence.firebaseio.com/StudentsAttedence/$program/$year/$course/${studList[i].stdNameRoll.substringAfter(
                        '-'
                    )}.json"
                var sr = StringRequest(Request.Method.GET, url, Response.Listener {
                    if (it == "null") {

                        ref.child(studList[i].stdNameRoll.substringAfter('-')).child("name")
                            .setValue(studList[i].stdNameRoll.substringBefore('-'))
                        if (AttedenceList[i] == 1) {
                            ref.child(
                                TakeAttendencecTeacher.studList[i].stdNameRoll.substringAfter(
                                    '-'
                                )
                            ).child("present").setValue(1)
                            ref.child(
                                TakeAttendencecTeacher.studList[i].stdNameRoll.substringAfter(
                                    '-'
                                )
                            ).child("total").setValue(1)
                            ref.child(
                                TakeAttendencecTeacher.studList[i].stdNameRoll.substringAfter(
                                    '-'
                                )
                            ).child("absent").setValue(0)
                            ref.child(
                                TakeAttendencecTeacher.studList[i].stdNameRoll.substringAfter(
                                    '-'
                                )
                            ).child("sick").setValue(0)
                        } else if (AttedenceList[i] == 0) {

                            ref.child(
                                TakeAttendencecTeacher.studList[i].stdNameRoll.substringAfter(
                                    '-'
                                )
                            ).child("absent").setValue(1)
                            ref.child(
                                TakeAttendencecTeacher.studList[i].stdNameRoll.substringAfter(
                                    '-'
                                )
                            ).child("total").setValue(1)
                            ref.child(
                                TakeAttendencecTeacher.studList[i].stdNameRoll.substringAfter(
                                    '-'
                                )
                            ).child("present").setValue(0)
                            ref.child(
                                TakeAttendencecTeacher.studList[i].stdNameRoll.substringAfter(
                                    '-'
                                )
                            ).child("sick").setValue(0)
                        } else {

                            ref.child(
                                TakeAttendencecTeacher.studList[i].stdNameRoll.substringAfter(
                                    '-'
                                )
                            ).child("total").setValue(1)
                            ref.child(
                                TakeAttendencecTeacher.studList[i].stdNameRoll.substringAfter(
                                    '-'
                                )
                            ).child("sick").setValue(1)
                            ref.child(
                                TakeAttendencecTeacher.studList[i].stdNameRoll.substringAfter(
                                    '-'
                                )
                            ).child("present").setValue(0)
                            ref.child(
                                TakeAttendencecTeacher.studList[i].stdNameRoll.substringAfter(
                                    '-'
                                )
                            ).child("absent").setValue(0)
                        }

                    } else {
                        if (AttedenceList[i] == 1) {

                            ref.child(
                                TakeAttendencecTeacher.studList[i].stdNameRoll.substringAfter(
                                    '-'
                                )
                            ).child("present")
                                .setValue((JSONObject(it).getString("present").toInt() + 1))
                            ref.child(
                                TakeAttendencecTeacher.studList[i].stdNameRoll.substringAfter(
                                    '-'
                                )
                            ).child("total")
                                .setValue((JSONObject(it).getString("total").toInt() + 1))
                        } else if (AttedenceList[i] == 0) {
                            ref.child(
                                TakeAttendencecTeacher.studList[i].stdNameRoll.substringAfter(
                                    '-'
                                )
                            ).child("absent")
                                .setValue((JSONObject(it).getString("absent").toInt() + 1))
                            ref.child(
                                TakeAttendencecTeacher.studList[i].stdNameRoll.substringAfter(
                                    '-'
                                )
                            ).child("total")
                                .setValue((JSONObject(it).getString("total").toInt() + 1))
                        } else {
                            ref.child(
                                TakeAttendencecTeacher.studList[i].stdNameRoll.substringAfter(
                                    '-'
                                )
                            ).child("sick").setValue((JSONObject(it).getString("sick").toInt() + 1))
                            ref.child(
                                TakeAttendencecTeacher.studList[i].stdNameRoll.substringAfter(
                                    '-'
                                )
                            ).child("total")
                                .setValue((JSONObject(it).getString("total").toInt() + 1))
                        }
                    }
                }, Response.ErrorListener {
                    context?.showToast("response = " + it.message.toString())
                })

                queue.add(sr)

            }

        }
    }
}

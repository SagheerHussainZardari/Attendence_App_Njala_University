package com.sagheer.attendenceappnjalauniversity.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sagheer.attendenceappnjalauniversity.AdaptersModels.AttedenceAdapter
import com.sagheer.attendenceappnjalauniversity.AdaptersModels.AttendenceMmodel
import com.sagheer.attendenceappnjalauniversity.R
import kotlinx.android.synthetic.main.fragment_take_attendencec_teacher.*


class TakeAttendencecTeacher : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_take_attendencec_teacher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val stdList = ArrayList<AttendenceMmodel>()
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12346"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12347"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12348"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12349"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12341"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12346"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12347"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12348"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12349"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12341"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12346"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12347"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12348"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12349"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12341"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12346"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12347"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12348"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12349"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12341"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12346"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12347"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12348"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12349"))
        stdList.add(AttendenceMmodel("Sagheer Hussain - 12341"))

        recyclerViewAttendence.setHasFixedSize(true)
        recyclerViewAttendence.layoutManager = LinearLayoutManager(context)
        recyclerViewAttendence.adapter = AttedenceAdapter(requireContext(), stdList)


    }
}

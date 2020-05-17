package com.sagheer.attendenceappnjalauniversity.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sagheer.attendenceappnjalauniversity.AdaptersModels.ShowAttendenceAdapter
import com.sagheer.attendenceappnjalauniversity.AdaptersModels.ShowAttendenceModel
import com.sagheer.attendenceappnjalauniversity.R
import kotlinx.android.synthetic.main.fragment_show_attendence.*

/**
 * A simple [Fragment] subclass.
 */
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

        var list = ArrayList<ShowAttendenceModel>()
        list.add(
            ShowAttendenceModel(
                "Sagheer-1234",
                "3",
                "2",
                "1",
                "6",
                "50%"
            )
        )
        list.add(
            ShowAttendenceModel(
                "Sagheer-1234",
                "3",
                "2",
                "1",
                "6",
                "50%"
            )
        )

        rc_showAttendence.setHasFixedSize(true)
        rc_showAttendence.layoutManager = LinearLayoutManager(context)
        rc_showAttendence.adapter = ShowAttendenceAdapter(requireContext(), list)
    }


}

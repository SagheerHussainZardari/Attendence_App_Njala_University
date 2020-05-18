package com.sagheer.attendenceappnjalauniversity.AdaptersModels

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sagheer.attendenceappnjalauniversity.R
import kotlinx.android.synthetic.main.recyclerview_timetable.view.*

class TimeTableAdapter(var context: Context, var list: ArrayList<TimeTableModel>) :
    RecyclerView.Adapter<TimeTableAdapter.MyViewHolder>() {


    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.recyclerview_timetable,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.tv_courseName_timetable.text = list[position].courseName
        holder.view.tv_courseTime_timetable.text = list[position].courseTime
    }

}
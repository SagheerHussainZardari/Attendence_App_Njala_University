package com.sagheer.attendenceappnjalauniversity.AdaptersModels

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sagheer.attendenceappnjalauniversity.Fragments.TakeAttendencecTeacher
import com.sagheer.attendenceappnjalauniversity.R
import com.sagheer.attendenceappnjalauniversity.showToast
import kotlinx.android.synthetic.main.recyclerview_attendence.view.*

class AttedenceAdapter(var context: Context, var list: ArrayList<AttendenceMmodel>) :


    RecyclerView.Adapter<AttedenceAdapter.MyViewHolder>() {

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.recyclerview_attendence,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.tv_stdNameRoll_RecyclerViewAttendence.text = list[position].stdNameRoll

        holder.view.tv_Present_RecyclerViewAttendence.setOnClickListener {
            if (it.tag.equals("gray")) {
                TakeAttendencecTeacher.AttedenceList[position] = 1
                context.showToast(TakeAttendencecTeacher.AttedenceList[position].toString())

                it.setBackgroundColor(Color.parseColor("#28D82D"))
                holder.view.tv_Absent_RecyclerViewAttendence.setBackgroundColor(Color.parseColor("#B8B8B8"))
                holder.view.tv_Sick_RecyclerViewAttendence.setBackgroundColor(Color.parseColor("#B8B8B8"))
                holder.view.tv_Absent_RecyclerViewAttendence.tag = "gray"
                holder.view.tv_Sick_RecyclerViewAttendence.tag = "gray"
                it.tag = "green"
            } else {
                it.setBackgroundColor(Color.parseColor("#B8B8B8"))
                it.tag = "gray"
            }
        }
        holder.view.tv_Absent_RecyclerViewAttendence.setOnClickListener {
            if (it.tag.equals("gray")) {
                TakeAttendencecTeacher.AttedenceList[position] = 0
                context.showToast(TakeAttendencecTeacher.AttedenceList[position].toString())

                it.setBackgroundColor(Color.parseColor("#F13232"))
                holder.view.tv_Present_RecyclerViewAttendence.setBackgroundColor(Color.parseColor("#B8B8B8"))
                holder.view.tv_Sick_RecyclerViewAttendence.setBackgroundColor(Color.parseColor("#B8B8B8"))
                holder.view.tv_Present_RecyclerViewAttendence.tag = "gray"
                holder.view.tv_Sick_RecyclerViewAttendence.tag = "gray"
                it.tag = "red"
            } else {
                it.setBackgroundColor(Color.parseColor("#B8B8B8"))
                it.tag = "gray"
            }
        }
        holder.view.tv_Sick_RecyclerViewAttendence.setOnClickListener {
            if (it.tag.equals("gray")) {
                TakeAttendencecTeacher.AttedenceList[position] = 2
                context.showToast(TakeAttendencecTeacher.AttedenceList[position].toString())

                it.setBackgroundColor(Color.parseColor("#F5E01E"))
                holder.view.tv_Absent_RecyclerViewAttendence.setBackgroundColor(Color.parseColor("#B8B8B8"))
                holder.view.tv_Present_RecyclerViewAttendence.setBackgroundColor(Color.parseColor("#B8B8B8"))
                holder.view.tv_Absent_RecyclerViewAttendence.tag = "gray"
                holder.view.tv_Present_RecyclerViewAttendence.tag = "gray"
                it.tag = "yellow"
            } else {
                it.setBackgroundColor(Color.parseColor("#B8B8B8"))
                it.tag = "gray"
            }
        }


    }
}
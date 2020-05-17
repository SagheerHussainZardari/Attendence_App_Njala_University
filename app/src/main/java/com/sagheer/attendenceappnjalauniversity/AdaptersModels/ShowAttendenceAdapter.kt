package com.sagheer.attendenceappnjalauniversity.AdaptersModels

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sagheer.attendenceappnjalauniversity.R
import kotlinx.android.synthetic.main.recyclerview_show_attedence.view.*

class ShowAttendenceAdapter(var context: Context, var list: ArrayList<ShowAttendenceModel>) :
    RecyclerView.Adapter<ShowAttendenceAdapter.MyyViewHolder>() {


    class MyyViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyyViewHolder {
        return MyyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.recyclerview_show_attedence,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: MyyViewHolder, position: Int) {
        holder.view.tv_stdNameRoll_ShowAttendence.text = list[position].stdNameRoll
        holder.view.tv_present_ShowAttendence.text = list[position].present
        holder.view.tv_absent_ShowAttendence.text = list[position].absent
        holder.view.tv_sick_ShowAttendence.text = list[position].sick
        holder.view.tv_total_ShowAttendence.text = list[position].total
        holder.view.tv_percentage_ShowAttendence.text = list[position].percentage
    }
}
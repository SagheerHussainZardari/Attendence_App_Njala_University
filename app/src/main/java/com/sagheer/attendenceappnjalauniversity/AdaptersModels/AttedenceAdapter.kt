package com.sagheer.attendenceappnjalauniversity.AdaptersModels

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sagheer.attendenceappnjalauniversity.R
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
    }
}
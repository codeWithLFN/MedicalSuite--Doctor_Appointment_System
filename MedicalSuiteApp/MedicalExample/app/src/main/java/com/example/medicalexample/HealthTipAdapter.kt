package com.example.medicalexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalexample.R

class HealthTipAdapter(private val healthTips: List<HealthTip>) :
    RecyclerView.Adapter<HealthTipAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.health_tip_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val healthTip = healthTips[position]
        holder.titleTextView.text = healthTip.title
        holder.descriptionTextView.text = healthTip.description
    }

    override fun getItemCount(): Int {
        return healthTips.size
    }
}